package com.yeswolf.badlock.packages.data

import android.content.Context
import toothpick.InjectConstructor

@InjectConstructor
class PackageVersionSource(
    private val context: Context
) {
    fun getPackageVersionName(packageName: String): String? =
        try {
            context.packageManager.getPackageInfo(
                packageName,
                0
            ).versionName
        }catch (e: Exception){
            null
        }
}