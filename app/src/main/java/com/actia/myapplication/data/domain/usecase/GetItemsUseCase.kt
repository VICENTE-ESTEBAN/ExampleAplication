package com.actia.myapplication.data.domain.usecase

import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.network.ItemRepositoryAPI
import io.reactivex.Observable

class GetItemsUseCase(private val itemRepository: ItemRepositoryAPI) {

    fun execute(apiKey:String, title:String): Observable<Result<List<Item>>> {
        return itemRepository.getItemsByName(apiKey, title)
            .toObservable()
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}