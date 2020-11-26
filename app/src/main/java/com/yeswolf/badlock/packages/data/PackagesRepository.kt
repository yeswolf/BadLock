package com.yeswolf.badlock.packages.data

import com.yeswolf.badlock.apkmirror.domain.Version
import com.yeswolf.badlock.packages.domain.IPackagesRepository
import com.yeswolf.badlock.packages.mappers.VersionMapper
import io.reactivex.rxjava3.core.Observable
import toothpick.InjectConstructor

@InjectConstructor
class PackagesRepository(
    private val packagesSource: PackagesSource,
    private val versionMapper: VersionMapper
) : IPackagesRepository {

    override fun getPackageVersion(packageName: String): Version? =
        packagesSource.getPackageVersion(packageName)
            ?.let(versionMapper::fromDto)

    override val updates: Observable<String> = packagesSource.updates
    override fun startListen() = packagesSource.startListen()
    override fun stopListen() =  packagesSource.stopListen()
}