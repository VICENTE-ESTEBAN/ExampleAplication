package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Result
import io.reactivex.Single

interface DetailItemRepositoryAPI {
    fun getItemByImdb(apiKey:String, imdb:String): Single<Result<DetailItem>>
    fun getItemByTitle(apiKey:String, title:String): Single<Result<DetailItem>>
}