package com.aprz.jetpack_biu.paging3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aprz.jetpack_biu.paging3.model.Article
import com.aprz.jetpack_biu.paging3.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    private var articleList: Flow<PagingData<Article>>? = null

    fun queryArticle(): Flow<PagingData<Article>> {
        // 缓存了当前的一页数据，便于立即恢复
        val newList = repository.getArticleStream().cachedIn(viewModelScope)
        articleList = newList
        return newList
    }

}