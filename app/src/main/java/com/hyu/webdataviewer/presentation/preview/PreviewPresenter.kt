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

    override fun previewClick(clickViewIndex: Int, model: IPreviewModel) {
        val args = Bundle()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            args.putString(IDetailViewContract.ARGS_STR_TRANSITION_NAME, view.getTransitionName(clickViewIndex))
        }
        args.putSerializable(IDetailViewContract.ARGS_OBJECT_MODEL, model)
        detailFragment.setArgument(args)
        view.showTranstionDetailView(detailFragment, clickViewIndex)
    }
}