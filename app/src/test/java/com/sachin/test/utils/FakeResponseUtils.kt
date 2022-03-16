package com.sachin.test.utils

import com.sachin.test.home.HomePageData
import retrofit2.Response

object FakeResponseUtils {

    fun getImageList() : Response<List<HomePageData>> {
        val image = HomePageData("32","Sachin","dummmyUrl","dummmyUrl")
        val userList : List<HomePageData> = listOf(image) //limit is 5
        val response = Response.success(userList)
        return response
    }
}