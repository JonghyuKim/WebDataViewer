package com.hyu.webdataviewer.presentation.base

import androidx.recyclerview.widget.RecyclerView
import android.view.View


abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView)
                                                , IBaseItemContract.View<T>