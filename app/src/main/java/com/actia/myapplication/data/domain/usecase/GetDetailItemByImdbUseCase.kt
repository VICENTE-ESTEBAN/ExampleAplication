package com.actia.myapplication.data.domain.usecase

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPI
import io.reactivex.Observable

class GetDetailItemByImdbUseCase(private val detailItemRepository: DetailItemRepositoryAPI) {

    fun execute(apiKey:String, imdb:String): Observable<Result<DetailItem>> {
        return detailItemRepository.getItemByImdb(apiKey, imdb)
            .toObservable()
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}