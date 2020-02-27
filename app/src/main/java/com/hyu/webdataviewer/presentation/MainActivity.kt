package com.hyu.webdataviewer.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import com.hyu.webdataviewer.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import android.view.View
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.util.log.HLog


class MainActivity : AppCompatActivity(), IMainActivityContract.View{

    private val presenter by inject<IMainActivityContract.Presenter>{parametersOf(this)}

    override fun onDestroy() {
        super.onDestroy()
        HLog.v("onDestroy : ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: let{
            presenter.initLayout()
        }
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main_layer,fragment.toFragment())
            .commit()
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View, transitionView: View, transitionName : String?) {
        baseTransition(fragment.toFragment(), transitionView, transitionName)
    }

    private fun baseTransition(nextFragment : androidx.fragment.app.Fragment, transitionView: View, transitionName : String?) {
        if (isDestroyed) {
            return
        }
        val previousFragment = supportFragmentManager.findFragmentById(R.id.fl_main_layer)!!

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        previousFragment.sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(R.transition.transition_detail_fragment)

        previousFragment.exitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.fade)

        nextFragment.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(R.transition.transition_detail_fragment)

        nextFragment.enterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.fade)

        fragmentTransaction.replace(R.id.fl_main_layer, nextFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)

        HLog.d("transitionName : $transitionName")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(transitionView, transitionName!!)
        }

        fragmentTransaction.commit()
    }
}