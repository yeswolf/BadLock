package com.yeswolf.badlock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.model.plugins
import com.yeswolf.badlock.ui.ApkListViewModel

class InstallResultReceiver(var viewModel: ApkListViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            viewModel.onLoadingUpdated(true)
            plugins.forEach {
                try {
                    it.installedVersion = Version(
                        context!!.packageManager.getPackageInfo(
                            it.packageName,
                            0
                        ).versionName, ""
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    print("no version found")
                }
            }
            viewModel.items = plugins
            viewModel.onLoadingUpdated(false)
        }
    }
}
