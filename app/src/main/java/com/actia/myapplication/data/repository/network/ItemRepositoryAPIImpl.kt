package com.actia.myapplication.data.repository.network

import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.SearchResultDTO
import com.actia.myapplication.data.service.RetrofitOmdbEndpoints
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response


class ItemRepositoryAPIImpl(
    private val itemApiService: RetrofitOmdbEndpoints,
    private val itemDataMapper: Mapper<SearchResultDTO, List<Item>>
) : ItemRepositoryAPI
{
    override fun getItemsByName(apiKey:String, title: String): Single<Result<List<Item>>> {
        return Single.create { emitter ->
            val call: Call<SearchResultDTO> = itemApiService.getItemsByTitle(apiKey, title)

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

}