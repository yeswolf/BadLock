package com.yeswolf.badlock.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.yeswolf.badlock.ui.viewmodel.PluginItemViewModel

@Composable
fun PluginItem(viewModel: PluginItemViewModel) {
    val context = ContextAmbient.current
    Row(modifier = Modifier.clickable(onClick = {
        viewModel.onPreferences(context)
    }).padding(10.dp).fillMaxWidth())
    {
        Column(modifier = Modifier.padding(5.dp)) {
            Image(asset = imageResource(id = viewModel.icon), modifier = Modifier.width(48.dp))
        }
        Column(
            modifier = Modifier.padding(5.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = viewModel.name)
            if (viewModel.hasInstalledVersion) {
                Text(text = viewModel.installedVersion, color = Color.Blue)
            }
        }
        Column(
            modifier = Modifier.padding(5.dp)
                .fillMaxWidth()
                .fillMaxSize().align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.End,
        ) {
            if (viewModel.showInstallUpdateButton) {
                Button(enabled = !viewModel.loading.value, onClick = {
                    viewModel.onDownload(context = context)
                }) {
                    Text(viewModel.installUpdateButtonText)
                }
            }
        }
    }
    if (viewModel.loading.value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}