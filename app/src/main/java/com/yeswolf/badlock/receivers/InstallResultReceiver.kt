package com.yeswolf.badlock.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yeswolf.badlock.di.Scopes
import com.yeswolf.badlock.packages.domain.PackageVersionUseCase
import com.yeswolf.badlock.ui.viewmodel.ApkListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

//FIXME: this is all wrong again. We don't need viewModel here
class InstallResultReceiver(
    var viewModel: ApkListViewModel,
) : BroadcastReceiver() {
    private val packageVersion: PackageVersionUseCase by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        KTP.openScope(Scopes.APP).inject(this)
        if (intent != null) {
            viewModel.onLoadingUpdated(true)
            viewModel.items.toObservable()
                .map { it.copy(installedVersion = packageVersion(it.packageName)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            viewModel.onLoadingUpdated(false)
        }
    }
}
