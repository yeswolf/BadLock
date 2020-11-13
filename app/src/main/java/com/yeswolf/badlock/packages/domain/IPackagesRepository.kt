package com.yeswolf.badlock.packages.domain

import com.yeswolf.badlock.apkmirror.domain.Version

interface IPackagesRepository {
    fun getPackageVersion(packageName: String) : Version?
}