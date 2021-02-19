package com.aprz.jetpack_biu.paging3.module

import com.aprz.jetpack_biu.common.Http
import com.aprz.jetpack_biu.paging3.api.ArticleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PagingModule {

    @Provides
    fun provideArticleService(): ArticleService {
        return Http.retrofit.create(ArticleService::class.java)
    }

}