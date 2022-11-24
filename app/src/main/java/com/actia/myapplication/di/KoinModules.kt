package com.actia.myapplication.di

import org.koin.core.module.Module

interface KoinModules {
    fun get(): List<Module>

    operator fun plus(module: KoinModules) = listOf(this, module)
}