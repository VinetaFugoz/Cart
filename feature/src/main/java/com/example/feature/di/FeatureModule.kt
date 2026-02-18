package com.example.feature.di

import com.example.feature.presentation.CartListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { CartListViewModel(get(), get()) }
}
