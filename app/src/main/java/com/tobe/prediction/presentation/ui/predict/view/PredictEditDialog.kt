package com.tobe.prediction.presentation.ui.predict.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.tobe.prediction.R


/**
 * Created by Yahor_Fralou on 9/27/2018 3:05 PM.
 */

class PredictEditDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogFragmentTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_predict_edit, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window.setWindowAnimations(R.style.DialogFadeAnimation)

        //window.setGravity(Gravity.TOP)
        //isCancelable = true
        //dialog.setCancelable(true)
        //dialog.setCanceledOnTouchOutside(true)
        //window.setWindowAnimations(R.style.DialogEmptyAnimation)
    }
}