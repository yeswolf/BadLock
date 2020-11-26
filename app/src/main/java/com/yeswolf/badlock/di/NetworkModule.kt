package com.yeswolf.badlock.di

import com.yeswolf.badlock.apkmirror.data.ApkMirrorRepository
import com.yeswolf.badlock.apkmirror.data.ApkMirrorSource
import com.yeswolf.badlock.apkmirror.domain.IApkMirrorRepository
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NetworkModule : Module() {
    init {
        bind<ApkMirrorSource>().toInstance(ApkMirrorSource)
        bind<IApkMirrorRepository>().toClass<ApkMirrorRepository>().singleton()
    }
}