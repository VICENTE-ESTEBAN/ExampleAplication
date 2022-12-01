package com.actia.myapplication.ui.base.viewmodel

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@OptIn(KoinApiExtension::class)
open class BaseViewModel(application: Application) : ApplicationViewModel(application), KoinComponent {

    protected var disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        disposables.clear()
    }
}