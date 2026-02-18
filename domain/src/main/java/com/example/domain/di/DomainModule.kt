package com.example.domain.di

import com.example.domain.usecase.GetCartListUseCase
import com.example.domain.usecase.RemoveCartUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCartListUseCase(get()) }
    factory { RemoveCartUseCase(get()) }
}
