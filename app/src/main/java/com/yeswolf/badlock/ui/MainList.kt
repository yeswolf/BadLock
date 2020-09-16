package com.yeswolf.badlock.ui

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.network.ApkMirror
import com.yeswolf.badlock.packageExists
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Composable
fun MainList(
    items: List<Plugin>, loading: MutableState<Boolean>, onUpdateItem: (Boolean, Plugin) -> Unit,
) {
    val context = ContextAmbient.current
    if (loading.value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    LazyColumnFor(items = items) {
        val plugin = it
        Row(modifier = Modifier.clickable(onClick = {

        }).padding(10.dp).fillMaxWidth())
        {
            var installedVersion: Version? = null
            try {
                installedVersion = Version(
                    context.packageManager.getPackageInfo(
                        plugin.packageName,
                        0
                    ).versionName
                )
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            Column(modifier = Modifier.padding(5.dp)) {
                Image(asset = imageResource(id = plugin.icon), modifier = Modifier.width(48.dp))
            }
            Column(
                modifier = Modifier.padding(5.dp)
                    .gravity(Alignment.CenterVertically)
            ) {
                Text(text = it.name)
                if (packageExists(plugin.packageName, context)) {
                    Text(text = "installed v.$installedVersion", color = Color.Blue)
                }
            }
            Column(
                modifier = Modifier.padding(5.dp)
                    .fillMaxWidth()
                    .fillMaxSize().gravity(Alignment.CenterVertically),
                horizontalGravity = Alignment.End,
            ) {
                if (plugin.versionsLoaded()) {
                    val newVersion = plugin.versions.first()
                    var text = ""
                    var showButton = false
                    if (installedVersion == null) {
                        text = "Install"
                        showButton = true
                    } else {
                        if (newVersion.compareTo(installedVersion) == 1) {
                            text = "Update"
                            showButton = true
                        }
                    }
                    if (showButton) {
                        Button(enabled = !plugin.loading,onClick = {
                            onUpdateItem(true, plugin)
                            Observable.fromCallable {
                                ApkMirror.latestVersionDirectDownloadURL(plugin)
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe {

                                    val request = DownloadManager.Request(Uri.parse(it))
                                    val downloadManager: DownloadManager =
                                        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                                    request.setAllowedOverRoaming(false)
                                    request.setTitle("Downloading ${plugin.latestVersionApkName()}")
                                    request.setDescription("Downloading ${plugin.latestVersionApkName()}")
                                    request.setDestinationInExternalPublicDir(
                                        Environment.DIRECTORY_DOWNLOADS,
                                        plugin.latestVersionApkName()
                                    )
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                                    downloadManager.enqueue(request)
                                }
                        }) {
                            Text(text)
                        }
                    }
                }
            }
        }
        Divider()

    }
}

//@Preview(showBackground = true, name = "Main list preview")
//@Composable
//fun MainListPreview() {
//    MainList(items = plugins)
//}

