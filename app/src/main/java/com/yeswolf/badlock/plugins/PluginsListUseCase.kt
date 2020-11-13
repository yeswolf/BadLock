package com.yeswolf.badlock.plugins

import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import com.yeswolf.badlock.plugins.domain.IPluginsRepository
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class PluginsListUseCase(
    private val pluginsRepository: IPluginsRepository,
    private val packagesRepository: IPackagesRepository
) {

    private fun getInstalledVersionInfo(plugin: Plugin): Plugin =
        plugin.copy(
            installedVersion = packagesRepository.getPackageVersion(plugin.packageName)
        )

    operator fun invoke(): Single<List<Plugin>> =
        pluginsRepository.getPluginsAsync()
            .map { it.map(::getInstalledVersionInfo) }
}