package com.yeswolf.badlock

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.core.app.ActivityCompat
import com.yeswolf.badlock.model.Plugin
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
        viewModel.onLoadingUpdated(true)
        setContent {
            MainList(items = viewModel.items, viewModel.loading, viewModel::onLoadingUpdated)
        }
        Observable.fromCallable {
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