package com.example.twintpoc.view.base

import androidx.lifecycle.ViewModel
import com.example.twintpoc.data.error.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager
}
