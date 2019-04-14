package com.hyu.webdataviewer.presentation.preview.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hyu.webdataviewer.R
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.BaseViewHolder
import com.hyu.webdataviewer.presentation.base.IBaseItemContract
import com.hyu.webdataviewer.util.log.HLog
import kotlinx.android.synthetic.main.layer_item_preview.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PreviewItemHolder(parent: ViewGroup)
    : BaseViewHolder<IPreviewModel>(
    LayoutInflater.from(parent.context).inflate(R.layout.layer_item_preview, parent, false)
) , IPreviewItemContract.View , KoinComponent{

    private val presenter by inject<IPreviewItemContract.Presenter>{ parametersOf(this) }

    override fun bindModel(model : IPreviewModel, itemClickListener: IBaseItemContract.ItemClickListener<IPreviewModel>?){
        presenter.setItemModel(model,itemClickListener)
    }

    override fun showItemView(
        model: IPreviewModel,
        itemClickListener: IBaseItemContract.ItemClickListener<IPreviewModel>?
    ) {
        HLog.d("call showItemView")
        with(itemView){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv_preview_image.transitionName = context.getString(R.string.transition_id_preview) + layoutPosition
            }
            presenter.bindImage(context, iv_preview_image, model.getPreviewUrl())
            tv_preview_title.text = model.getPreviewTitle()
            setOnClickListener{
                itemClickListener?.onClick(it, layoutPosition, model)
            }
        }
    }

    override fun unbind() {
        presenter.releaseModels()
    }
}