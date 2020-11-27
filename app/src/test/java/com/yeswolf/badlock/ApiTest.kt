package com.yeswolf.badlock

import com.yeswolf.badlock.apkmirror.data.ApkMirrorRepository
import com.yeswolf.badlock.apkmirror.data.ApkMirrorSource
import com.yeswolf.badlock.packages.mappers.VersionMapper
import com.yeswolf.badlock.plugins.data.PluginsListSource
import com.yeswolf.badlock.schedulers.SchedulersProvider
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {
    @Test
    fun testPluginUrls() {
        //FIXME: of course, that's wrong, but we need to have at least something for now.
        val source = PluginsListSource()
        val apkMirrorRepository = ApkMirrorRepository(ApkMirrorSource, VersionMapper, SchedulersProvider())
        source.plugins.forEach {
            it.versions = apkMirrorRepository.getPluginVersions(it)
            val url = ApkMirrorSource.latestVersionDirectDownloadURL(it)
            println(it.name + " " + url)
        }
    }

}