package com.actia.myapplication

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.actia.myapplication.di.AppModules
import com.actia.myapplication.di.DomainModules
import com.actia.myapplication.di.RepositoryModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication


class ApplicationActia : Application() {


    override fun onCreate() {
        super.onCreate()
        startMyKoin()
    }

    private fun startMyKoin(){
        val diApp = koinApplication {
            androidContext(this@ApplicationActia)
            modules(AppModules().get() + RepositoryModules().get() +  DomainModules().get())
        }

        startKoin(koinApplication = diApp)
    }
}