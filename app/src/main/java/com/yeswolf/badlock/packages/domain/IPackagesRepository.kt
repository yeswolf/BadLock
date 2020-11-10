package com.yeswolf.badlock.packages.domain

import com.yeswolf.badlock.model.Version

interface IPackagesRepository {
    fun getPackageVersion(packageName: String) : Version
}