package com.example.cart

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.util.DebugLogger
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.feature.di.featureModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

private fun createImageLoader(context: Context): ImageLoader {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .followSslRedirects(true)
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("User-Agent", "Cart-App/1.0 (Android)")
                    .build()
            )
        }
        .build()
    return ImageLoader.Builder(context)
        .components {
            add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
        }
        .logger(DebugLogger())
        .build()
}

class CartApplication : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader = createImageLoader(context)

    override fun onCreate() {
        super.onCreate()

        // Garante que o singleton do Coil use nosso ImageLoader com rede (OkHttp) desde o arranque
        SingletonImageLoader.setSafe { createImageLoader(it) }

        startKoin {
            androidLogger()
            androidContext(this@CartApplication)
            modules(
                domainModule,
                dataModule,
                featureModule
            )
        }
    }
}
