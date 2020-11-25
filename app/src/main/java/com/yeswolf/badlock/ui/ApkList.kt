package com.yeswolf.badlock.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yeswolf.badlock.ui.viewmodel.ApkListViewModel
import com.yeswolf.badlock.ui.viewmodel.PluginItemViewModel

@Composable
fun ApkList(
    viewModel: ApkListViewModel
) {
    if (viewModel.loading.value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    LazyColumnFor(items = viewModel.items) {
        PluginItem(
            viewModel = PluginItemViewModel(
                plugin = it
            )
        )
        Divider()
    }
}

