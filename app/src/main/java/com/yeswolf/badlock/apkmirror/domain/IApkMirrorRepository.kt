package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.Version

interface IApkMirrorRepository {
    fun getPluginVersions(plugin: Plugin): Array<Version>
}