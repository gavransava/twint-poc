package com.example.twintpoc.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolidayDto(
    val name: String,
    val observed: String
) : Parcelable