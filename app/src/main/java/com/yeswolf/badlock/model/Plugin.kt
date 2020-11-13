package com.yeswolf.badlock.model

data class Plugin(
    val name: String,
    val serverRoot: String,
    val icon: Int = 0,
    val packageName: String = "",
    val className: String,
    val description: String = "",
    val installedVersion: Version? = null
) {
    var versions: Array<Version> = emptyArray()
    var loading = false
    val apkName: String
        get() {
            return "$name ${versions.first()}.apk"
        }

    val versionsLoaded: Boolean
        get() {
            return versions.isNotEmpty()
        }
}