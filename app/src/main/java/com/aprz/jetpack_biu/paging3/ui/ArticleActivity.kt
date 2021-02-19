package com.aprz.jetpack_biu.paging3.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aprz.jetpack_biu.common.BaseActivity
import com.aprz.jetpack_biu.common.setFinishActivityListener
import com.aprz.jetpack_biu.databinding.ActivityArticleBinding
import com.aprz.jetpack_biu.paging3.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    companion object {
        private const val TAG = "ArticleActivity"
    }

    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    private val viewModel: ArticleViewModel by lazy {
        ViewModelProvider(this).get(ArticleViewModel::class.java)
    }

    override fun inflateViewBinding(): ActivityArticleBinding {
        return ActivityArticleBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewBinding.tbToolBar.setFinishActivityListener(this)
        viewBinding.rvArticleList.adapter = adapter
        adapter.addLoadStateListener { states ->
            when (states.refresh) {
                is LoadState.Loading -> viewBinding.srlRefresh.autoRefresh()
                is LoadState.NotLoading -> viewBinding.srlRefresh.finishRefresh(true)
                is LoadState.Error -> viewBinding.srlRefresh.finishRefresh(false)
            }
            when (states.append) {
                is LoadState.Loading -> viewBinding.srlRefresh.autoLoadMore()
                is LoadState.NotLoading -> {
                    viewBinding.srlRefresh.finishLoadMore(true)
                    viewBinding.srlRefresh.setNoMoreData(states.append.endOfPaginationReached)
                }
                is LoadState.Error -> viewBinding.srlRefresh.finishLoadMore(false)
            }
        }
        viewBinding.srlRefresh.setEnableRefresh(true)
        viewBinding.srlRefresh.setEnableAutoLoadMore(false)
        viewBinding.srlRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        viewBinding.srlRefresh.setOnLoadMoreListener {
            adapter.retry()
        }
    }

    override fun bindEvent() {
    }

    override fun sendHttpRequest() {
        lifecycleScope.launch {
            viewModel.queryArticle()
                .collect {
                    adapter.submitData(it)
                }
        }
    }


}