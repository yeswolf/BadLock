package com.yeswolf.badlock.model

import com.yeswolf.badlock.R

val apkMirrorURL = "https://www.apkmirror.com"
var samsungCategory = "samsung-electronics-co-ltd"

var plugins = listOf(
    Plugin("LockStar", "lockstar", R.drawable.lockstar, "com.samsung.systemui.lockstar"),
    Plugin("QuickStar", "quickstar", R.drawable.quickstar, "com.samsung.android.qstuner"),
    Plugin(
        "Task Changer",
        "task-changer",
        R.drawable.taskchanger,
        "com.samsung.android.pluginrecents"
    ),
    Plugin(
        "Clockface",
        "samsung-clockface",
        R.drawable.clockface,
        "com.samsung.android.app.clockface"
    ),
    Plugin("MultiStar", "samsung-multistar", R.drawable.multistar, "com.samsung.android.multistar"),
    Plugin("Nice Shot", "nice-shot", R.drawable.niceshot, "com.samsung.android.app.captureplugin"),
    Plugin("NotiStar", "notistar", R.drawable.notistar, "com.samsung.systemiu.notilus")
)