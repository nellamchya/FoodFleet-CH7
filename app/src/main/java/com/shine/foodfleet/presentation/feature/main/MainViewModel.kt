package com.shine.foodfleet.presentation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {
    val userDarkModeLiveData = userPreferenceDataSource.getUserDarkModePrefFlow().asLiveData(
        Dispatchers.IO)
}