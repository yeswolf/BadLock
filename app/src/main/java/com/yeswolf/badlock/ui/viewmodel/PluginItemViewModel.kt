package com.yeswolf.badlock.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.apkmirror.domain.GetPluginDownloadUrlUseCase
import com.yeswolf.badlock.di.Scopes
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.schedulers.ISchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import timber.log.Timber
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class PluginItemViewModel(
    val plugin: Plugin,
    val onPreferences: () -> Unit,
    val onStartDownload: (Plugin, String) -> Unit,
) : ViewModel() {
    private val getDownloadUrl: GetPluginDownloadUrlUseCase by inject()
    private val schedulersProvider: ISchedulersProvider by inject()

    private val compositeDisposable = CompositeDisposable()

    val icon: Int = plugin.icon
    val name = plugin.name
    val installedVersion = "installed v.${plugin.installedVersion}"
    val hasInstalledVersion = plugin.installedVersion != null
    var loading = mutableStateOf(plugin.loading)

    init {
        KTP.openScope(Scopes.APP).inject(this)
    }

    val installUpdateButtonText: String
        get() {
            val newVersion = plugin.versions.first()
            var text = ""
            if (plugin.installedVersion != null) {
                if (newVersion.compareTo(plugin.installedVersion) == 1) {
                    text = "Update"
                }
            } else {
                text = "Install"
            }
            return text
        }
    val showInstallUpdateButton: Boolean
        get() {
            if (!plugin.versionsLoaded) {
                return false
            }
            val newVersion = plugin.versions.first()
            var showButton = false
            if (plugin.installedVersion != null) {
                if (newVersion.compareTo(plugin.installedVersion) == 1) {
                    showButton = true
                }
            } else {
                showButton = true
            }
            return showButton
        }

    fun onStartDownload() {
        updateLoading(true)
        compositeDisposable += getDownloadUrl(plugin = plugin)
            .observeOn(schedulersProvider.mainThread)
            .subscribe({
                onStartDownload(plugin, it)
                updateLoading(false)
            }, {
                Timber.e(it)
                updateLoading(false)
            })
    }

    private fun updateLoading(value: Boolean) {
        loading.value = value
        plugin.loading = value
        //TODO: why the plugin.loading is not updated when loading.value is set?
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}