package com.actia.myapplication.data.domain.usecase

import android.util.Log
import com.actia.myapplication.data.model.Item
import com.actia.myapplication.data.model.Result
import com.actia.myapplication.data.repository.network.ItemRepositoryAPI
import io.reactivex.Observable
import org.koin.core.component.KoinComponent

class GetItemsUseCase(private val itemRepository: ItemRepositoryAPI): KoinComponent {



    fun execute(apiKey:String, title:String): Observable<Result<List<Item>>> {
        return itemRepository.getItemsByName(apiKey, title)
            .toObservable()
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}