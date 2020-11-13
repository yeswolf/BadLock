package com.yeswolf.badlock.di

import com.yeswolf.badlock.plugins.data.PluginsRepository
import com.yeswolf.badlock.plugins.domain.IPluginsRepository
import toothpick.config.Module
import toothpick.ktp.binding.bind

class PluginsModule : Module() {
    init {
        bind<IPluginsRepository>().toClass<PluginsRepository>()
    }
}