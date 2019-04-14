package com.hyu.webdataviewer.presentation.preview.list

import android.view.ViewGroup
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.BaseListAdapter
import com.hyu.webdataviewer.presentation.base.BaseViewHolder
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PreviewAdaptor: BaseListAdapter<IPreviewModel>(), KoinComponent{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<IPreviewModel> {
        return get(named(VIEW_TYPE_LIST[viewType])){parametersOf(parent)}
    }

    override fun getItemViewType(position: Int): Int {
        return (position % 2)
    }

    companion object {
        const val VIEW_TYPE_BASIC = "basic"
        const val VIEW_TYPE_BIG_SIZE = "bigSize"
        private val VIEW_TYPE_LIST = arrayListOf(
            VIEW_TYPE_BASIC,
            VIEW_TYPE_BIG_SIZE
        )
    }
}

