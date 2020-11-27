package com.yeswolf.badlock

import com.yeswolf.badlock.apkmirror.data.ApkMirrorRepository
import com.yeswolf.badlock.apkmirror.data.ApkMirrorSource
import com.yeswolf.badlock.packages.mappers.VersionMapper
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.plugins.data.PluginsListSource
import com.yeswolf.badlock.schedulers.SchedulersProvider
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {
    private val source = PluginsListSource()
    private val apkMirrorRepository =
        ApkMirrorRepository(ApkMirrorSource, VersionMapper, SchedulersProvider())

    @Test
    fun testKeysCafe() = testPlugin(
        Plugin(
            name = "Keys Cafe",
            serverRoot = "keys-cafe",
            vendorToken = "",
            serverCompanyNameRoot = "good-lock-labs",
            icon = R.drawable.keyscafe,
            packageName = "com.samsung.android.keyscafe",
            className = "com.samsung.android.keyscafe.main.ui.KeysCafeSplashActivity"
        )
    )

    @Test
    fun testWonderLand() = testPlugin(
        Plugin(
            name = "Wonderland",
            serverRoot = "wonderland",
            vendorToken = "",
            icon = R.drawable.wonderland,
            packageName = "com.samsung.android.wonderland.wallpaper",
            className = "com.samsung.android.wonderland.wallpaper.settings.StartupActivity"
        )
    )

    @Test
    fun testAppBooster() = testPlugin(
        Plugin(
            name = "App Booster",
            serverRoot = "app-booster",
            vendorToken = "samsung-galaxy",
            icon = R.drawable.appbooster,
            packageName = "com.samsung.android.appbooster",
            className = "com.samsung.android.appbooster.app.presentation.home.HomeActivity"
        )
    )

    @Test
    fun testAll() {
        source.plugins.forEach {
            testPlugin(it)
        }
    }

    private fun testPlugin(plugin: Plugin) {
        plugin.versions = apkMirrorRepository.getPluginVersions(plugin = plugin)
        val url = ApkMirrorSource.latestVersionDirectDownloadURL(plugin)
        println(plugin.name + " " + url)
    }

}