package com.actia.myapplication.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
import com.actia.myapplication.data.domain.usecase.GetDetailItemByImdbUseCase
import com.actia.myapplication.data.domain.usecase.GetDetailItemByTitleUseCase
import com.actia.myapplication.data.domain.usecase.GetItemsUseCase
import com.actia.myapplication.ui.base.viewmodel.BaseViewModel
import com.actia.myapplication.util.Constants
import com.actia.myapplication.util.Constants.SHOW_ALL_YEARS
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

    val getYearsLiveData : ObservableArrayList<String> = ObservableArrayList<String>()
    val selectedYearLiveData :MutableLiveData<Int> = MutableLiveData<Int>(-1)

    private val getItemsUseCase:GetItemsUseCase by inject()
    private val getDetailItemByImdbUseCase: GetDetailItemByImdbUseCase by inject()
    private val getDetailItemByTitleUseCase: GetDetailItemByTitleUseCase by inject()
    private val _getItemsLiveData: MutableLiveData<List<Item>> = MutableLiveData(emptyList())

    val getItemsLiveData :LiveData<List<Item>> = Transformations.map(selectedYearLiveData)
    {
            yearSelected ->
                val years = getYearsLiveData
                if (years.isNotEmpty() && yearSelected<years.size) {
                    val year = years[yearSelected]
                    filterItemsByYear(year)
                } else {
                    _getItemsLiveData.value
                }


    }

    private val _hasErrorOnRequestiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasErrorOnRequestiveData :LiveData<Boolean> = _hasErrorOnRequestiveData


    private val _getDetailItemLiveData: MutableLiveData<DetailItem> = MutableLiveData()
    val getDetailItemLiveData :LiveData<DetailItem> = _getDetailItemLiveData



    fun loadItems(title:String){

        getYearsLiveData.clear()
        _getItemsLiveData.value = emptyList()

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

    private fun getYearsFromItem(): List<String> {
        return _getItemsLiveData.value?.mapNotNull {
            it.releaseYear
        }?.distinct()?: emptyList()
    }

    private fun filterItemsByYear(year:String): List<Item> {
        return if(year == SHOW_ALL_YEARS)
        {
            _getItemsLiveData.value
        }
        else {
            _getItemsLiveData.value?.filter {
                it.releaseYear == year
            }?.toList()
        } ?: emptyList()
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

                getYearsLiveData.addAll(getYearsFromItem())
                if(result.value.isNotEmpty())
                    getYearsLiveData.add(0, SHOW_ALL_YEARS)
            }
            is Result.Failure<List<Item>>->{
                sendError(result.throwable.message)

                getYearsLiveData.clear()
            }
        }

        selectedYearLiveData.value = 0

    }

    private fun sendError(error: String?) {
        error?.let { Log.e(TAG, it) }

        _hasErrorOnRequestiveData.value = true
    }
}


