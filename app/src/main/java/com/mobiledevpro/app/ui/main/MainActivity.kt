package com.mobiledevpro.app.ui.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Observer
import com.mobiledevpro.app.R
import com.mobiledevpro.app.common.BaseFragmentInterface
import com.mobiledevpro.app.ui.main.viemodel.MainViewModel
import com.mobiledevpro.app.utils.*
import com.mobiledevpro.commons.activity.BaseActivity
import com.mobiledevpro.commons.helpers.BaseResourcesHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_main

    override fun isAdjustFontScaleToNormal() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //set navigation bar translucent
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        super.onCreate(savedInstanceState)

        applyWindowInsets(findViewById(android.R.id.content))
    }

    override fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
        toolbar?.let { setSupportActionBar(it) }
    }

    override fun initPresenters() {
        //no need
    }

    override fun populateView(layoutView: View) {
        //work with view here

        observeEvents()
    }

    override fun onBackPressed() {

        with(supportFragmentManager.findFragmentById(R.id.fragment_nav_host)?.childFragmentManager?.fragments?.get(0)) {
            if (this is BaseFragmentInterface) {

                val position: IntArray = fab_main_action?.findLocationOfCenterOnTheScreen()
                    ?: IntArray(2)

                this.view?.exitCircularReveal(position) {
                    super.onBackPressed()
                } ?: super.onBackPressed()

            } else {
                super.onBackPressed()
            }

        }


        // super.onBackPressed()
    }

    private fun observeEvents() {
        mainViewModel.eventNavigateTo.observe(this, Observer {
            it.getContentIfNotHandled()?.let { navigateTo ->
                when (navigateTo) {
                    Navigation.NAVIGATE_TO_COUNTRIES_LIST -> showCountiesList(R.id.fragment_nav_host)
                }
            }
        })

        mainViewModel.eventFabAction.observe(this, Observer {
            it.getContentIfNotHandled()?.let { action ->
                when (action) {
                    FabActionNavigation.ACTION_SHOW_COUNTRIES ->
                        fab_main_action?.apply {
                            this.setOnClickListener { mainViewModel.showCountriesList() }
                            this.setImageDrawable(
                                BaseResourcesHelper.getDrawableCompatible(this@MainActivity, R.drawable.ic_world_24dp))
                            this.show()
                        }

                    FabActionNavigation.ACTION_SHOW_COUNTRY_SEARCH_BAR ->
                        fab_main_action?.apply {
                            this.setOnClickListener { mainViewModel.showSearchCountryBar() }
                            this.setImageDrawable(
                                BaseResourcesHelper.getDrawableCompatible(this@MainActivity, R.drawable.ic_search_24))
                            this.show()
                        }
                }
            }
        })

    }

    private fun applyWindowInsets(view: View) {
        //Use Window Insets to set top and bottom paddings to our activity
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            v.updatePadding(
                left = insets.systemWindowInsetLeft,
                top = insets.systemWindowInsetTop,
                right = insets.systemWindowInsetRight,
                bottom = insets.systemWindowInsetBottom
            )
            insets
        }
    }
}
