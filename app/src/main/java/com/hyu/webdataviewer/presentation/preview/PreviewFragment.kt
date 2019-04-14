package com.hyu.webdataviewer.presentation.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.BaseFragment
import com.hyu.webdataviewer.presentation.base.BaseListAdapter
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.presentation.base.IBaseItemContract
import kotlinx.android.synthetic.main.fragment_preview.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PreviewFragment : BaseFragment(), IPreviewContract.View {

    private val listAdapter by inject<BaseListAdapter<IPreviewModel>>()

    private val presenter by inject<IPreviewContract.Presenter>{parametersOf(this)}

    private lateinit var progress : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val mainLayout = inflater.inflate(com.hyu.webdataviewer.R.layout.fragment_preview, container, false)

        mainLayout.rv_preview_list.apply {

            layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
                2,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )

            listAdapter.itemClickListener = object :IBaseItemContract.ItemClickListener<IPreviewModel>{
                override fun onClick(clickView: View, index: Int, model: IPreviewModel) {
                    presenter.previewClick(clickView, model)
                }
            }

            adapter = listAdapter

        }
        progress = mainLayout.pb_progress

        presenter.loadModel()

        return mainLayout
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.INVISIBLE
    }

    override fun setModelListInAdapter(modelList: List<IPreviewModel>) {
        listAdapter.dataList = modelList
    }

    override fun notifyDataSetChanged() {
        listAdapter.notifyDataSetChanged()
    }

    override fun notifyDataChanged(position : Int) {
        listAdapter.notifyItemChanged(position)
    }

    override fun showDetailView(fragment: IBaseFragmentContract.View, transitionView: View) {
        addFragment(fragment, transitionView)
    }
}