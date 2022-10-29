package com.mahrous.advashowimagetask.network

import androidx.databinding.ktx.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    }


    // this function will return us instance of any api we will base to
    fun <APi> buildApi(api: Class<APi>): APi {
        return Retrofit.Builder()
            .client(getClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getClient(): OkHttpClient {
        val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)

            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}