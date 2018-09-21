package com.tobe.prediction.helper

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
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

fun SwipeRefreshLayout.setUp() {
    this.setColorSchemeResources(R.color.refresh_1, R.color.refresh_2, R.color.refresh_3)
}