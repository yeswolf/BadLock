package com.yeswolf.badlock.packages.data

import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import toothpick.InjectConstructor

@InjectConstructor
class PackagesRepository(
    private val packagesSource: PackagesSource
) : IPackagesRepository {

    override fun getPackageVersion(packageName: String): Version =
        Version(packagesSource.getPackageVersionName(packageName), "")
}