package com.yeswolf.badlock.plugins.data

import com.yeswolf.badlock.R
import com.yeswolf.badlock.model.Plugin
import toothpick.InjectConstructor

@InjectConstructor
class PluginsListSource {
    val plugins = listOf(
        Plugin(
            "LockStar",
            "lockstar",
            R.drawable.lockstar,
            "com.samsung.systemui.lockstar",
            "com.samsung.systemui.lockstar.settings.main.MainActivity"
        ),
        Plugin(
            "QuickStar",
            "quickstar",
            R.drawable.quickstar,
            "com.samsung.android.qstuner",
            "com.samsung.android.qstuner.QsTunerActivity"
        ),
        Plugin(
            "Task Changer",
            "task-changer",
            R.drawable.taskchanger,
            "com.samsung.android.pluginrecents",
            "com.samsung.android.pluginrecents.setting.SettingsActivity"
        ),
        Plugin(
            "Clockface",
            "samsung-clockface",
            R.drawable.clockface,
            "com.samsung.android.app.clockface",
            "com.samsung.android.app.clockface.setting.ClockFaceSetting"
        ),
        Plugin(
            "MultiStar",
            "samsung-multistar",
            R.drawable.multistar,
            "com.samsung.android.multistar",
            "com.samsung.android.multistar.view.MainActivity"
        ),
        Plugin(
            "Nice Shot",
            "nice-shot",
            R.drawable.niceshot,
            "com.samsung.android.app.captureplugin",
            "com.samsung.android.app.captureplugin.settings.ui.CapturePlugInSettingActivity"
        ),
        Plugin(
            "NotiStar",
            "notistar",
            R.drawable.notistar,
            "com.samsung.systemui.notilus",
            "com.samsung.systemui.notilus.NotiCenterPage"
        ),
        Plugin(
            "NavStar",
            "samsung-navstar",
            R.drawable.navstar,
            "com.samsung.systemui.navillera",
            "com.samsung.systemui.navillera.presentation.view.MainSettingActivity"
        ),
        Plugin(
            "Edge Lighting+",
            "edgelighting",
            R.drawable.edgelighting,
            "com.samsung.android.edgelightingeffectunit",
            "com.samsung.android.edgelightingeffectunit.activity.EdgeLightingUnitActivity"
        ),
        Plugin(
            "One Hand Operation+",
            "one-hand-operation",
            R.drawable.onehand,
            "com.samsung.android.sidegesturepad",
            "com.samsung.android.sidegesturepad.settings.SGPSettingsActivity"
        ),
        Plugin(
            "Edge Touch",
            "edge-touch",
            R.drawable.edgetouch,
            "com.samsung.android.app.edgetouch",
            "com.samsung.android.app.edgetouch.ui.activity.MainActivity"
        ),
        Plugin(
            "Nice Catch",
            "nice-catch",
            R.drawable.nicecatch,
            "com.samsung.android.app.goodcatch",
            "com.samsung.android.app.goodcatch.common.MainActivity"
        ),
        Plugin(
            "Sound Assistant",
            "soundassistant",
            R.drawable.soundassistant,
            "com.samsung.android.soundassistant",
            "com.sec.android.soundassistant.activities.MainActivity"
        ),
        Plugin(
            "Theme Park",
            "samsung-theme-park",
            R.drawable.themepark,
            "com.samsung.android.themedesigner",
            "com.samsung.android.themedesigner.MainThemeActivity"
        ),
        Plugin(
            "Home Up",
            "home-up",
            R.drawable.homeup,
            "com.samsung.android.app.homestar",
            "com.samsung.android.app.homestar.SettingActivity"
        ),
        Plugin(
            "Pentastic",
            "pentastic",
            R.drawable.pentastic,
            "com.samsung.android.pentastic",
            "com.samsung.android.pentastic.settings.SettingsActivity"
        )
    )
}