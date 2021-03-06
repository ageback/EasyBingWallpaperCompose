package com.bigflowertiger.compopsebingwallpaper.domain

import com.bigflowertiger.compopsebingwallpaper.data.model.BingResponse
import com.bigflowertiger.compopsebingwallpaper.network.api.BingApiService
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BingRepository(private val apiService: BingApiService) {
    suspend fun getBingWallpaperList3(index: Int, ensearch: String): BingResponse =
        apiService.fetchBingWalls2(getQueryOptions(), index, ensearch)

    fun getBingWallpaperList2(index: Int, ensearch: String): Flow<BingResponse> {
        return flow {
            val response = apiService.fetchBingWalls2(getQueryOptions(), index, ensearch)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getBingWallpaperList(index: Int, ensearch: String): Flow<Resource<BingResponse>> =
        networkResource(
            fetchFromRemote = { apiService.fetchBingWalls(getQueryOptions(), index, ensearch) }
        )

    private fun getQueryOptions(): Map<String, String> {
        return mapOf(
            "format" to "js", //	返回的数据格式。hp为html格式；js为json格式；其他值为xml格式。
//            "idx" to "0", //	获取特定时间点的数据。如idx=1表示前一天（昨天），依此类推。经过测试最大值为7。
            "n" to "8",//	获取数据的条数。经测试，配合上idx最大可以获取到13天前的数据，即idx=7&n=7。
//            "pid" to "", //未知 。 pid为hp时 ， copyrightlink返回的是相对地址 。 pid不为hp时 ， 没有看到og信息 。
//            "ensearch" to "1", //指定获取必应 【 国际版 / 国内版 】 的每日一图 。 当ensearch = 1 时 ， 获取到的是必应国际版的每日一图数据 。 默认情况和其他值情况下 ， 获取到的是必应国内版的每日一图数据 。
//            "quiz" to "",//当quiz = 1 时 ， 返回必应小测验所需的相关数据 。
//            "og" to "",// 水印图相关的信息 。 包含了title 、 img 、 desc和hash等信息 。
//            "uhd" to "1",// 当uhd = 1 时 ， 可以自定义图片的宽高 。 当uhd = 0 时 ， 返回的是固定宽高 （ 1920 x1080 ） 的图片数据 。
//            "uhdwidth" to "3840",//图片宽度 。 当uhd = 1 时生效 。 最大值为3840 ， 超过这个值当作3840处理 。
//            "uhdheight" to "2592",//图片高度 。 当uhd = 1 时生效 。 最大值为2592 ， 超过这个值当作2592处理 。
//            "setmkt" to "zh-CN", //指定图片相关的区域信息 。 如图片名中包含的EN -CN、 EN -US或者ZH - CN等。当域名为global.bing.com时才会有相应变化。值的格式：en-us、zh-cn等。
//            "setlang" to "zh-CN" // 指定返回数据所使用的语言 。 值的格式 ： en -us、zh-cn等。
        )
    }
}