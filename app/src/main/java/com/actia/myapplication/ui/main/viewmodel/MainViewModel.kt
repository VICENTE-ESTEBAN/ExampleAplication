package com.actia.myapplication.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.actia.myapplication.data.domain.usecase.GetItemsUseCase
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.domain.model.Result
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
    private val getItemsUseCase:GetItemsUseCase by inject()

   private val _getItemsLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    val getItemsLiveData :LiveData<List<Item>> = _getItemsLiveData

    fun loadItems(title:String){
        getItemsUseCase.execute(Constants.APIKEY, title)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleGetItemsUseCase(it)
            }
            .addTo(disposables)
    }


    fun getDetailItem(index:Int): Item?{
        return with(_getItemsLiveData.value)
        {
            if (this!=null && this.isNotEmpty() && index< this.count()) {
                this[index]
            }
            else
                null
        }
    }

    private fun handleGetItemsUseCase(result: Result<List<Item>>) {
        when (result)
        {
            is Result.Success<List<Item>>->{
                _getItemsLiveData.value = result.value
            }
            is Result.Failure<List<Item>>->{

            }
        }
    }
}


