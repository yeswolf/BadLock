package com.yeswolf.badlock

import com.yeswolf.badlock.model.plugins
import com.yeswolf.badlock.network.ApkMirror
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {
    @Test
    fun testPluginUrls() {
        plugins.forEach {
            var plug = ApkMirror.versionInfo(it)
//            val latestVersionDownloadURL = ApkMirror.latestVersionDirectDownloadURL(plug)
            val pluginUrl = ApkMirror.pluginURL(plug)
            println(pluginUrl)
        }

    }

}