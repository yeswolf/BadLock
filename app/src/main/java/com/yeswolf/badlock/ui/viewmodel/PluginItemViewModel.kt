package com.yeswolf.badlock.ui.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.apkmirror.data.ApkMirrorSource
import com.yeswolf.badlock.plugins.data.Plugin
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PluginItemViewModel(
    val plugin: Plugin
) : ViewModel() {
    val icon: Int = plugin.icon
    val name = plugin.name
    val installedVersion = "installed v.${plugin.installedVersion}"
    val hasInstalledVersion = plugin.installedVersion != null
    var loading = mutableStateOf(false)

    val installUpdateButtonText: String
        get() {
            val newVersion = plugin.versions.first()
            var text = ""
            if (plugin.installedVersion != null) {
                if (newVersion.compareTo(plugin.installedVersion) == 1) {
                    text = "Update"
                }
            } else {
                text = "Install"
            }
            return text
        }
    val showInstallUpdateButton: Boolean
        get() {
            if (!plugin.versionsLoaded){
                return false
            }
            val newVersion = plugin.versions.first()
            var showButton = false
            if (plugin.installedVersion != null) {
                if (newVersion.compareTo(plugin.installedVersion) == 1) {
                    showButton = true
                }
            } else {
                showButton = true
            }
            return showButton
        }

    fun onPreferences(context: Context) {
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.setClassName(plugin.packageName, plugin.className)
            context.startActivity(intent)
        } catch (e: Throwable) {
        }
    }

    fun onDownload(context: Context) {
        loading.value = true
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
                loading.value = false
            }
    }
}