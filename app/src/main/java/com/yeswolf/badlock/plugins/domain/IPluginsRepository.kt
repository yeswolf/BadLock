package com.yeswolf.badlock.plugins.domain

import com.yeswolf.badlock.model.Plugin
import io.reactivex.rxjava3.core.Single

interface IPluginsRepository {
    fun getPlugins(): List<Plugin>
    fun getPluginsAsync(): Single<List<Plugin>>
}