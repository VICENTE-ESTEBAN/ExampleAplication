package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Item
import io.reactivex.Single
import com.actia.myapplication.data.domain.model.Result

interface DetailItemRepositoryAPI {
    fun getItemByImdb(apiKey:String, imdb:String): Single<Result<DetailItem>>
    fun getItemByTitle(apiKey:String, title:String): Single<Result<DetailItem>>
}