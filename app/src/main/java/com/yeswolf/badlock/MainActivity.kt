package com.yeswolf.badlock

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.core.app.ActivityCompat
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.model.plugins
import com.yeswolf.badlock.network.ApkMirror
import com.yeswolf.badlock.network.DownloadResultReceiver
import com.yeswolf.badlock.ui.ApkListViewModel
import com.yeswolf.badlock.ui.MainList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<ApkListViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 0
        )
        applicationContext.registerReceiver(
            DownloadResultReceiver(viewModel),
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        val filter = IntentFilter("android.intent.action.PACKAGE_INSTALL")
        filter.addAction("android.intent.action.PACKAGE_ADDED")
        filter.addAction("android.intent.action.PACKAGE_REMOVED")
        filter.addDataScheme("package");

        applicationContext.registerReceiver(
            InstallResultReceiver(viewModel),
            filter
        )


        viewModel.onLoadingUpdated(true)
        setContent {
            MainList(items = viewModel.items, viewModel.loading, viewModel::onLoadingUpdated)
        }
        Observable.fromCallable {
            plugins.forEach {
                try {
                    it.installedVersion = Version(
                        baseContext.packageManager.getPackageInfo(
                            it.packageName,
                            0
                        ).versionName, ""
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    print("no version found")
                }
            }
            viewModel.items = plugins
            plugins = plugins.map { ApkMirror.versionInfo(it) } as ArrayList<Plugin>
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel.onLoadingUpdated(false)
                    viewModel.items = plugins
                },
                {
                    viewModel.onLoadingUpdated(false)
                    Toast.makeText(
                        baseContext,
                        "Error loading update information",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
    }
}