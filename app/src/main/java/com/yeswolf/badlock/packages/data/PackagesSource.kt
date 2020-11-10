package com.yeswolf.badlock.packages.data

import android.content.Context
import toothpick.InjectConstructor

@InjectConstructor
class PackagesSource(
    private val context: Context
) {
    fun getPackageVersionName(packageName: String): String =
        context.packageManager.getPackageInfo(
            packageName,
            0
        ).versionName
}