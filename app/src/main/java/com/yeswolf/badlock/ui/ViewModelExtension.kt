package com.yeswolf.badlock.ui

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import toothpick.Scope

inline fun <reified VM : ViewModel> ViewModelStoreOwner.provideViewModel(
    owner: SavedStateRegistryOwner,
    crossinline lazyScope: () -> Scope,
    arguments: Bundle? = null
): VM =
    ViewModelProvider(
        this,
        @Suppress("UNCHECKED_CAST")
        object : AbstractSavedStateViewModelFactory(owner, arguments) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return lazyScope.invoke().getInstance(VM::class.java) as T
            }
        }
    ).get(VM::class.java)
