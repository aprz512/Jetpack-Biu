package com.aprz.jetpack_biu.paging3.ui

import android.util.Log
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

        // paging 加载数据的时候，可以使用这个来监听加载的状态
        adapter.addLoadStateListener { states ->
            // 刷新
            when (states.refresh) {
                is LoadState.Loading -> viewBinding.srlRefresh.autoRefresh()
                is LoadState.NotLoading -> viewBinding.srlRefresh.finishRefresh(true)
                is LoadState.Error -> viewBinding.srlRefresh.finishRefresh(false)
            }
            // 加载更多
            when (states.append) {
                is LoadState.Loading -> viewBinding.srlRefresh.autoLoadMore()
                is LoadState.NotLoading -> {
                    viewBinding.srlRefresh.finishLoadMore(true)
                    viewBinding.srlRefresh.setNoMoreData(states.append.endOfPaginationReached)
                }
                is LoadState.Error -> viewBinding.srlRefresh.finishLoadMore(false)
            }

            Log.e(TAG, states.source.toString())
        }

        viewBinding.srlRefresh.setEnableRefresh(true)
        viewBinding.srlRefresh.setEnableAutoLoadMore(false)
        viewBinding.srlRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        // 这个很重要，如果不加的话，没有网络的情况下，footer 不会消失
        // 是因为如果列表已经滚动到底部了，再主动的往上拉的话，是不会触发 paging 的状态变化的
        // 所以，需要调用一下 retry 方法，由于 retry 只会重试失败的请求，所以不会导致重复请求
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
                    // adapter 自己会做新旧列表合并
                    adapter.submitData(it)
                }
        }
    }


}