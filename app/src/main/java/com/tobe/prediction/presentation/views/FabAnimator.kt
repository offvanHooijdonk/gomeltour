package com.tobe.prediction.presentation.views

import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.tobe.prediction.helper.gone
import com.tobe.prediction.helper.visible
import org.jetbrains.anko.dip

/**
 * Created by Yahor_Fralou on 11/29/2018 5:33 PM.
 */

class FabAnimator(private val button: Button) {
    private lateinit var aScaleFade: ValueAnimator
    private lateinit var aShrink: ValueAnimator
    private lateinit var aSlideOut: ValueAnimator
    private lateinit var aSinkDown: ValueAnimator

    fun startOutAnimation() {
        aScaleFade = prepareScaleFade() // TODO implement reusing Animator objects
        aShrink = prepareShrink()
        aSlideOut = prepareSlideOutAnimation()
        //aSinkDown = prepareSinkDown()

        aShrink.also {
            it.after({ it.animatedFraction as Float > 0.97f }, aScaleFade) {}
        }.start()
    }

    fun startInAnimation() {
        button.layoutParams = (button.layoutParams as CoordinatorLayout.LayoutParams)
                .apply {
                    //anchorGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                    //marginStart = 0
                    width = CoordinatorLayout.LayoutParams.WRAP_CONTENT
                }
        button.visible()
        button.scaleX = 1f
        button.scaleY = 1f
        button.alpha = 1f
    }

    private fun prepareSinkDown() =
            ValueAnimator.ofInt(0, button.dip(16))
                    .apply {
                        addUpdateListener {
                            val value = it.animatedValue as Int
                            button.layoutParams.let { params ->
                                (params as CoordinatorLayout.LayoutParams).apply {
                                    topMargin = value
                                }
                            }
                        }
                        interpolator = AccelerateInterpolator()
                        duration = DURATION_SINK_DOWN
                    }


    private fun prepareSlideOutAnimation() =
            ValueAnimator.ofInt(button.right, button.dip(16))
                    .apply {
                        addUpdateListener {
                            val value = it.animatedValue as Int
                            button.layoutParams.let { params ->
                                (params as CoordinatorLayout.LayoutParams).apply {
                                    marginEnd = value
                                }
                                button.layoutParams = params
                            }
                        }
                    }


    private fun prepareScaleFade() =
            ValueAnimator.ofFloat(0.9f, 0.0f)
                    .apply {
                        addUpdateListener {
                            val value = it.animatedValue as Float
                            val scale = 1 - (1 - value) * 0.8f
                            button.scaleX = scale
                            button.scaleY = scale
                            button.alpha = 1 - (1 - value) * 0.8f
                            if (it.animatedFraction == 1.0f) {
                                button.gone()
                            }
                        }
                        duration = DURATION_SCALE
                    }


    private fun prepareShrink() =
            ValueAnimator.ofInt(button.width, button.height)
                    .apply {
                        interpolator = DecelerateInterpolator(2f)
                        duration = DURATION_SHRINK
                        addUpdateListener {
                            button.layoutParams.apply {
                                width = it.animatedValue as Int; button.layoutParams = this
                            }
                        }
                    }
}


fun ValueAnimator.after(trigger: () -> Boolean, nextAnim: ValueAnimator, preparation: () -> Unit) {
    this.addUpdateListener {
        if (trigger() && !nextAnim.isRunning) {
            preparation()
            nextAnim.start()
        }
    }
}

private const val DURATION_SHRINK = 250L
private const val DURATION_SCALE = 150L
private const val DURATION_SINK_DOWN = DURATION_SHRINK / 2 + DURATION_SCALE