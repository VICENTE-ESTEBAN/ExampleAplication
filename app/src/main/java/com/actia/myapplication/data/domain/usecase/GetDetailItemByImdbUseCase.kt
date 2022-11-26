package com.actia.myapplication.data.domain.usecase

import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPI
import io.reactivex.Observable
import org.koin.core.component.KoinComponent

class GetDetailItemUseCase(private val detailItemRepository: DetailItemRepositoryAPI): KoinComponent {

    fun execute(apiKey:String, title:String): Observable<Result<Item>> {
        return detailItemRepository.getItemByImdb(apiKey, title)
            .toObservable()
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}