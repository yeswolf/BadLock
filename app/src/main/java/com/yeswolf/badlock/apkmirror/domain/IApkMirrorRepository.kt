package com.yeswolf.badlock.apkmirror.domain

import com.yeswolf.badlock.model.Plugin

interface IApkMirrorRepository {
    fun updateVersionInfo(plugin: Plugin): Plugin
}