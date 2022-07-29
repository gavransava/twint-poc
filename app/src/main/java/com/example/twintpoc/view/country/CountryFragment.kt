package com.example.twintpoc.view.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.twintpoc.data.dto.HolidayDataDto
import com.example.twintpoc.data.dto.HolidayDto
import com.example.twintpoc.databinding.FragmentCountryBinding
import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.util.*
import com.example.twintpoc.view.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentCountryBinding

    val holidayDataDto = HolidayDataDto()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.fetchHolidays.setOnClickListener {
            viewModel.requestHolidays(binding.country1.selectedCountryNameCode, binding.country2.selectedCountryNameCode, HOLIDAY_YEAR)
            holidayDataDto.countryOneName = binding.country1.selectedCountryEnglishName
            holidayDataDto.countryTwoName = binding.country2.selectedCountryEnglishName
        }
    }

    private fun initObservers() {
        observe(viewModel.holidayData, ::handleHolidayData)
        observeToast(viewModel.showToast)
    }

    private fun handleHolidayData(holidayData: Resource<List<Holiday>>) {
        when (holidayData) {
            is Resource.Loading -> {
                binding.pbLoading.toVisible()
            }
            is Resource.Success -> {
                holidayData.data?.let { holidays ->
                    holidays.forEach{ holiday ->
                        if(holiday.country == binding.country1.selectedCountryNameCode){
                            holidayDataDto.countryOneHolidays.add(HolidayDto(holiday.name, holiday.observed))
                        }
                        if(holiday.country == binding.country2.selectedCountryNameCode){
                            holidayDataDto.countryTwoHolidays.add(HolidayDto(holiday.name, holiday.observed))
                        }
                        Log.d("Holidays",  holiday.name)
                    }
                }
                if(holidayDataDto.countryOneHolidays.isNotEmpty() && holidayDataDto.countryTwoHolidays.isNotEmpty()){
                    binding.pbLoading.toGone()
                 // goto holidayfragment
                }
            }
            is Resource.DataError -> {
                if (binding.pbLoading.isVisible) {
                    holidayData.errorCode?.let { viewModel.showToastMessage(it) }
                }
                binding.pbLoading.toGone()
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    companion object {
        // Api only supports years prior to current one for free.
        const val HOLIDAY_YEAR = "2021"
    }
}