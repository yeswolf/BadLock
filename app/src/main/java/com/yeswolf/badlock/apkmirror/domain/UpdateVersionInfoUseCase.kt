package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.model.Plugin
import toothpick.InjectConstructor

@InjectConstructor
class UpdateVersionInfoUseCase(
    private val apkMirrorRepository: IApkMirrorRepository
) {
    operator fun invoke(plugin: Plugin): Plugin =
        apkMirrorRepository.updateVersionInfo(plugin)
}