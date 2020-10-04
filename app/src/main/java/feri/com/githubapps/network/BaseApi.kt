package feri.com.githubapps.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApi {
    private fun interceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun client(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.github.com/")
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun api(): ApiRoute {
        return client().create(ApiRoute::class.java)
    }
}