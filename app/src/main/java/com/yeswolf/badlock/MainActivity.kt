package com.yeswolf.badlock

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.core.app.ActivityCompat
import com.yeswolf.badlock.di.Scopes
import com.yeswolf.badlock.receivers.DownloadResultReceiver
import com.yeswolf.badlock.ui.ApkList
import com.yeswolf.badlock.ui.viewmodel.ApkListViewModel
import com.yeswolf.badlock.ui.viewmodel.provideViewModel
import toothpick.ktp.KTP

class MainActivity : AppCompatActivity() {
    private val viewModel: ApkListViewModel by lazy {
        provideViewModel(
            this,
            { KTP.openScope(Scopes.APP) }
        )
    }

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

        viewModel.onError.observe(this, {
            Toast.makeText(
                baseContext,
                it,
                Toast.LENGTH_LONG
            ).show()
        })

        setContent {
            ApkList(viewModel = viewModel)
        }

    }
}