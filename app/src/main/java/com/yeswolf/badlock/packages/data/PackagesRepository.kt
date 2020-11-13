package com.yeswolf.badlock.packages.data

import com.yeswolf.badlock.apkmirror.domain.Version
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import com.yeswolf.badlock.packages.mappers.VersionMapper
import toothpick.InjectConstructor

@InjectConstructor
class PackagesRepository(
    private val packageVersionSource: PackageVersionSource,
    private val versionMapper: VersionMapper
) : IPackagesRepository {



    override fun getPackageVersion(packageName: String): Version? =
        packageVersionSource.getPackageVersion(packageName)
            ?.let(versionMapper::fromDto)

}