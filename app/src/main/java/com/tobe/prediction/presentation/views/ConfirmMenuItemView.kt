package com.tobe.prediction.presentation.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.tobe.prediction.R
import com.tobe.prediction.helper.gone
import com.tobe.prediction.helper.invisible
import com.tobe.prediction.helper.visible
import kotlinx.android.synthetic.main.view_menu_confirm.view.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Yahor_Fralou on 10/23/2018 11:20 AM.
 */

class ConfirmMenuItemView(ctx: Context, attrs: AttributeSet) : FrameLayout(ctx, attrs) {
    companion object {
        const val DEFAULT_TEXT = ""
    }

    private var icon: Drawable? = null
    private lateinit var itemText: String
    private lateinit var positiveButton: String
    private lateinit var negativeButton: String
    private lateinit var onConfirmListener: () -> Unit

    private var eventX = 0f
    private var eventY = 0f

    init {
        val ta: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ConfirmMenuItemView, 0, 0)
        try {
            icon = ta.getDrawable(R.styleable.ConfirmMenuItemView_icon)
            itemText = ta.getString(R.styleable.ConfirmMenuItemView_text) ?: DEFAULT_TEXT
            positiveButton = ta.getString(R.styleable.ConfirmMenuItemView_positiveButton) ?: ctx.getString(R.string.btn_confirm_sign_out)
            negativeButton = ta.getString(R.styleable.ConfirmMenuItemView_negativeButton) ?: ctx.getString(android.R.string.cancel)
        } catch (e: Exception) {
        } finally {
            ta.recycle()
        }

        View.inflate(ctx, R.layout.view_menu_confirm, this)

        setUpView()
        setUpAnimations()
    }

    fun onConfirm(listener: () -> Unit) {
        onConfirmListener = listener
    }

    private fun setUpView() {
        blockButtons.gone()

        if (icon == null) imgItemIcon.gone() else imgItemIcon.setImageDrawable(icon)
        txtItemText.text = itemText
        btnPositive.text = positiveButton
        btnNegative.text = negativeButton
    }

    private fun setUpAnimations() {
        startListenItemTouch()

        itemRoot.setOnClickListener {
            revealButtons(!blockButtons.isShown)
        }
        btnNegative.setOnClickListener {
            hideButtons()
        }
        btnPositive.setOnClickListener {
            hideButtons()
            this.onConfirmListener()
        }
    }

    private fun revealButtons(isShowButtons: Boolean) {
        if (isShowButtons) {
            txtItemText.invisible()
            if (icon != null) imgItemIcon.invisible()
            applyReveal(blockButtons, itemRoot.height.toFloat(), itemRoot.width.toFloat(), true)
        } else {
            applyReveal(blockButtons, itemRoot.height.toFloat(), itemRoot.width.toFloat(), false)
            txtItemText.visible()
            if (icon != null) imgItemIcon.visible()
        }
    }

    private fun hideButtons() {
        hideBlock(blockButtons)
        txtItemText.visible()
    }

    private fun startListenItemTouch() {
        itemRoot.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                eventX = event.x
                eventY = event.y
            }
            return@setOnTouchListener false
        }
    }

    private fun applyReveal(v: View, height: Float, width: Float, isShow: Boolean) { // todo to helper
        if (isShow) v.visible()

        val radius = sqrt(max(max(height - eventY, eventY), max(width - eventX, eventX)).pow(2) +
                min(max(height - eventY, eventY), max(width - eventX, eventX)).pow(2))
        val startRadius = if (isShow) 0f else radius
        val endRadius = if (isShow) radius else 0f

        ViewAnimationUtils.createCircularReveal(v, eventX.toInt(), eventY.toInt(), startRadius, endRadius)
                .apply {
                    duration = if (isShow) 350 else 150
                    interpolator = DecelerateInterpolator()
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            if (!isShow) v.gone()
                        }
                    })
                }
                .start()
    }

    private fun hideBlock(v: View) { // todo to helper
        ObjectAnimator.ofFloat(v, View.SCALE_Y, 1f, 0f)
                .apply {
                    duration = 150 // todo set less time
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            v.gone()
                            v.scaleY = 1f
                        }
                    })
                }
                .start()
    }

}