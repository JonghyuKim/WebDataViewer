package com.hyu.webdataviewer.presentation.preview

import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract

interface IPreviewContract {
    interface Presenter{
        fun loadModel()
        fun previewClick(clickViewIndex: Int, model : IPreviewModel)
    }

    interface View {
        fun showLoading()
        fun hideLoading()

        fun getTransitionName(index: Int) : String?

        fun showTranstionDetailView(fragment: IBaseFragmentContract.View, viewPosition: Int)

        /**
         * Setting the modelList in a ListViewAdapter
         */
        fun setModelListInAdapter(modelList : List<IPreviewModel>)

        /**
         * Notify the View to change the modelList.
         */
        fun notifyDataSetChanged()

        /**
         * Notify the View to change the model.
         */
        fun notifyDataChanged(position: Int)

    }
}