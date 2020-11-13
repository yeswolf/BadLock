package com.yeswolf.badlock

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import toothpick.InjectConstructor

@InjectConstructor
class SchedulersProvider : ISchedulersProvider {
    override val io: Scheduler
        get() = Schedulers.io()
    override val mainThread: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val computation: Scheduler
        get() = Schedulers.computation()
    override val single: Scheduler
        get() = Schedulers.single()
}