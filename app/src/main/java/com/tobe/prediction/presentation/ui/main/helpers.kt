package com.tobe.prediction.presentation.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.annotation.DrawableRes
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class EFABTransformer(@DrawableRes private val initialIconRes: Int, private val fab: ExtendedFloatingActionButton) {
    companion object {
        const val DURATION_ROTATE = 175L
    }

    fun transformToFAB(@DrawableRes iconRes: Int) {
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
            fab.shrink(object : ExtendedFloatingActionButton.OnChangedListener() {
                override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
                    super.onShrunken(extendedFab)
                    rotateAnimation.start()
                }
            })
        } else {
            rotateAnimation.start()
        }
    }

    fun transformBack() {
        ValueAnimator.ofFloat(0.0f, -90.0f).apply {
            addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                private var changeDone = false

                override fun onAnimationUpdate(animator: ValueAnimator) {
                    val rotationValue = (animator.animatedValue as Float).let {
                        if (it <= -45.0f) {
                            if (!changeDone) {
                                fab.setIconResource(initialIconRes)
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