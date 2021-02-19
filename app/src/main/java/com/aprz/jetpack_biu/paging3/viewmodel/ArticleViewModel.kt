package com.aprz.jetpack_biu.paging3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.aprz.jetpack_biu.paging3.model.ArticleModel
import com.aprz.jetpack_biu.paging3.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    fun queryArticle(): Flow<PagingData<ArticleModel>> {
        // 缓存了当前的一页数据，便于立即恢复
        return repository.getArticleStream()
            .map { data -> data.map { ArticleModel.ArticleItem(it) } }
            .map {
                it.insertSeparators { before, after ->

                    if (after == null) {
                        return@insertSeparators null
                    }

                    // check between 2 items
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = after.article.shareDate
                    val afterMonth = calendar.get(Calendar.MONTH) + 1

                    if (before == null) {
                        return@insertSeparators ArticleModel.SeparatorItem("${afterMonth}月份文章")
                    }

                    calendar.timeInMillis = before.article.shareDate
                    val beforeMonth = calendar.get(Calendar.MONTH) + 1

                    if (beforeMonth != afterMonth) {
                        ArticleModel.SeparatorItem("${afterMonth}月份文章")
                    } else {
                        // no separator
                        null
                    }

                }
            }
            .cachedIn(viewModelScope)
    }

}