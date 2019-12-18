package com.tobe.prediction.presentation.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.DrawableRes
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class EFABTransformer(private val fab: ExtendedFloatingActionButton, @DrawableRes initialIconRes: Int) {
    companion object {
        const val DURATION_ROTATE = 175L
    }

    private var currentIconRes = initialIconRes

    fun transformTo(@DrawableRes iconRes: Int, extended: Boolean? = null, text: String? = null) {
        if (fab.visibility != View.VISIBLE) {
            applyAndShow(iconRes, extended, text)
        } else {
            apply(iconRes, extended, text)
        }
        currentIconRes = iconRes
    }

    fun hide() {
        fab.hide()
    }

    private fun applyAndShow(@DrawableRes iconRes: Int, extended: Boolean? = null, text: String?) {
        fab.setIconResource(iconRes)
        fab.text = text
        if (extended == true || extended == null && text != null) fab.extend() else fab.shrink()
        fab.show()
    }

    private fun apply(@DrawableRes iconRes: Int, extended: Boolean? = null, text: String?) {
        when {
            iconRes == currentIconRes && extended?.let { it != fab.isExtended } ?: false -> {
            }
            iconRes != currentIconRes -> {
                when {
                    extended == null || extended == fab.isExtended -> {
                        if (fab.isExtended) {
                            text?.let { fab.text = it }
                            fab.setIconResource(iconRes)
                        } else {
                            collapseAndRotate(iconRes)
                        }
                    }
                    extended -> {
                        text?.let { fab.text = it }
                        if (fab.isExtended) fab.setIconResource(iconRes) else rotateAndExtend(iconRes)
                    }
                    else -> {
                        //if (fab.isExtended) collapseAndRotate(iconRes) else /* todo rotate and set icon */ {}
                        collapseAndRotate(iconRes)
                    }
                }
            }
            iconRes == currentIconRes && text != null -> {
                fab.text = text
                fab.show()
            }
        }
    }

    private fun collapseAndRotate(@DrawableRes iconRes: Int) {
        val rotateAnimation = ValueAnimator.ofFloat(0.0f, 90.0f).apply {
            addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                private var changeDone = false

                override fun onAnimationUpdate(animator: ValueAnimator) {
                    val rotationValue = (animator.animatedValue as Float).let {
                        if (it >= 45.0f) {
                            if (!changeDone) {
                                fab.setIconResource(iconRes)
                                changeDone = true
                            }
                            it - 90.0f
                        } else it
                    }

                    fab.rotation = rotationValue
                }
            })
            duration = DURATION_ROTATE
        }

        if (fab.isExtended) {
            fab.shrink(object : ExtendedFloatingActionButton.OnChangedCallback() {
                override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
                    super.onShrunken(extendedFab)
                    rotateAnimation.start()
                }
            })
        } else {
            rotateAnimation.start()
        }
    }

    private fun rotateAndExtend(@DrawableRes iconRes: Int) {
        ValueAnimator.ofFloat(0.0f, -90.0f).apply {
            addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                private var changeDone = false

                override fun onAnimationUpdate(animator: ValueAnimator) {
                    val rotationValue = (animator.animatedValue as Float).let {
                        if (it <= -45.0f) {
                            if (!changeDone) {
                                fab.setIconResource(iconRes)
                                changeDone = true
                            }
                            it + 90.0f
                        } else it
                    }

                    fab.rotation = rotationValue
                }
            })
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    fab.extend()
                }
            })
            duration = DURATION_ROTATE
        }.start()
    }
}