package com.aprz.jetpack_biu.paging3.model

import android.text.TextUtils
import java.util.*


data class ArticlePageResponse(
    val data: ArticlePageData,
    val errorCode: Int = 0,
    val errorMsg: String = "",
) {

    fun isSuccess(): Boolean {
        return errorCode == 0 && TextUtils.isEmpty(errorMsg)
    }
}

data class ArticlePageData(
    val curPage: Int = 0,
    val datas: List<Article> = Collections.emptyList(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
)

data class Article(
    /**
     * 文章标题
     */
    val title: String = "",
    /**
     * 分享该文章的用户
     */
    val shareUser: String = "",
    /**
     * 该文章的分享日期
     */
    val shareDate: Long = 0,
    /**
     * 该文章的点赞次数
     */
    val zan: Int = 0,
    /**
     *该文章的链接
     */
    val link: String = "",
)