package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.ItemDTO
import com.actia.myapplication.data.service.RetrofitOmdbEndpoints
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean


class DetailItemRepositoryAPIImpl(
    private val itemApiService: RetrofitOmdbEndpoints,
    private val itemDataMapper: Mapper<ItemDTO, DetailItem>
) : DetailItemRepositoryAPI
{
    override fun getItemByImdb(apiKey:String, imdb: String): Single<Result<DetailItem>> {
        return Single.create { emitter ->
            val call: Call<ItemDTO> = itemApiService.getItemByImdb(apiKey, imdb)

            val response: Response<ItemDTO> = call.execute()

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

    override fun getItemByTitle(apiKey:String, title: String): Single<Result<DetailItem>> {
        return Single.create { emitter ->
            val call: Call<ItemDTO> = itemApiService.getItemByTitle(apiKey, title)

            val response: Response<ItemDTO> = call.execute()

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

    private fun mapItems(result: ItemDTO): DetailItem {
        return itemDataMapper.map(result)
    }


    companion object Factory {
        private lateinit var INSTANCE: DetailItemRepositoryAPI
        private val initialized = AtomicBoolean()

        val TAG = DetailItemRepositoryAPIImpl::class.java.simpleName

        fun getInstance(
            feedsEndpoint: RetrofitOmdbEndpoints,
            itemMapper: Mapper<ItemDTO, DetailItem>
        ): DetailItemRepositoryAPI {
            if (initialized.compareAndSet(false, true)) {
                INSTANCE = DetailItemRepositoryAPIImpl(feedsEndpoint, itemMapper)
            }
            return INSTANCE
        }
    }

}