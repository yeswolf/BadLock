package com.yeswolf.badlock.packages.domain

import com.yeswolf.badlock.apkmirror.domain.Version
import io.reactivex.rxjava3.core.Observable

interface IPackagesRepository {
    val updates: Observable<String>
    fun startListen()
    fun stopListen()
    fun getPackageVersion(packageName: String) : Version?
}