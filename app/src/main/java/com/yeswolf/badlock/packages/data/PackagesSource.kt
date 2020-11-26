package com.yeswolf.badlock.packages.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.yeswolf.badlock.apkmirror.data.VersionData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import toothpick.InjectConstructor

@InjectConstructor
class PackagesSource(
    private val context: Context
) {
    private val updatesSubject: PublishSubject<String> = PublishSubject.create()
    val updates: Observable<String> = updatesSubject.hide()
    private val updatesReceiver:BroadcastReceiver

    init {
        updatesReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                val packageName = intent?.data?.schemeSpecificPart
                updatesSubject.onNext(packageName)
            }
        }
    }

    fun getPackageVersion(packageName: String): VersionData? =
        try {
            VersionData(context.packageManager.getPackageInfo(
                packageName,
                0
            ).versionName)
        }catch (e: Exception){
            null
        }

    fun startListen(){
        val filter = IntentFilter("android.intent.action.PACKAGE_INSTALL")
        filter.addAction("android.intent.action.PACKAGE_ADDED")
        filter.addAction("android.intent.action.PACKAGE_REMOVED")
        filter.addDataScheme("package")
        context.registerReceiver(updatesReceiver, filter)
    }

    fun stopListen(){
        context.unregisterReceiver(updatesReceiver)
    }
}