package com.example.twintpoc.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolidayDataDto(
    var countryOneName: String = "",
    var countryTwoName: String = "",
    var countryOneHolidays: ArrayList<HolidayDto> = arrayListOf(),
    var countryTwoHolidays: ArrayList<HolidayDto> = arrayListOf()
) : Parcelable