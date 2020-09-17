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
    var versions: Array<Version> = arrayOf()
    val url: String = ""
    var vendor = "Samsung"
    var loading = false
    fun latestVersionApkName(): String {
        val latestVersion = versions.first()
        return "$name $latestVersion.apk"
    }

    fun versionsLoaded(): Boolean {
        return !versions.isEmpty()
    }
}