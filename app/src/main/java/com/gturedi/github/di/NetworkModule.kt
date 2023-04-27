package com.gturedi.github.di

import android.os.Build
import com.gturedi.github.BuildConfig
import com.gturedi.github.network.GithubApi
import com.gturedi.github.util.AppConst
import com.gturedi.github.util.logI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkhttp() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
    single { provideApi<GithubApi>(get()) }
}

fun provideOkhttp(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(AppConst.CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
        .readTimeout(AppConst.CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
        .writeTimeout(AppConst.CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
        .addInterceptor {
            val original = it.request()

            val req = original.newBuilder()
                .addHeader("device", Build.MANUFACTURER + " " + Build.MODEL)
                .addHeader("osLevel", Build.VERSION.SDK_INT.toString())
                .addHeader("osVersion", Build.VERSION.RELEASE)
                .addHeader("appVersionName", BuildConfig.VERSION_NAME)
                .addHeader("appVersionCode", BuildConfig.VERSION_CODE.toString())
                .build()

            val resp = it.proceed(req)

            resp
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        })
        .build()

fun provideGson(): Gson =
    GsonBuilder()
        .disableHtmlEscaping()
        //.serializeNulls()
        .create()

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .baseUrl(AppConst.API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

inline fun <reified T> provideApi(retrofit: Retrofit): T =
    retrofit.create(T::class.java)
        .also {
            logI("providePopApi $it")
        }