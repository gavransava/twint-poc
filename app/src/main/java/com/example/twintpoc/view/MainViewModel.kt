package com.example.twintpoc.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.repository.usecase.GetCountryHolidaysUseCase
import com.example.twintpoc.util.SingleEvent
import com.example.twintpoc.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCountryHolidaysUseCase: GetCountryHolidaysUseCase
) : BaseViewModel() {

    var firstCountryCode : String = ""
    var secondCountryCode : String = ""

    private val holidayPrivate = MutableLiveData<SingleEvent<Resource<List<Holiday>>>>()
    val holidayData: LiveData<SingleEvent<Resource<List<Holiday>>>> get() = holidayPrivate

    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun requestHolidays(country1: String, country2: String, year: String) = viewModelScope.launch {
        holidayPrivate.value = SingleEvent(Resource.Loading())
        firstCountryCode = country1
        secondCountryCode = country2
        if (country1 == country2) {
            getCountryHolidaysUseCase.requestHolidays(country1, year).collect {
                holidayPrivate.value = SingleEvent(it)
            }
        } else {
            val deferred1 = viewModelScope.async {
                getCountryHolidaysUseCase.requestHolidays(country1, year).collect {
                    holidayPrivate.value = SingleEvent(it)
                }
            }
            val deferred2 = viewModelScope.async {
                getCountryHolidaysUseCase.requestHolidays(country2, year).collect {
                    holidayPrivate.value = SingleEvent(it)
                }
            }
            deferred1.await()
            deferred2.await()
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}