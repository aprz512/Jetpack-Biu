package com.aprz.jetpack_biu.paging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aprz.jetpack_biu.paging3.api.ArticleService
import com.aprz.jetpack_biu.paging3.model.Article
import com.aprz.jetpack_biu.paging3.source.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 如果没有分页，按照一般的 MVVM 写法，这里就是直接请求网络，然后使用 LiveData 来封装返回的数据
 * 使用了 Paging3，就相当于是多了一层 PagingSource，Repository 还要从 PagingSource 里面取数据，
 * 它并不真正的执行网络请求，真正的网络请求在 PagingSource 里面，这里就是相当于做了一个代理工作
 */
class ArticleRepository @Inject constructor(private val service: ArticleService) {

    fun getArticleStream(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                // 一页 20 条，已经够多了，没必要预加载多页
                prefetchDistance = 1,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ArticlePagingSource(service)
            }
        ).flow
    }

}