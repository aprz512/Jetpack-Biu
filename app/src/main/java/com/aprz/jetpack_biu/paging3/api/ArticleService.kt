package com.aprz.jetpack_biu.paging3.api

import com.aprz.jetpack_biu.paging3.model.ArticlePageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {

    /**
     * https://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun queryArticleList(@Path("page") page: Int): ArticlePageResponse

}