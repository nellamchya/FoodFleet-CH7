package com.shine.foodfleet.presentation.feature.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.utils.ResultWrapper
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }
    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()
    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue((menu?.menuPrice?.times(count) ?: 0).toDouble())
    }
    fun minus() {
        val count = (menuCountLiveData.value ?: 0) - 1
        if ((menuCountLiveData.value ?: 0) <= 0) return
        menuCountLiveData.postValue(count)
        priceLiveData.postValue((menu?.menuPrice?.times(count) ?: 0).toDouble())
    }

    fun addToCart() {
        viewModelScope.launch {
            val menuQuantity =
                if ((menuCountLiveData.value ?: 0) <= 0) {
                    return@launch
                } else {
                    menuCountLiveData.value ?: 0
                }
            menu?.let {
                cartRepository.createCart(it, menuQuantity).collect { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }
    }
}
