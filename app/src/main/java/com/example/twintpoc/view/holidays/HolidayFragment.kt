package com.example.twintpoc.view.holidays

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.twintpoc.databinding.FragmentHolidayBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolidayFragment : Fragment() {
    private lateinit var binding: FragmentHolidayBinding

    private val args: HolidayFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(HolidayFragmentDirections.actionHolidayFragmentToCountryFragment())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHolidayBinding.inflate(inflater)
        binding.backButton.setOnClickListener {
            findNavController().navigate(HolidayFragmentDirections.actionHolidayFragmentToCountryFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.holidayData.countryOneHolidays.forEach { holiday ->
            Log.d("HolidayFragment", holiday.name)
        }
        args.holidayData.countryTwoHolidays.forEach { holiday ->
            Log.d("HolidayFragment", holiday.name)
        }
    }
}