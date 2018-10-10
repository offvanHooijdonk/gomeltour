package com.tobe.prediction.presentation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.hideBut
import com.tobe.prediction.helper.show
import kotlinx.android.synthetic.main.fr_bottom_options_dialog.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Yahor_Fralou on 10/10/2018 2:40 PM.
 */

class BottomOptionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val EVENT_SIGN_OUT = 0
    }

    private var pickEventListener: (Int) -> Unit = {}
    private var eventX = 0f
    private var eventY = 0f

    fun setMenuPickListener(listener: (option: Int) -> Unit) {
        this.pickEventListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_bottom_options_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignOutOption()
    }

    private fun setupSignOutOption() {
        itemSignOut.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                eventX = event.x
                eventY = event.y
            }
            return@setOnTouchListener false
        }
        itemSignOut.setOnClickListener {
            if (blockSignOutConfirm.isShown) {
                //hideBlock(blockSignOutConfirm)
                applyReveal(blockSignOutConfirm, itemSignOut.height.toFloat(), itemSignOut.width.toFloat(), false)
                txtSignOut.show()
            } else {
                txtSignOut.hideBut()
                applyReveal(blockSignOutConfirm, itemSignOut.height.toFloat(), itemSignOut.width.toFloat(), true)
            }
        }

        btnSignOutCancel.setOnClickListener {
            hideBlock(blockSignOutConfirm)
            //applyReveal(blockSignOutConfirm, itemSignOut.height.toFloat(), itemSignOut.width.toFloat(), false)
            txtSignOut.show()
        }

        btnSignOutConfirm.setOnClickListener { pickEventListener(EVENT_SIGN_OUT) }
    }

    private fun hideBlock(v: View) {
        ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f)
                .apply {
                    duration = 150
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            v.hide()
                            v.alpha = 1f
                        }
                    })
                }.start()
    }

    private fun applyReveal(v: View, height: Float, width: Float, isShow: Boolean) {
        if (isShow) v.show()

        val radius = sqrt(max(max(height - eventY, eventY), max(width - eventX, eventX)).pow(2) +
                min(max(height - eventY, eventY), max(width - eventX, eventX)).pow(2))
        val startRadius = if (isShow) 0f else radius
        val endRadius = if (isShow) radius else 0f

        ViewAnimationUtils.createCircularReveal(v, eventX.toInt(), eventY.toInt(), startRadius, endRadius)
                .apply {
                    duration = if (isShow) 500 else 150
                    interpolator = DecelerateInterpolator()
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            if (!isShow) v.hide()
                        }
                    })
                }
                .start()
    }
}