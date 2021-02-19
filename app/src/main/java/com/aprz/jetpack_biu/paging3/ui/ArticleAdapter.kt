package com.aprz.jetpack_biu.paging3.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aprz.jetpack_biu.R
import com.aprz.jetpack_biu.common.getColorPrimary
import com.aprz.jetpack_biu.paging3.model.Article
import com.scwang.smart.refresh.footer.ClassicsFooter
import java.text.DateFormat
import java.util.*

class ArticleAdapter : PagingDataAdapter<Article, ArticleViewHolder>(ARTICLE_COMPARATOR) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        item?.apply {
            holder.bind(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            /**
             * 检查 item 是否是同一个，如果 item 内容会变化的话，一般以 id 判断
             */
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.link == newItem.link
            }

            /**
             * 检查 item 的内容是否完全一致
             */
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.tv_title)
    private val author: TextView = itemView.findViewById(R.id.tv_author)
    private val time: TextView = itemView.findViewById(R.id.tv_time)
    private val link: TextView = itemView.findViewById(R.id.tv_link)
    private val likeIcon: ImageView = itemView.findViewById(R.id.iv_like_icon)
    private val like: TextView = itemView.findViewById(R.id.tv_like)


    fun bind(article: Article) {
        title.text = article.title
        author.text = article.shareUser
        time.text = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA)
            .format(Date(article.shareDate))
        link.text = article.link
        likeIcon.setColorFilter(getColorPrimary(itemView.context))
        like.text = "${article.zan}"
    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val root = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
            return ArticleViewHolder(root)
        }
    }
}
