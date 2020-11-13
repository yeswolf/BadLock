package com.yeswolf.badlock.packages.data

import com.yeswolf.badlock.model.Version
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import com.yeswolf.badlock.packages.mappers.VersionMapper
import toothpick.InjectConstructor

@InjectConstructor
class PackagesRepository(
    private val packageVersionSource: PackageVersionSource,
    private val versionMapper: VersionMapper
) : IPackagesRepository {



    override fun getPackageVersion(packageName: String): Version? =
        packageVersionSource.getPackageVersionName(packageName)
            ?.let(versionMapper::toDto)

}