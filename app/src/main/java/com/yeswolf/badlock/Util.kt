package com.yeswolf.badlock

import android.content.Context
import android.content.pm.PackageManager

fun packageExists(targetPackage: String?, context: Context): Boolean {
    val pm: PackageManager = context.packageManager
    try {
        pm.getPackageInfo(targetPackage!!, PackageManager.GET_META_DATA)
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
    return true
}

