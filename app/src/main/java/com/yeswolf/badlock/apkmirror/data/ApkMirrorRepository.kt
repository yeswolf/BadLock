package com.yeswolf.badlock.apkmirror.data

import com.yeswolf.badlock.apkmirror.domain.IApkMirrorRepository
import com.yeswolf.badlock.model.Plugin
import toothpick.InjectConstructor

@InjectConstructor
class ApkMirrorRepository(
    private val apkMirrorSource: ApkMirror
) : IApkMirrorRepository {

    override fun updateVersionInfo(plugin: Plugin): Plugin =
        apkMirrorSource.versionInfo(plugin)
}