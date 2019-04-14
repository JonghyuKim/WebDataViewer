package com.hyu.webdataviewer.presentation.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    var dataList: List<T>? = null
    var itemClickListener : IBaseItemContract.ItemClickListener<T>? = null

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        dataList?.let {
            holder.bindModel(it[position], itemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.let { it.size } ?: 0
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>) {
        holder.unbind()
        super.onViewDetachedFromWindow(holder)
    }
}
