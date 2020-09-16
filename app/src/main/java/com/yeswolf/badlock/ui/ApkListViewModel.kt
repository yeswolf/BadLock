package com.yeswolf.badlock.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yeswolf.badlock.model.Plugin
import com.yeswolf.badlock.model.plugins

class ApkListViewModel : ViewModel() {
    var items by mutableStateOf(plugins)
    var loading = mutableStateOf(false)
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