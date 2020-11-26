package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.plugins.data.Plugin
import io.reactivex.rxjava3.core.Single

interface IApkMirrorRepository {
    fun getPluginVersions(plugin: Plugin): List<Version>
    fun getPluginDownloadUrl(plugin: Plugin): String
    fun getPluginDownloadUrlAsync(plugin: Plugin): Single<String>
}