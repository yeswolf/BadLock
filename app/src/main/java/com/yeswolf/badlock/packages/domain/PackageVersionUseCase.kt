package com.yeswolf.badlock.packages.domain

import com.yeswolf.badlock.apkmirror.domain.Version
import toothpick.InjectConstructor

@InjectConstructor
class PackageVersionUseCase(
    private val packagesRepository: IPackagesRepository
) {
    operator fun invoke(packageName: String): Version? =
        packagesRepository.getPackageVersion(packageName)
}