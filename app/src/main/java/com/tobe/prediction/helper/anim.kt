package com.tobe.prediction.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.dip

@SuppressLint("ClickableViewAccessibility")
fun FloatingActionButton.setDragToRing(targetView: View,
                                       dirToEnd: Boolean,
                                       onStart: () -> Unit,
                                       onCancel: () -> Unit,
                                       onHit: (Boolean) -> Unit,
                                       onSuccess: () -> Unit) { // todo implement with Builder?

    this.setOnTouchListener(object : View.OnTouchListener {
        val DURATION_CANCEL = 150L
        val DURATION_TO_CENTER = 50L

        private var xDelta = 0.0f
        private var xStart = 0.0f
        private var limitX = 0.0f
        private var isInit = false
        private var width = 0.0f
        private var isHit = false
        private var posStartInTarget = 0.0f

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (!isInit) {
                limitX = if (dirToEnd) targetView.x + targetView.width else targetView.x
                xStart = v.x
                width = v.width.toFloat()
                posStartInTarget = targetView.x + targetView.width - this.width
                isInit = true
            }

            val x = event.rawX
            val newX = x - xDelta

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    xDelta = x - v.x

                    onStart()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isMovementAllowed(newX)) {
                        v.animate()
                                .x(newX)
                                .setDuration(0)
                                .start()
                    }

                    isHit = isTargetHit(newX).also {
                        if (isHit != it) onHit(it)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (isTargetHit(newX)) {
                        animateToTargetCenter(newX) {
                            onSuccess()
                        }
                    } else {
                        onCancel()
                        v.animate()
                                .x(xStart)
                                .setDuration(DURATION_CANCEL)
                                .setInterpolator(DecelerateInterpolator())
                                .start()
                    }
                }
            }

            return true
        }

        private fun animateToTargetCenter(location: Float, doAfter: () -> Unit) {
            if (Math.abs(location - posStartInTarget) > dip(4)) {
                this@setDragToRing.animate()
                        .x(posStartInTarget)
                        .setDuration(DURATION_TO_CENTER)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                doAfter()
                            }
                        })
                        .start()
            } else {
                doAfter()
            }
        }

        private fun isMovementAllowed(location: Float) = if (dirToEnd) {
            location > xStart && location + width < limitX
        } else {
            location < xStart && location > limitX
        }

        private fun isTargetHit(location: Float) = if (dirToEnd) {
            location + width > limitX - width / 2
        } else {
            location < limitX + width / 2
        }
    })
}