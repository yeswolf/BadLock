package com.yeswolf.badlock.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.ISchedulersProvider
import com.yeswolf.badlock.apkmirror.domain.UpdateVersionInfoUseCase
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.plugins.PluginsListUseCase
import com.yeswolf.badlock.rx.SingleLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import timber.log.Timber
import toothpick.InjectConstructor

@InjectConstructor
class ApkListViewModel(
    pluginsListUseCase: PluginsListUseCase,
    private val updateVersionInfo: UpdateVersionInfoUseCase,
    schedulersProvider: ISchedulersProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var items by mutableStateOf(emptyList<Plugin>())

    var loading = mutableStateOf(false)

    private val onErrorLiveData: SingleLiveData<String> = SingleLiveData()
    val onError: LiveData<String> =
        onErrorLiveData

    init {
        compositeDisposable +=
            pluginsListUseCase()
                .doOnSuccess { items = items + it }
                .flatMapObservable { Observable.fromIterable(it) }
                .doOnNext { updateVersionInfo(it) }
                .observeOn(schedulersProvider.mainThread)
                .subscribe({ it.loading = false },
                    {
                        Timber.e(it)
                        onErrorLiveData.value = "Error loading update information: $it"
                        onLoadingUpdated(false)
                    }, {
                        onLoadingUpdated(false)
                    })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onLoadingUpdated(loading: Boolean, plugin: Plugin) {
        this.loading.value = loading
        plugin.loading = loading
    }

    fun onLoadingUpdated(loading: Boolean) {
        this.loading.value = loading
        this.items.forEach {
            it.loading = loading
        }
    }
}