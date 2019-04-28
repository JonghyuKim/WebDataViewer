package com.hyu.webdataviewer.presentation.datail

import android.os.Build
import android.os.Bundle
import com.hyu.webdataviewer.domain.model.AmiiboModel
import com.hyu.webdataviewer.util.log.HLog

class AmiiboViewPresenter(private val view: IDetailViewContract.View) : IDetailViewContract.Presenter{

    override fun loadData(args: Bundle) {

        var transitionName  = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionName = args.getString(IDetailViewContract.ARGS_STR_TRANSITION_NAME)
        }

        HLog.d("loadModel : $transitionName")

        var amiiboModel = args.getSerializable(IDetailViewContract.ARGS_OBJECT_MODEL) as AmiiboModel

        view.showDetailView(transitionName, amiiboModel)
    }

}