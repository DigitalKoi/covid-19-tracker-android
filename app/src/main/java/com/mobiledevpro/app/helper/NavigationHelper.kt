package com.mobiledevpro.app.helper

import android.view.View
import androidx.navigation.findNavController
import com.mobiledevpro.app.R

/**
 * Navigation Helper
 *
 * Created by Dmitriy Chernysh
 *
 * http://androiddev.pro
 *
 * #MobileDevPro
 */

fun View.showEditUserFragment() {
    this.findNavController()
            .navigate(R.id.actionUserEdit)
}


fun View.showViewUserFragment() {
    this.findNavController()
            .navigate(R.id.actionUserView)
}