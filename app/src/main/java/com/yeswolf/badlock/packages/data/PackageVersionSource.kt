package com.yeswolf.badlock.packages.data

import android.content.Context
import com.yeswolf.badlock.apkmirror.data.VersionData
import toothpick.InjectConstructor

@InjectConstructor
class PackageVersionSource(
    private val context: Context
) {
    fun getPackageVersion(packageName: String): VersionData? =
        try {
            VersionData(context.packageManager.getPackageInfo(
                packageName,
                0
            ).versionName)
        }catch (e: Exception){
            null
        }
}