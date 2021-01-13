package com.yeswolf.badlock.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.ui.viewmodel.ApkListViewModel
import com.yeswolf.badlock.ui.viewmodel.PluginItemViewModel

@Composable
fun ApkList(
    viewModel: ApkListViewModel
) {
    if (viewModel.loading.value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    val context = AmbientContext.current

    LazyColumn {
        items(viewModel.items){
            PluginItem(
                viewModel = PluginItemViewModel(
                    plugin = it,
                    onPreferences = {
                        try {
                            val intent = Intent("android.intent.action.MAIN")
                            intent.setClassName(it.packageName, it.className)
                            context.startActivity(intent)
                        } catch (e: Throwable) {
                        }
                    },
                    onStartDownload = { plugin: Plugin, url: String ->
                        val request = DownloadManager.Request(Uri.parse(url))
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
                )
            )
            Divider()
        }
    }
}

