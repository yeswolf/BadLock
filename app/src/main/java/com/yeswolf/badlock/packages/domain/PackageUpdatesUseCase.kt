package com.yeswolf.badlock.packages.domain

import io.reactivex.rxjava3.core.Observable
import toothpick.InjectConstructor

@InjectConstructor
class PackageUpdatesUseCase (
    private val packagesRepository: IPackagesRepository
) {
    fun startListen() = packagesRepository.startListen()
    fun stopListen() = packagesRepository.stopListen()
    operator fun invoke(): Observable<String> = packagesRepository.updates

}