package com.shine.foodfleet.presentation.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.model.Cart
import com.shine.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutViewModel(
    private val cartRepo : CartRepository
) : ViewModel(){
    val cartListOrder = cartRepo.getUserCartData().asLiveData(Dispatchers.IO)

}
