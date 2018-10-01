package com.tobe.prediction.helper

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.tobe.prediction.R

/**
 * Created by Yahor_Fralou on 9/18/2018 1:55 PM.
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.hideBut() {
    this.visibility = View.INVISIBLE
}

fun SwipeRefreshLayout.setUp() {
    this.setColorSchemeResources(R.color.refresh_1, R.color.refresh_2, R.color.refresh_3)
}

fun Snackbar.colorError() {
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(this.context.resources.getColor(R.color.snackbar_error_text))
}

fun Snackbar.colorWarn() {
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(this.context.resources.getColor(R.color.snackbar_warn_text))
}