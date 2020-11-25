package com.yeswolf.badlock.schedulers

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulersProvider {
    val io: Scheduler
    val mainThread: Scheduler
    val computation: Scheduler
    val single: Scheduler
}