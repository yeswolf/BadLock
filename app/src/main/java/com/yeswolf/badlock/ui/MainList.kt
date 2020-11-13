package com.yeswolf.badlock.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.yeswolf.badlock.apkmirror.data.ApkMirrorSource
import com.yeswolf.badlock.plugins.data.Plugin
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

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
            try {
                val intent = Intent("android.intent.action.MAIN")
                intent.setClassName(plugin.packageName, plugin.className)
                context.startActivity(intent)
            } catch (e: Throwable) {
            }
        }).padding(10.dp).fillMaxWidth())
        {
            Column(modifier = Modifier.padding(5.dp)) {
                Image(asset = imageResource(id = plugin.icon), modifier = Modifier.width(48.dp))
            }
            Column(
                modifier = Modifier.padding(5.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = it.name)
                if (plugin.installedVersion != null) {
                    Text(text = "installed v.${plugin.installedVersion}", color = Color.Blue)
                }
            }
            Column(
                modifier = Modifier.padding(5.dp)
                    .fillMaxWidth()
                    .fillMaxSize().align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.End,
            ) {
                if (plugin.versionsLoaded) {
                    val newVersion = plugin.versions.first()
                    var text = ""
                    var showButton = false
                    if (plugin.installedVersion == null) {
                        text = "Install"
                        showButton = true
                    } else {
                        if (newVersion.compareTo(plugin.installedVersion) == 1) {
                            text = "Update"
                            showButton = true
                        }
                    }
                    if (showButton) {
                        Button(enabled = !plugin.loading, onClick = {
                            onUpdateItem(true, plugin)
                            Observable.fromCallable {
                                ApkMirrorSource.latestVersionDirectDownloadURL(plugin)
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe {

                                    val request = DownloadManager.Request(Uri.parse(it))
                                    val downloadManager: DownloadManager =
                                        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                                    request.setAllowedOverRoaming(false)
                                    request.setTitle("Downloading ${plugin.apkName}")
                                    request.setDescription("Downloading ${plugin.apkName}")
                                    request.setDestinationInExternalPublicDir(
                                        Environment.DIRECTORY_DOWNLOADS,
                                        plugin.apkName
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

