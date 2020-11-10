package com.yeswolf.badlock.di

import com.yeswolf.badlock.apkmirror.data.ApkMirrorRepository
import com.yeswolf.badlock.apkmirror.domain.IApkMirrorRepository
import com.yeswolf.badlock.apkmirror.data.ApkMirror
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NetworkModule : Module() {
    init {
        bind<ApkMirror>().toInstance(ApkMirror)
        bind<IApkMirrorRepository>().toClass<ApkMirrorRepository>().singleton()
    }
}