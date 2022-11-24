package com.actia.myapplication.di

import com.actia.myapplication.data.repository.network.ItemRepositoryAPI
import com.actia.myapplication.data.repository.network.ItemRepositoryAPIImpl
import com.actia.myapplication.data.repository.mappers.ItemsMapper
import com.actia.myapplication.data.service.RetrofitItemsEndpoints
import com.actia.myapplication.data.service.RetrofitItemsProvider
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class RepositoryModules : KoinModules {

    companion object
    {

    }

    override fun get(): List<Module> {
        return listOf(getItemsRepository())
    }

    private fun getItemsRepository(): Module {
        return module(override = true) {
            single {
                ItemRepositoryAPIImpl.getInstance(
                    feedsEndpoint = RetrofitItemsProvider.createService(
                        RetrofitItemsEndpoints::class.java),

                    itemMapper = ItemsMapper()
                    )

            } bind ItemRepositoryAPI::class
        }
    }
}