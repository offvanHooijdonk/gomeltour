package com.tobe.prediction.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.tobe.prediction.R
import java.math.RoundingMode
import java.text.NumberFormat

/**
 * Created by Yahor_Fralou on 9/18/2018 1:55 PM.
 */
// todo move to 'presentation' package
fun View.visible() =
        this.apply { visibility = View.VISIBLE }

fun View.gone() =
        this.apply { visibility = View.GONE }

fun View.invisible() =
        this.apply { visibility = View.INVISIBLE }

//fun View.isShown() = this.visibility == View.VISIBLE

fun SwipeRefreshLayout.setUpDefault() {
    this.setColorSchemeResources(R.color.refresh_1, R.color.refresh_2, R.color.refresh_3)
}

fun Snackbar.colorError() =
        this.apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    .setTextColor(this.context.resources.getColor(R.color.snackbar_error_text))
        }

fun Snackbar.colorWarn() =
        this.apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    .setTextColor(this.context.resources.getColor(R.color.snackbar_warn_text))
        }

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun convertVotesPresentation(votes: Int, ctx: Context): String {
    val results = convertVotesPresentation(votes)

    return results.first + when (results.second) {
        1 -> ctx.getString(R.string.votes_thousand)
        2 -> ctx.getString(R.string.votes_million)
        3 -> ctx.getString(R.string.votes_billion)
        4 -> ctx.getString(R.string.votes_trillion)
        else -> ""
    }
}

fun convertVotesPresentation(votes: Int): Pair<String, Int> {
    var numReduced = votes
    var range = 0
    while (numReduced >= 1000) {
        numReduced /= 1000
        range++
    }

    return when (numReduced.toString().length) {
        1, 2 -> {
            NumberFormat.getInstance().apply {
                maximumFractionDigits = 1
                minimumFractionDigits = 0
                roundingMode = RoundingMode.DOWN
            }.format(votes.toDouble() / Math.pow(1000.0, range.toDouble())) to range
        }
        else -> {
            numReduced.toString() to range
        }
    }
}