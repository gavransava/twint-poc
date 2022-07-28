package com.example.twintpoc.view.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twintpoc.databinding.FragmentCountryBinding
import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.util.observe
import com.example.twintpoc.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentCountryBinding


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
        viewModel.requestHolidays("RS", "2021")
    }

    private fun initObservers() {
        observe(viewModel.holidayData, ::handleHolidayData)
    }

    private fun handleHolidayData(holidayData: Resource<List<Holiday>>) {
        when (holidayData) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                holidayData.data?.let { holiday ->
                    holiday.forEach{
                        Log.d("Holidays",  it.name)
                    }
                }
            }
            is Resource.DataError -> {
            }
        }
    }
}