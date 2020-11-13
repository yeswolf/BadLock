package com.yeswolf.badlock.plugins.data

import com.yeswolf.badlock.ISchedulersProvider
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.plugins.domain.IPluginsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable
import toothpick.InjectConstructor

@InjectConstructor
class PluginsRepository(
    private val pluginsListSource: PluginsListSource,
    private val schedulersProvider: ISchedulersProvider
) : IPluginsRepository {

    override fun getPlugins(): List<Plugin> =
        pluginsListSource.plugins

    override fun getPluginsAsync(): Single<List<Plugin>> =
        Single.just(pluginsListSource.plugins)
            .subscribeOn(schedulersProvider.io)

}