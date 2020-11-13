package com.yeswolf.badlock.plugins.domain

import com.yeswolf.badlock.plugins.data.Plugin
import io.reactivex.rxjava3.core.Single

interface IPluginsRepository {
    fun getPlugins(): List<Plugin>
    fun getPluginsAsync(): Single<List<Plugin>>
}