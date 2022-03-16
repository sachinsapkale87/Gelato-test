package com.sachin.test.network

import com.sachin.test.home.HomePageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("v2/list?")
    suspend fun getImageList(@Query("page") pageNumber : Int, @Query("limit") limit : Int) : Response<List<HomePageData>>

    @GET("id/{imgId}/info")
    suspend fun getImageDetails(@Path("imgId") imageId : String) : Response<HomePageData>
}