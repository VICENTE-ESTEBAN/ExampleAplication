package com.actia.myapplication.di

import com.actia.myapplication.ui.main.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class AppModules : KoinModules {
    override fun get(): List<Module> {
        return listOf(getViewModels())
    }

    private fun getViewModels() = module {
        viewModel(override = true) { MainViewModel(androidApplication()) }
    }
}