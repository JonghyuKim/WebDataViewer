package com.hyu.webdataviewer.presentation.preview

import android.os.Build
import android.os.Bundle
import android.view.View
import com.hyu.webdataviewer.R
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.domain.usecase.IUseCase
import com.hyu.webdataviewer.presentation.datail.IDetailViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class PreviewPresenter(private val view: IPreviewContract.View): IPreviewContract.Presenter , KoinComponent{

    private val getData by inject<IUseCase<List<IPreviewModel>>>()
    private val detailFragment by inject<IDetailViewContract.View>()

    override fun loadModel(){

        view.showLoading()

        getData.excute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->

                view.hideLoading()
                view.setModelListInAdapter(list)
                view.notifyDataSetChanged()
        }
    }

    override fun previewClick(clickView: View, model: IPreviewModel) {
        val args = Bundle()
        val transitionView = clickView.findViewById<View>(R.id.iv_preview_image)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            args.putString("transitionName", transitionView.transitionName)
        }
        args.putSerializable("model", model)
        detailFragment.setArgument(args)
        view.showDetailView(detailFragment, transitionView)
    }
}