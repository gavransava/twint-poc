package com.example.twintpoc.view.country

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twintpoc.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

}