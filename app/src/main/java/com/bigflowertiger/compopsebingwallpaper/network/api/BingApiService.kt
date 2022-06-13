package com.bigflowertiger.compopsebingwallpaper.network.api

import com.bigflowertiger.compopsebingwallpaper.data.model.BingResponse
import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BingApiService {
    @GET("HPImageArchive.aspx")
    fun fetchBingWalls(
        @QueryMap options: Map<String, String>? = null,
        @Query("idx") index: Int?,
        @Query("ensearch") ensearch: String?
    ): Flow<ApiResponse<BingResponse>>


    @GET("HPImageArchive.aspx")
    suspend fun fetchBingWalls2(
        @QueryMap options: Map<String, String>? = null,
        @Query("idx") index: Int?,
        @Query("ensearch") ensearch: String?
    ): BingResponse
}