package com.hyu.webdataviewer.presentation.preview

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.hyu.webdataviewer.R
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.BaseFragment
import com.hyu.webdataviewer.presentation.base.BaseListAdapter
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.presentation.base.IBaseItemContract
import com.hyu.webdataviewer.presentation.datail.IDetailViewContract
import com.hyu.webdataviewer.util.log.HLog
import kotlinx.android.synthetic.main.fragment_preview.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PreviewFragment : BaseFragment(), IPreviewContract.View {

    private val listAdapter by inject<BaseListAdapter<IPreviewModel>>()

    private val presenter by inject<IPreviewContract.Presenter>{parametersOf(this)}

    private lateinit var progress : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        HLog.d("onCreateView")
        val mainLayout = inflater.inflate(com.hyu.webdataviewer.R.layout.fragment_preview, container, false)

        mainLayout.rv_preview_list.apply {

            layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
                2,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )

            listAdapter.itemClickListener = object :IBaseItemContract.ItemClickListener<IPreviewModel>{
                override fun onClick(index: Int, model: IPreviewModel) {
                    presenter.previewClick(index, model)
                }
            }

            adapter = listAdapter

            val preDrawListener = object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return true
                }
            }
            viewTreeObserver.addOnPreDrawListener(preDrawListener)

        }
        progress = mainLayout.pb_progress

        presenter.loadModel()

        return mainLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
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

    override fun showTranstionDetailView(fragment: IBaseFragmentContract.View, viewPosition: Int) {
        getListChildView(viewPosition)?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceFragment(fragment, it, it.transitionName)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getTransitionName(index: Int) : String?{
        return getListChildView(index)?.transitionName
    }

    private fun getListChildView(index : Int) : View?{
        return view?.rv_preview_list?.getChildAt(index)?.findViewById<View>(R.id.iv_preview_image)
    }
}