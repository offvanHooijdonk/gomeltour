package com.tobe.prediction.app

import android.annotation.SuppressLint
import android.content.Context
import com.tobe.prediction.R

/**
 * Created by Yahor_Fralou on 11/13/2018 1:12 PM.
 */

@SuppressLint("StaticFieldLeak")
object Const {
    internal lateinit var appCtx: Context
    val optionPos: Int by lazy { appCtx.resources.getInteger(R.integer.option_positive) }
    val optionNeg: Int by lazy { appCtx.resources.getInteger(R.integer.option_negative) }
}