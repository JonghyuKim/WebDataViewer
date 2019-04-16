package com.hyu.webdataviewer.presentation.datail

import android.os.Build
import com.hyu.webdataviewer.R
import com.hyu.webdataviewer.domain.model.AmiiboModel
import kotlinx.android.synthetic.main.fragment_amiibo_view.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AmiiboViewFragment : DetailFragment(){

    override val presenter: IDetailViewContract.Presenter by inject{ parametersOf(this) }
    override val layoutId: Int = R.layout.fragment_amiibo_view

    override fun showDetailView(targetTranslateName : String, model: Any) {
        if(model is AmiiboModel) {
            mainLayout.apply {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iv_detail_main_image.transitionName = targetTranslateName
                }

                presenter.bindImage (context, iv_detail_main_image, model.image)
                tv_detail_title.text = model.name
                tv_amiibo_series.text = model.amiiboSeries
                tv_amiibo_character.text = model.character
                tv_amiibo_game_series.text = model.gameSeries
            }
        }
    }
}