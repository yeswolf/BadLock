package com.yeswolf.badlock.network

import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.model.apkMirrorURL
import com.yeswolf.badlock.model.samsungCategory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ApkMirror {
    companion object {
        fun pluginURL(plugin: Plugin): String {
            return "$apkMirrorURL/apk/$samsungCategory/${plugin.serverRoot}"
        }

        fun latestVersionHTMLDownloadURL(plugin: Plugin): String {
            return versionHTMLDownloadURL(plugin, plugin.versions.first())
        }

        fun versionHTMLDownloadURL(plugin: Plugin, version: Version): String {
            val versionPart = version.defised()
            var url = "$apkMirrorURL${version.url}"
            if (plugin.serverRoot != "nice-catch" && plugin.serverRoot != "soundassistant" && plugin.serverRoot != "home-up" && plugin.serverRoot != "pentastic") {
                url += "${plugin.vendor.toLowerCase()}-"
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
            return "$apkMirrorURL${here}"
        }

        fun latestVersionDirectDownloadURL(plugin: Plugin): String {
            return versionDirectDownloadURL(plugin, plugin.versions.first())
        }

        fun versionInfo(plugin: Plugin): Plugin {
            val pluginURL = pluginURL(plugin)
            val doc = documentFromURL(pluginURL)
            val versionNames = doc.select(".listWidget").first {
                it.select(".widgetHeader").text().contains("All versions")
            }.select(".fontBlack")
                .filter {
                    it.attr("href").contains("/apk")
                }
                .map {
                    Pair(it.text(), it.attr("href"))
                }
            plugin.versions = versionNames.map {
                val parts = it.first.replace("(READ NOTES)", "").trim().split(" ")
                val versionPart = parts.last()
                Version(versionPart, it.second)
            }.toTypedArray()
            return plugin
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
}