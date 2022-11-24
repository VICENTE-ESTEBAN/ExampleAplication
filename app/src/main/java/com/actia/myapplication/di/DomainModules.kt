package com.actia.myapplication.di

import com.actia.myapplication.data.domain.usecase.GetItemsUseCase
import com.actia.myapplication.data.repository.network.ItemRepositoryAPI
import org.koin.core.module.Module
import org.koin.dsl.module

class DomainModules : KoinModules {
    override fun get(): List<Module> {
        return listOf(getUseCases())
    }
    private fun getUseCases() = module {
       single {

           val itemsRepository: ItemRepositoryAPI by inject()

            GetItemsUseCase(itemsRepository)
        }
    }
}