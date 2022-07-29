package com.example.twintpoc.view.holidays

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twintpoc.data.dto.HolidayDto
import com.example.twintpoc.databinding.FragmentHolidayBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolidayFragment : Fragment() {
    private lateinit var binding: FragmentHolidayBinding

    private val args: HolidayFragmentArgs by navArgs()
    private lateinit var rvAdapter: HolidayListAdapter

    private val onlyCountryOneHolidays: ArrayList<HolidayDto> = arrayListOf()
    private val commonHolidays: ArrayList<HolidayDto> = arrayListOf()
    private val onlyCountryTwoHolidays: ArrayList<HolidayDto> = arrayListOf()

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


        setupHolidayLists()
        setupButtons()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.rvHolidays.layoutManager = layoutManager
        rvAdapter = HolidayListAdapter(commonHolidays)
        binding.rvHolidays.adapter = rvAdapter
        binding.selectedLabel.text = args.holidayData.countryOneName + " ∩ " + args.holidayData.countryTwoName
    }

    private fun setupHolidayLists() {
        val countryOneHolidayDates: ArrayList<String> = arrayListOf()
        args.holidayData.countryOneHolidays.forEach { holidayOne ->
            countryOneHolidayDates.add(holidayOne.observedDate)

        }
        val countryTwoHolidayDates: ArrayList<String> = arrayListOf()
        args.holidayData.countryTwoHolidays.forEach { holidayTwo ->
            countryTwoHolidayDates.add(holidayTwo.observedDate)
        }

        args.holidayData.countryOneHolidays.forEach { holidayOne ->
            if (countryTwoHolidayDates.contains(holidayOne.observedDate)) {
                commonHolidays.add(holidayOne)
            } else {
                onlyCountryOneHolidays.add(holidayOne)
            }
        }

        args.holidayData.countryTwoHolidays.forEach { holidayTwo ->
            if (!countryOneHolidayDates.contains(holidayTwo.observedDate)) {
                onlyCountryTwoHolidays.add(holidayTwo)
            }
        }
    }

    private fun setupButtons() {
        binding.onlyCountryOneHolidays.text =
            args.holidayData.countryOneName + "\nw/o\n" + args.holidayData.countryTwoName
        binding.commonHolidays.text =
            args.holidayData.countryOneName + "\n∩\n" + args.holidayData.countryTwoName
        binding.onlyCountryTwoHolidays.text =
            args.holidayData.countryTwoName + "\nw/o\n" + args.holidayData.countryOneName

        binding.onlyCountryOneHolidays.setOnClickListener {
            rvAdapter.setData(onlyCountryOneHolidays)
            binding.selectedLabel.text = args.holidayData.countryOneName + " w/o " + args.holidayData.countryTwoName
        }
        binding.commonHolidays.setOnClickListener {
            rvAdapter.setData(commonHolidays)
            binding.selectedLabel.text = args.holidayData.countryOneName + " ∩ " + args.holidayData.countryTwoName
        }
        binding.onlyCountryTwoHolidays.setOnClickListener {
            rvAdapter.setData(onlyCountryTwoHolidays)
            binding.selectedLabel.text = args.holidayData.countryTwoName + " w/o " + args.holidayData.countryOneName
        }
    }
}