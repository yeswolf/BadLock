package com.yeswolf.badlock.plugins.data

import com.yeswolf.badlock.apkmirror.domain.Version

data class Plugin(
    var name: String,
    val serverCompanyNameRoot: String = "samsung-electronics-co-ltd",
    val vendorToken: String = "samsung",
    val serverRoot: String,
    val icon: Int = 0,
    val packageName: String = "",
    val className: String,
    val description: String = "",
    var installedVersion: Version? = null
) {
    var versions: List<Version> = emptyList()
    val apkName: String
        get() {
            return "$name ${versions.first()}.apk"
        }
    var loading: Boolean = false

    val versionsLoaded: Boolean
        get() {
            return versions.isNotEmpty()
        }
}