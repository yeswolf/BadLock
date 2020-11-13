package com.yeswolf.badlock.apkmirror.data

import com.yeswolf.badlock.apkmirror.domain.IApkMirrorRepository
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.apkmirror.domain.Version
import com.yeswolf.badlock.packages.mappers.VersionMapper
import toothpick.InjectConstructor

@InjectConstructor
class ApkMirrorRepository(
    private val apkMirrorSource: ApkMirrorSource,
    private val versionMapper: VersionMapper
) : IApkMirrorRepository {

    override fun getPluginVersions(plugin: Plugin): List<Version> =
        apkMirrorSource.getPluginVersions(plugin).map {
            versionMapper.fromDto(it)
        }
}