package com.example.data.di

import com.example.data.repository.CartListRepositoryImpl
import com.example.domain.repository.CartListRepository
import org.koin.dsl.module

val dataModule = module {
    single<CartListRepository> { CartListRepositoryImpl() }
}
