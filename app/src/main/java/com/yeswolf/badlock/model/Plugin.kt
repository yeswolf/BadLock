package com.yeswolf.badlock.model

data class Plugin(
    var name: String,
    var serverRoot: String,
    var icon: Int = 0,
    var packageName: String = "",
    val className: String,
    var description: String = "",
    var installedVersion: Version? = null
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