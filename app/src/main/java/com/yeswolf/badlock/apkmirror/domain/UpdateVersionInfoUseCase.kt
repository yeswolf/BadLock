package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.Version
import toothpick.InjectConstructor

@InjectConstructor
class GetPluginVersionsUseCase(
    private val apkMirrorRepository: IApkMirrorRepository
) {
    operator fun invoke(plugin: Plugin): Array<Version> =
        apkMirrorRepository.getPluginVersions(plugin)
}