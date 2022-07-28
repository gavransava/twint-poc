package com.example.twintpoc.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.repository.usecase.GetCountryHolidaysUseCase
import com.example.twintpoc.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCountryHolidaysUseCase: GetCountryHolidaysUseCase
) : BaseViewModel() {

    private val holidayPrivate = MutableLiveData<Resource<List<Holiday>>>()
    val holidayData: LiveData<Resource<List<Holiday>>> get() = holidayPrivate

    fun requestHolidays(country: String, year: String) = viewModelScope.launch {
        holidayPrivate.value = Resource.Loading()
        getCountryHolidaysUseCase.requestHolidays(country, year).collect {
            holidayPrivate.value = it
        }
    }
}