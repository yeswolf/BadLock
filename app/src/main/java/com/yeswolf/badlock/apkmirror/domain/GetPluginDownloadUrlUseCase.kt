package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.plugins.data.Plugin
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class GetPluginDownloadUrlUseCase (
    private val apkMirrorRepository: IApkMirrorRepository
){
    operator fun invoke(plugin: Plugin): Single<String> = apkMirrorRepository.getPluginDownloadUrlAsync(plugin = plugin)
}