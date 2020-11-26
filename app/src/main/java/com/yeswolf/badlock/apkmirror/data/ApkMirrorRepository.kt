package com.yeswolf.badlock.apkmirror.data

import com.yeswolf.badlock.apkmirror.domain.IApkMirrorRepository
import com.yeswolf.badlock.apkmirror.domain.Version
import com.yeswolf.badlock.packages.mappers.VersionMapper
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.schedulers.ISchedulersProvider
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class ApkMirrorRepository(
    private val apkMirrorSource: ApkMirrorSource,
    private val versionMapper: VersionMapper,
    private val schedulersProvider: ISchedulersProvider
) : IApkMirrorRepository {

    override fun getPluginVersions(plugin: Plugin): List<Version> =
        apkMirrorSource.getPluginVersions(plugin).map {
            versionMapper.fromDto(it)
        }

    override fun getPluginDownloadUrl(plugin: Plugin): String {
        return apkMirrorSource.latestVersionDirectDownloadURL(plugin)
    }

    override fun getPluginDownloadUrlAsync(plugin: Plugin): Single<String> {
        return Single.fromCallable {
            getPluginDownloadUrl(plugin = plugin)
        }.subscribeOn(schedulersProvider.io)
    }
}