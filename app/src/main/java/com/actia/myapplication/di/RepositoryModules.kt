package com.actia.myapplication.di

import com.actia.myapplication.data.repository.mappers.ItemDTOToDetailMapper
import com.actia.myapplication.data.repository.network.ItemRepositoryAPI
import com.actia.myapplication.data.repository.network.ItemRepositoryAPIImpl
import com.actia.myapplication.data.repository.mappers.ItemsDtoToItemsMapper
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPI
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPIImpl
import com.actia.myapplication.data.service.RetrofitOmdbEndpoints
import com.actia.myapplication.data.service.RetrofitOmdbProvider
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class RepositoryModules : KoinModules {

    companion object
    {

    }

    override fun get(): List<Module> {
        return listOf(getItemsRepository(), getDetailItemRepository())
    }

    private fun getItemsRepository(): Module {
        return module(override = true) {
            single {
                ItemRepositoryAPIImpl.getInstance(
                    feedsEndpoint = RetrofitOmdbProvider.createService(
                        RetrofitOmdbEndpoints::class.java),

                    itemMapper = ItemsDtoToItemsMapper()
                    )

            } bind ItemRepositoryAPI::class
        }
    }

    private fun getDetailItemRepository(): Module {
        return module(override = true) {
            single {
                DetailItemRepositoryAPIImpl.getInstance(
                    feedsEndpoint = RetrofitOmdbProvider.createService(
                        RetrofitOmdbEndpoints::class.java),

                    itemMapper = ItemDTOToDetailMapper()
                )

            } bind DetailItemRepositoryAPI::class
        }
    }

}