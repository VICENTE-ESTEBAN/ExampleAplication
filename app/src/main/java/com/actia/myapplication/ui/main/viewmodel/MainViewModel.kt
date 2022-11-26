package com.actia.myapplication.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.usecase.GetItemsUseCase
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.domain.usecase.GetDetailItemByImdbUseCase
import com.actia.myapplication.data.domain.usecase.GetDetailItemByTitleUseCase
import com.actia.myapplication.data.repository.network.DetailItemRepositoryAPIImpl
import com.actia.myapplication.ui.base.viewmodel.BaseViewModel
import com.actia.myapplication.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject

@OptIn(KoinApiExtension::class)
class MainViewModel(application: Application) : BaseViewModel(application)
{
    companion object
    {
        val TAG = MainViewModel::class.java.simpleName
    }

    private val getItemsUseCase:GetItemsUseCase by inject()
    private val getDetailItemByImdbUseCase: GetDetailItemByImdbUseCase by inject()
    private val getDetailItemByTitleUseCase: GetDetailItemByTitleUseCase by inject()

   private val _getItemsLiveData: MutableLiveData<List<Item>> = MutableLiveData(emptyList())
    val getItemsLiveData :LiveData<List<Item>> = _getItemsLiveData

    private val _hasErrorOnRequestiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasErrorOnRequestiveData :LiveData<Boolean> = _hasErrorOnRequestiveData


    private val _getDetailItemLiveData: MutableLiveData<DetailItem> = MutableLiveData()
    val getDetailItemLiveData :LiveData<DetailItem> = _getDetailItemLiveData



    fun loadItems(title:String){
        getItemsUseCase.execute(Constants.APIKEY, title)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleGetItemsUseCase(it)
            }
            .addTo(disposables)
    }

    fun canGetDetail(data:Item?):Boolean{

        return if(!data?.imdb.isNullOrEmpty()) {
            getDetailItemByImdb(data?.imdb!!)
            true
        } else if(!data?.title.isNullOrEmpty()) {
            getDetailItemByTitle(data?.title!!)
            true
        } else {
            false
        }
    }

    private fun getDetailItemByImdb(imdb:String) {
        getDetailItemByImdbUseCase.execute(Constants.APIKEY, imdb)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleGetDetailItemUseCase(it)
            }
            .addTo(disposables)
    }

    private fun getDetailItemByTitle(title:String) {
        getDetailItemByTitleUseCase.execute(Constants.APIKEY, title)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleGetDetailItemUseCase(it)
            }
            .addTo(disposables)
    }

    private fun handleGetDetailItemUseCase(result: Result<DetailItem>) {
        when (result)
        {
            is Result.Success<DetailItem>->{
                _getDetailItemLiveData.value = result.value
            }
            is Result.Failure<DetailItem>->{
                sendError(result.throwable.message)
            }
        }
    }

    private fun handleGetItemsUseCase(result: Result<List<Item>>) {
        when (result)
        {
            is Result.Success<List<Item>>->{
                _getItemsLiveData.value = result.value
            }
            is Result.Failure<List<Item>>->{
                sendError(result.throwable.message)
            }
        }
    }

    private fun sendError(error: String?) {
        error?.let { Log.e(TAG, it) }

        _hasErrorOnRequestiveData.value = true
    }
}


