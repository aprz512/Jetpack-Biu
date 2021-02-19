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
import com.aprz.jetpack_biu.paging3.model.ArticleModel
import com.aprz.jetpack_biu.paging3.model.ArticleModel.ArticleItem
import com.aprz.jetpack_biu.paging3.model.ArticleModel.SeparatorItem
import com.scwang.smart.refresh.footer.ClassicsFooter
import org.w3c.dom.Text
import java.text.DateFormat
import java.util.*

class ArticleAdapter :
    PagingDataAdapter<ArticleModel, RecyclerView.ViewHolder>(ARTICLE_COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when (item) {
                is ArticleItem -> (holder as ArticleViewHolder).bind(item.article)
                is SeparatorItem -> (holder as SeparatorViewHolder).bind(item.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.item_separator) {
            return SeparatorViewHolder.create(parent)
        }
        return ArticleViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArticleItem -> R.layout.item_article
            is SeparatorItem -> R.layout.item_separator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<ArticleModel>() {
            /**
             * 检查 item 是否是同一个，如果 item 内容会变化的话，一般以 id 判断
             */
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return (oldItem is ArticleItem && newItem is ArticleItem &&
                        oldItem.article.link == newItem.article.link) ||
                        (oldItem is SeparatorItem && newItem is SeparatorItem &&
                                oldItem.title == newItem.title)

            }

            /**
             * 检查 item 的内容是否完全一致
             */
            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
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

class SeparatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.tv_title)

    fun bind(title: String) {
        this.title.text = title
    }

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val root = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_separator, parent, false)
            return SeparatorViewHolder(root)
        }
    }
}