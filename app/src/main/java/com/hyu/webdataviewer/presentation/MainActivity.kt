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


class MainActivity : AppCompatActivity(), IMainActivityContract.View{

    private val presenter by inject<IMainActivityContract.Presenter>{parametersOf(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.initLayout()
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main_layer,fragment.toFragment())
            .commit()
    }

    override fun addFragment(fragment: IBaseFragmentContract.View) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_main_layer,fragment.toFragment())
            .commitAllowingStateLoss()
    }

    override fun addFragment(fragment: IBaseFragmentContract.View, transitionView: View) {
        baseTransition(fragment.toFragment(), transitionView)
    }

    private fun baseTransition(nextFragment : androidx.fragment.app.Fragment, transitionView: View) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(transitionView, transitionView.transitionName)
        }

        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
        else {
            super.onBackPressed()
        }
    }
}