package com.aprz.jetpack_biu.paging3.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aprz.jetpack_biu.paging3.api.ArticleService
import com.aprz.jetpack_biu.paging3.model.Article

class ArticlePagingSource(private val service: ArticleService) :
    PagingSource<Int, Article>() {

    companion object {
        private const val TAG = "ArticlePagingSource"
    }

    /**
     * load 方法里面去发送网络请求
     * 这里的例子没有设计到数据库缓存
     * 该方法里面，主要是设置 LoadResult 的参数，LoadResult 就是将返回的数据封装了一下
     * prevKey 与 nextKey 就是表示前一页与后一页，按逻辑设置好就行了
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        try {
            // 该方法第一次被调用的时候，params 的 key 值为 null
            val page = params.key ?: 0

            val response = service.queryArticleList(page)
            if (response.isSuccess()) {
                val datas = response.data.datas

                // 按日期降序排列
                val sortedList = mutableListOf<Article>()
                sortedList.addAll(datas)
                sortedList.sortByDescending { article -> article.shareDate }


                Log.e(TAG, "currentPage = ${response.data.curPage}")

                return LoadResult.Page(
                    data = sortedList,
                    prevKey = if (response.data.curPage == 1) {
                        null
                    } else {
                        response.data.curPage - 2
                    },
                    nextKey = if (response.data.curPage < response.data.pageCount) {
                        response.data.curPage
                    } else {
                        null
                    }
                )
            } else {
                return LoadResult.Error(Exception(response.errorMsg))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

    /**
     * 这个方法还不是很懂，但是如果是 PagedKey 的话，直接返回 null 就好了
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}