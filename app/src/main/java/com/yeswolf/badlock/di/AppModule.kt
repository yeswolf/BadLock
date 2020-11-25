package com.yeswolf.badlock.di

import android.content.Context
import com.yeswolf.badlock.schedulers.ISchedulersProvider
import com.yeswolf.badlock.schedulers.SchedulersProvider
import com.yeswolf.badlock.packages.data.PackagesRepository
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import com.yeswolf.badlock.packages.mappers.VersionMapper
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(context: Context) : Module() {
    init {
        bind<Context>().toInstance(context)
        bind<IPackagesRepository>().toClass<PackagesRepository>()
        bind<VersionMapper>().toInstance(VersionMapper)
        bind<ISchedulersProvider>().toClass<SchedulersProvider>()
    }
}