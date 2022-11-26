package com.actia.myapplication.data.domain.usecase

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPI
import io.reactivex.Observable

class GetDetailItemByTitleUseCase(private val detailItemRepository: DetailItemRepositoryAPI) {

    fun execute(apiKey:String, title:String): Observable<Result<DetailItem>> {
        return detailItemRepository.getItemByTitle(apiKey, title)
            .toObservable()
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}