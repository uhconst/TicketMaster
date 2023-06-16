package com.uhc.data.remote

import com.uhc.data.remote.dto.EventDto
import com.uhc.data.remote.interceptor.RemoteRequestInterceptor
import com.uhc.data.remote.interceptor.RxRemoteErrorInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * REST API for Event Service
 */
interface EventService {

    /**
     * Attempts to get [EventDto.Response].
     */
    @GET("discovery/v2/events")
    suspend fun getEvents(
        @Query("size") size: Int
    ): EventDto.Response

    /**
     * Attempts to get [EventDto.Response].
     */
    @GET("discovery/v2/events/{id}")
    fun getEventById(@Path("id") id: String): Single<EventDto.EventResponse>

    companion object {
        fun createEventService(
            baseUrl: String,
            requestInterceptor: RemoteRequestInterceptor,
            rxRemoteErrorInterceptor: RxRemoteErrorInterceptor
        ): EventService {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(rxRemoteErrorInterceptor)
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
                .create(EventService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }
}