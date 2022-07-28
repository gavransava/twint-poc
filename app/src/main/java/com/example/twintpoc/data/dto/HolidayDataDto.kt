package com.example.twintpoc.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolidayDataDto(
    val countryOneName: String,
    val countryTwoName: String,
    val countryOneHolidays: ArrayList<HolidayDto>,
    val countryTwoHolidays: ArrayList<HolidayDto>
) : Parcelable