package com.yeswolf.badlock.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.apkmirror.domain.GetPluginVersionsUseCase
import com.yeswolf.badlock.packages.domain.PackageUpdatesUseCase
import com.yeswolf.badlock.packages.domain.PackageVersionUseCase
import com.yeswolf.badlock.plugins.PluginsListUseCase
import com.yeswolf.badlock.plugins.data.Plugin
import com.yeswolf.badlock.rx.SingleLiveData
import com.yeswolf.badlock.schedulers.ISchedulersProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import timber.log.Timber
import toothpick.InjectConstructor

@InjectConstructor
class ApkListViewModel(
    listPlugins: PluginsListUseCase,
    schedulersProvider: ISchedulersProvider,
    packageVersion: PackageVersionUseCase,
    private val getPluginVersions: GetPluginVersionsUseCase,
    private val packageUpdates: PackageUpdatesUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var items by mutableStateOf(emptyList<Plugin>())

    var loading = mutableStateOf(false)

    private val onErrorLiveData: SingleLiveData<String> = SingleLiveData()
    val onError: LiveData<String> =
        onErrorLiveData

    init {
        onLoadingUpdated(true)
        compositeDisposable +=
            listPlugins()
                .doOnSuccess { items = items + it }
                .flatMapObservable { Observable.fromIterable(it) }
                .doOnNext { it.versions = getPluginVersions(it) }
                .observeOn(schedulersProvider.mainThread)
                .subscribe({ },
                    {
                        Timber.e(it)
                        onErrorLiveData.value = "Error loading update information: $it"
                        onLoadingUpdated(false)
                    }, {
                        onLoadingUpdated(false)
                    })
        packageUpdates.startListen()

        compositeDisposable +=
            packageUpdates()
                .observeOn(schedulersProvider.mainThread)
                .doOnNext { onLoadingUpdated(true) }
                .observeOn(schedulersProvider.io)
                .doOnNext { packageName ->
                    items.firstOrNull { it.packageName == packageName }
                        ?.let { it.installedVersion = packageVersion(packageName = packageName) }
                }
                .observeOn(schedulersProvider.mainThread)
                .subscribe({ onLoadingUpdated(false) }, Timber::e, {})
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        packageUpdates.stopListen()
    }

    fun onLoadingUpdated(loading: Boolean) {
        this.loading.value = loading
    }
}