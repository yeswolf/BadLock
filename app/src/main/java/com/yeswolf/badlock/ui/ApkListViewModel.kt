package com.yeswolf.badlock.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.SingleLiveData
import com.yeswolf.badlock.apkmirror.domain.UpdateVersionInfoUseCase
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.plugins
import com.yeswolf.badlock.packages.domain.PackageVersionUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import toothpick.InjectConstructor

@InjectConstructor
class ApkListViewModel(
    private val updateVersionInfoUseCase: UpdateVersionInfoUseCase,
    private val packageVersionUseCase: PackageVersionUseCase
) : ViewModel() {

    var items by mutableStateOf(plugins)
    var loading = mutableStateOf(false)

    private val onErrorLiveData: SingleLiveData<String> = SingleLiveData()
    val onError: LiveData<String> =
        onErrorLiveData


    init {

        // TODO move this logic into usecase
        plugins.toObservable()
            .doOnNext {
                try {
                    it.installedVersion = packageVersionUseCase(it.packageName)
                } catch (e: Exception) {
                }
            }
            .map { updateVersionInfoUseCase(it) }

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