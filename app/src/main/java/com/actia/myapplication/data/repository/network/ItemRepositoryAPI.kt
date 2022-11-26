package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.Item
import io.reactivex.Single
import com.actia.myapplication.data.domain.model.Result

interface ItemRepositoryAPI {
    fun getItemsByName(apiKey:String, title:String): Single<Result<List<Item>>>
}