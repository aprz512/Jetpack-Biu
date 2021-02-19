package com.aprz.jetpack_biu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aprz.jetpack_biu.common.BaseActivity
import com.aprz.jetpack_biu.databinding.ActivityMainBinding
import com.aprz.jetpack_biu.paging3.ui.ArticleActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter: ComponentGridAdapter by lazy {
        ComponentGridAdapter()
    }

    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewBinding.rvComponentGrid.adapter = adapter

        val list: List<ComponentGrid> = listOf(
            ComponentGrid(title = "paging *", desc = "在页面中加载数据，并在 RecyclerView 中呈现。") {
                startActivity(Intent(this, ArticleActivity::class.java))
            },
        )

        adapter.submitList(list)
    }

    override fun bindEvent() {
    }

    override fun sendHttpRequest() {
    }
}

data class ComponentGrid(
    val title: String = "",
    val desc: String = "",
    val navigatorRunnable: Runnable?
)

class ComponentGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.tv_title)
    private val desc: TextView = itemView.findViewById(R.id.tv_desc)

    fun bind(componentGrid: ComponentGrid) {
        itemView.setOnClickListener {
            componentGrid.navigatorRunnable?.run()
        }
        title.text = componentGrid.title
        desc.text = componentGrid.desc
    }

    companion object {
        fun create(parent: ViewGroup): ComponentGridViewHolder {
            val root = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_component_grid, parent, false)
            return ComponentGridViewHolder(root)
        }
    }

}

class ComponentGridAdapter : ListAdapter<ComponentGrid, ComponentGridViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ComponentGrid>() {
            override fun areItemsTheSame(
                oldItem: ComponentGrid,
                newItem: ComponentGrid
            ): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: ComponentGrid,
                newItem: ComponentGrid
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentGridViewHolder {
        return ComponentGridViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ComponentGridViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this)
        }
    }

}