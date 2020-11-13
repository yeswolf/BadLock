package com.yeswolf.badlock.packages.domain

import com.yeswolf.badlock.model.Version
import io.reactivex.rxjava3.core.Observable

interface IPackagesRepository {
    fun getPackageVersion(packageName: String) : Version?
}