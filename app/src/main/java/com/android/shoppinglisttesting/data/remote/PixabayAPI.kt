package com.android.shoppinglisttesting.data.remote

import com.android.shoppinglisttesting.BuildConfig
import com.android.shoppinglisttesting.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("api/")
    suspend fun getAllImages(
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>

    @GET("api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}