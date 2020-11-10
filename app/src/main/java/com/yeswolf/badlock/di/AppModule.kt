package com.yeswolf.badlock.di

import android.content.Context
import com.yeswolf.badlock.packages.data.PackagesRepository
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(context: Context) : Module() {
    init {
        bind<Context>().toInstance(context)
        bind<IPackagesRepository>().toClass<PackagesRepository>()
    }
}