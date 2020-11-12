package com.yeswolf.badlock.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.R
import com.yeswolf.badlock.apkmirror.domain.UpdateVersionInfoUseCase
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.packages.domain.PackageVersionUseCase
import com.yeswolf.badlock.rx.SingleLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import toothpick.InjectConstructor

@InjectConstructor
class ApkListViewModel(
    private val updateVersionInfo: UpdateVersionInfoUseCase,
    private val packageVersion: PackageVersionUseCase
) : ViewModel() {

    var items by mutableStateOf(
        listOf(
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
    )
    var loading = mutableStateOf(false)

    private val onErrorLiveData: SingleLiveData<String> = SingleLiveData()
    val onError: LiveData<String> =
        onErrorLiveData


    init {
        //TODO move this logic into usecase

        //We need to first load versions asap
        //FIXME: Do we need threading here at all for loading package version? Should be pretty fast
        items.toObservable()
            .doOnNext {
                it.installedVersion = packageVersion(it.packageName)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        //And only after that update info
        items.toObservable()
            .doOnNext {
                updateVersionInfo(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ },
                {
                    Timber.e(it)
                    onErrorLiveData.value = "Error loading update information: $it"
                    onLoadingUpdated(false)
                }, {
                    onLoadingUpdated(false)
                })
    }

    fun onLoadingUpdated(loading: Boolean, plugin: Plugin) {
        this.loading.value = loading
        this.items[items.indexOf(plugin)].loading = loading
    }

    fun onLoadingUpdated(loading: Boolean) {
        this.loading.value = loading
        this.items.forEach {
            it.loading = loading
        }
    }
}