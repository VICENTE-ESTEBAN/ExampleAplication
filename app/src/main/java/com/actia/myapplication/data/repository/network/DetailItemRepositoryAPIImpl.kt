package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.SearchResultDTO
import com.actia.myapplication.data.service.RetrofitItemsEndpoints
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean


class ItemRepositoryAPIImpl(
    private val itemApiService: RetrofitItemsEndpoints,
    private val itemDataMapper: Mapper<SearchResultDTO, List<Item>>
) : ItemRepositoryAPI
{
    override fun getItemsByName(apiKey:String, name: String): Single<Result<List<Item>>> {
        return Single.create { emitter ->
            val call: Call<SearchResultDTO> = itemApiService.getItemsByTitle(apiKey, name)

            val response: Response<SearchResultDTO> = call.execute()

            if (!emitter.isDisposed) {
                if (response.isSuccessful && response.body() != null) {

                   emitter.onSuccess(Result.Success(mapItems(response.body()!!)))

                } else {
                    // Handle error
                    val errorBody: String? = response.errorBody()?.string()

                    emitter.onError(Throwable(errorBody))
                }
            }
        }
    }

    private fun mapItems(result: SearchResultDTO): List<Item> {
        return itemDataMapper.map(result)
    }


    companion object Factory {
        private lateinit var INSTANCE: ItemRepositoryAPI
        private val initialized = AtomicBoolean()

        val TAG = ItemRepositoryAPIImpl::class.java.simpleName

        fun getInstance(
            feedsEndpoint: RetrofitItemsEndpoints,
            itemMapper: Mapper<SearchResultDTO, List<Item>>
        ): ItemRepositoryAPI {
            if (initialized.compareAndSet(false, true)) {
                INSTANCE = ItemRepositoryAPIImpl(feedsEndpoint, itemMapper)
            }
            return INSTANCE
        }
    }

}