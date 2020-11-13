package com.yeswolf.badlock.apkmirror.data

import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.apkmirror.domain.Version
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object ApkMirrorSource {
    private const val APK_MIRROR_URL = "https://www.apkmirror.com"
    private const val SAMSUNG_CATEGORY = "samsung-electronics-co-ltd"
    private const val VENDOR = "samsung"

    fun pluginURL(plugin: Plugin): String {
        return "$APK_MIRROR_URL/apk/$SAMSUNG_CATEGORY/${plugin.serverRoot}"
    }

    fun latestVersionHTMLDownloadURL(plugin: Plugin): String {
        return versionHTMLDownloadURL(plugin, plugin.versions.first())
    }

    fun versionHTMLDownloadURL(plugin: Plugin, version: Version): String {
        val versionPart = version.defised()
        var url = "$APK_MIRROR_URL${version.url}"
        if (plugin.serverRoot != "nice-catch" && plugin.serverRoot != "soundassistant" && plugin.serverRoot != "home-up" && plugin.serverRoot != "pentastic") {
            url += "${VENDOR}-"
        }
        url += "${
            plugin.serverRoot.replace(
                "samsung-",
                ""
            )
        }-${versionPart}-android-apk-download/download/"
        return url
    }

    fun versionDirectDownloadURL(plugin: Plugin, version: Version): String {
        val url = versionHTMLDownloadURL(plugin, version)
        val doc = documentFromURL(url)
        val here = doc.select("a").first {
            it.text() == "here"
        }.attr("href")
        return "$APK_MIRROR_URL${here}"
    }

    fun latestVersionDirectDownloadURL(plugin: Plugin): String {
        return versionDirectDownloadURL(plugin, plugin.versions.first())
    }

    fun getPluginVersions(plugin: Plugin): List<VersionData> {
        val pluginURL = pluginURL(plugin)
        val doc = documentFromURL(pluginURL)
        return doc.select(".listWidget").first {
            it.select(".widgetHeader").text().contains("All versions")
        }.select(".fontBlack")
            .filter {
                it.attr("href").contains("/apk")
            }
            .map {
                VersionData(
                    it.text().replace("(READ NOTES)", "").trim().split(" ").last(),
                    it.attr("href")
                )
            }
    }

    private fun documentFromURL(pluginURL: String): Document {
        return Jsoup.connect(pluginURL)
            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(10000)
            .get()
    }
}
