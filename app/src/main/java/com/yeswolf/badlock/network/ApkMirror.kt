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
            return "$apkMirrorURL/apk/${plugin.serverRoot}/${plugin.serverRoot}-${versionPart}-release/${plugin.vendor.toLowerCase()}-${
                plugin.name.toLowerCase().replace(
                    " ",
                    "-"
                )
            }-${versionPart}-android-apk-download/download/"
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
                    it.text()
                }
            plugin.versions = versionNames.map {
                val parts = it.split(" ")
                val versionPart = parts.last()
                Version(versionPart)
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