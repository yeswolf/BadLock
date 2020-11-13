package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.plugins.data.Plugin

interface IApkMirrorRepository {
    fun getPluginVersions(plugin: Plugin): List<Version>
}