package com.yeswolf.badlock.plugins.data

import com.yeswolf.badlock.R
import toothpick.InjectConstructor

@InjectConstructor
class PluginsListSource {
    val plugins = listOf(
        Plugin(
            name = "LockStar",
            serverRoot = "lockstar",
            icon = R.drawable.lockstar,
            packageName = "com.samsung.systemui.lockstar",
            className = "com.samsung.systemui.lockstar.settings.main.MainActivity"
        ),
        Plugin(
            name = "QuickStar",
            serverRoot = "quickstar",
            icon = R.drawable.quickstar,
            packageName = "com.samsung.android.qstuner",
            className = "com.samsung.android.qstuner.QsTunerActivity"
        ),
        Plugin(
            name = "Task Changer",
            serverRoot = "task-changer",
            icon = R.drawable.taskchanger,
            packageName = "com.samsung.android.pluginrecents",
            className = "com.samsung.android.pluginrecents.setting.SettingsActivity"
        ),
        Plugin(
            name = "Clockface",
            serverRoot = "samsung-clockface",
            icon = R.drawable.clockface,
            packageName = "com.samsung.android.app.clockface",
            className = "com.samsung.android.app.clockface.setting.ClockFaceSetting"
        ),
        Plugin(
            name = "MultiStar",
            serverRoot = "samsung-multistar",
            icon = R.drawable.multistar,
            packageName = "com.samsung.android.multistar",
            className = "com.samsung.android.multistar.view.MainActivity"
        ),
        Plugin(
            name = "Nice Shot",
            serverRoot = "nice-shot",
            icon = R.drawable.niceshot,
            packageName = "com.samsung.android.app.captureplugin",
            className = "com.samsung.android.app.captureplugin.settings.ui.CapturePlugInSettingActivity"
        ),
        Plugin(
            name = "NotiStar",
            serverRoot = "notistar",
            icon = R.drawable.notistar,
            packageName = "com.samsung.systemui.notilus",
            className = "com.samsung.systemui.notilus.NotiCenterPage"
        ),
        Plugin(
            name = "NavStar",
            serverRoot = "samsung-navstar",
            icon = R.drawable.navstar,
            packageName = "com.samsung.systemui.navillera",
            className = "com.samsung.systemui.navillera.presentation.view.MainSettingActivity"
        ),
        Plugin(
            name = "Edge Lighting+",
            serverRoot = "edgelighting",
            icon = R.drawable.edgelighting,
            packageName = "com.samsung.android.edgelightingeffectunit",
            className = "com.samsung.android.edgelightingeffectunit.activity.EdgeLightingUnitActivity"
        ),
        Plugin(
            name = "One Hand Operation+",
            serverRoot = "one-hand-operation",
            icon = R.drawable.onehand,
            packageName = "com.samsung.android.sidegesturepad",
            className = "com.samsung.android.sidegesturepad.settings.SGPSettingsActivity"
        ),
        Plugin(
            name = "Edge Touch",
            serverRoot = "edge-touch",
            icon = R.drawable.edgetouch,
            packageName = "com.samsung.android.app.edgetouch",
            className = "com.samsung.android.app.edgetouch.ui.activity.MainActivity"
        ),
        Plugin(
            name = "Nice Catch",
            serverRoot = "nice-catch",
            vendorToken = "",
            icon = R.drawable.nicecatch,
            packageName = "com.samsung.android.app.goodcatch",
            className = "com.samsung.android.app.goodcatch.common.MainActivity"
        ),
        Plugin(
            name = "Sound Assistant",
            serverRoot = "soundassistant",
            vendorToken = "",
            icon = R.drawable.soundassistant,
            packageName = "com.samsung.android.soundassistant",
            className = "com.sec.android.soundassistant.activities.MainActivity"
        ),
        Plugin(
            name = "Theme Park",
            serverRoot = "samsung-theme-park",
            icon = R.drawable.themepark,
            packageName = "com.samsung.android.themedesigner",
            className = "com.samsung.android.themedesigner.MainThemeActivity"
        ),
        Plugin(
            name = "Home Up",
            serverRoot = "home-up",
            vendorToken = "",
            icon = R.drawable.homeup,
            packageName = "com.samsung.android.app.homestar",
            className = "com.samsung.android.app.homestar.SettingActivity"
        ),
        Plugin(
            name = "Pentastic",
            serverRoot = "pentastic",
            vendorToken = "",
            icon = R.drawable.pentastic,
            packageName = "com.samsung.android.pentastic",
            className = "com.samsung.android.pentastic.settings.SettingsActivity"
        ),
        Plugin(
            name = "Wonderland",
            serverRoot = "wonderland",
            vendorToken = "",
            icon = R.drawable.wonderland,
            packageName = "com.samsung.android.wonderland.wallpaper",
            className = "com.samsung.android.wonderland.wallpaper.settings.StartupActivity"
        ),
        Plugin(
            name = "Keys Cafe",
            serverRoot = "keys-cafe",
            vendorToken = "",
            serverCompanyNameRoot = "good-lock-labs",
            icon = R.drawable.keyscafe,
            packageName = "com.samsung.android.keyscafe",
            className = "com.samsung.android.keyscafe.main.ui.KeysCafeSplashActivity"
        ),
        Plugin(
            name = "File Guardian",
            serverRoot = "file-guardian",
            icon = R.drawable.fileguardian,
            packageName = "com.android.samsung.icebox",
            className = "com.android.samsung.icebox.app.presentation.home.HomeActivity"
        ),
        Plugin(
            name = "Battery Tracker",
            serverRoot = "battery-tracker",
            icon = R.drawable.batterytracker,
            packageName = "com.android.samsung.batteryusage",
            className = "com.android.samsung.batteryusage.app.presentation.batteryhistory.BatteryHistoryActivity"
        ),
        Plugin(
            name = "Battery Guardian",
            serverRoot = "battery-guardian",
            icon = R.drawable.batteryguardian,
            packageName = "com.samsung.android.statsd",
            className = "com.samsung.android.statsd.app.presentation.home.HomeActivity"
        ),
        Plugin(
            name = "App Booster",
            serverRoot = "app-booster",
            vendorToken = "samsung-galaxy",
            icon = R.drawable.appbooster,
            packageName = "com.samsung.android.appbooster",
            className = "com.samsung.android.appbooster.app.presentation.home.HomeActivity"
        )

    )
}