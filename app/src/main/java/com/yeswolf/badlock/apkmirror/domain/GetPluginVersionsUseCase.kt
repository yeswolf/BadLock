package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.plugins.data.Plugin
import toothpick.InjectConstructor

@InjectConstructor
class GetPluginVersionsUseCase(
    private val apkMirrorRepository: IApkMirrorRepository
) {
    operator fun invoke(plugin: Plugin): List<Version> =
        apkMirrorRepository.getPluginVersions(plugin)
}