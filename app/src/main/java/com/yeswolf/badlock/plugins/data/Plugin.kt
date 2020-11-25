package com.yeswolf.badlock.plugins.data

import com.yeswolf.badlock.apkmirror.domain.Version

data class Plugin(
    val name: String,
    val serverRoot: String,
    val icon: Int = 0,
    val packageName: String = "",
    val className: String,
    val description: String = "",
    val installedVersion: Version? = null
) {
    var versions: List<Version> = emptyList()
    val apkName: String
        get() {
            return "$name ${versions.first()}.apk"
        }

    val versionsLoaded: Boolean
        get() {
            return versions.isNotEmpty()
        }
}