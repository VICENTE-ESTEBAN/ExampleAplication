package com.actia.myapplication.ui.base.viewmodel

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@OptIn(KoinApiExtension::class)
open class BaseViewModel(application: Application) : ApplicationViewModel(application), KoinComponent {


    protected val viewModelJob = Job()
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected var disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        disposables.clear()

        viewModelJob.cancel()
        //Primero cancelar jobs, después uiScope
        uiScope.coroutineContext.cancelChildren()
    }
}