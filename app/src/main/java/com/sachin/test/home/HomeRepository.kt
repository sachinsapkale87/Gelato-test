package com.sachin.test.home

import com.sachin.test.network.RetrofitService

class HomeRepository (val retrofitService: RetrofitService) {

    suspend fun getImageList(pageNo:Int,limit:Int) =  retrofitService.getImageList(pageNo,limit)
    suspend fun getImageDetails(imageId:String) =  retrofitService.getImageDetails(imageId)
}