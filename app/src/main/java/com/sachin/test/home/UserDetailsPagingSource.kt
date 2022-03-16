//package com.sachin.test.home
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.sachin.test.network.RetrofitService
//
//private const val Startpage = 1
//class UserDetailsPagingSource(
//    val retrofitService: RetrofitService
//    val pageNo:String
//) : PagingSource<Int, List<HomeData.DataObj>>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<HomeData.DataObj>> {
//        val position = params.key?: Startpage
//        try {
//            val apiresponse = retrofitService.getPageDetails(params.loadSize)
//            val response = apiresponse.body()?.data
//
//            LoadResult.Page(
//                data = response!!,
//                prevKey = if (position == Startpage) {
//                    null
//                } else {
//                    position - 1
//                },
//                nextKey = if (response.isEmpty()) {
//                    null
//                } else {
//                    position + 1
//                }
//            )
//        }catch (e:Exception){
//            e.localizedMessage
//        }
//
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, List<HomeData.DataObj>>): Int? {
//        TODO("Not yet implemented")
//    }
//}