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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HolidayFragment : Fragment() {
    private lateinit var binding: FragmentHolidayBinding

    private val args: HolidayFragmentArgs by navArgs()
    private lateinit var rvAdapter: HolidayListAdapter

    private var onlyCountryOneHolidays: ArrayList<HolidayDto> = arrayListOf()
    private var commonHolidays: ArrayList<HolidayDto> = arrayListOf()
    private var onlyCountryTwoHolidays: ArrayList<HolidayDto> = arrayListOf()

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
        binding.selectedLabel.text =
            args.holidayData.countryOneName + " ∩ " + args.holidayData.countryTwoName
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
                commonHolidays.add(holidayOne.copy(observedDate = formatDate(holidayOne.observedDate)))
            } else {
                onlyCountryOneHolidays.add(holidayOne.copy(observedDate = formatDate(holidayOne.observedDate)))
            }
        }

        args.holidayData.countryTwoHolidays.forEach { holidayTwo ->
            if (!countryOneHolidayDates.contains(holidayTwo.observedDate)) {
                onlyCountryTwoHolidays.add(holidayTwo.copy(observedDate = formatDate((holidayTwo.observedDate))))
            }
        }

        onlyCountryOneHolidays = collapseContiguousHolidays(onlyCountryOneHolidays)
        commonHolidays = collapseContiguousHolidays(commonHolidays)
        onlyCountryTwoHolidays = collapseContiguousHolidays(onlyCountryTwoHolidays)
    }

    private fun formatDate(observedDate: String): String {
        val dateFormatDateAndTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date? = dateFormatDateAndTime.parse(observedDate)
        return android.text.format.DateFormat.format("dd-MM-yyyy", date).toString()
    }

    private fun collapseContiguousHolidays(holidays: ArrayList<HolidayDto>): ArrayList<HolidayDto> {
        var i = -1
        val result: ArrayList<HolidayDto> = arrayListOf()
        holidays.forEachIndexed { index, holidayDto ->
            if (index > i) {
                i = index
                val startDate = holidays[i].observedDate
                val holidayName = holidays[i].name
                var endDate: String
                while (i + 1 <= holidays.size) {
                    if (i + 1 != holidays.size && isContiguous(holidays[i], holidays[i + 1]))
                        i++
                    else {
                        endDate = holidays[i].observedDate
                        if (startDate != endDate) {
                            result.add(HolidayDto(holidayName, "$startDate to $endDate"))
                        } else {
                            result.add(HolidayDto(holidays[i].name, holidays[i].observedDate))
                        }
                        break
                    }
                }
            }
        }
        return result
    }

    private fun isContiguous(day: HolidayDto, nextDay: HolidayDto): Boolean {
        val dateFormatDateAndTime = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date1: Date? = dateFormatDateAndTime.parse(day.observedDate)
        val date2: Date? = dateFormatDateAndTime.parse(nextDay.observedDate)
        date1?.let { d1 ->
            date2?.let { d2 ->
                val cal1 = Calendar.getInstance()
                cal1.timeInMillis = d1.time
                cal1.add(Calendar.DAY_OF_MONTH, 1)
                val cal2 = Calendar.getInstance()
                cal2.timeInMillis = d2.time
                return@isContiguous if (cal1.equals(cal2))
                    return true
                else false
            }
        }
        return false
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
            binding.selectedLabel.text =
                args.holidayData.countryTwoName + " w/o " + args.holidayData.countryOneName
        }
    }
}