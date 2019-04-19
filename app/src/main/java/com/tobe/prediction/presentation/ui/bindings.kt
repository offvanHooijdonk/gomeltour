package com.tobe.prediction.presentation.ui

import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.helper.colorWarn
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.ui.predict.list.PredictAdapter
import org.jetbrains.anko.design.longSnackbar
import java.text.DateFormat
import java.util.*

val dateFormatLong: DateFormat = DateFormat.getDateInstance(DateFormat.FULL)

@BindingAdapter("errorMsg")
fun setErrorMessage(textView: TextView, message: String?) {
    textView.visibility = if (message != null) View.VISIBLE else View.GONE
    textView.text = message
}

@BindingAdapter("errorMsg")
fun setErrorSnackbar(coordinatorLayout: CoordinatorLayout, errorMessage: String?) {
    errorMessage?.let {
        if (it.isNotEmpty()) {
            coordinatorLayout.longSnackbar(it).colorError()
        }
    }
}

@BindingAdapter("invalidMsg")
fun setWarnSnackbar(coordinatorLayout: CoordinatorLayout, invalidMessage: String?) {
    invalidMessage?.let {
        if (it.isNotEmpty()) {
            coordinatorLayout.longSnackbar(it).colorWarn()
        }
    }
}

@BindingAdapter("enabled")
fun setEnabled(view: View, enabled: Boolean) {
    view.isEnabled = enabled
}

@BindingAdapter("visible")
fun setVisibleOrGone(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("predictsList")
fun setPredictsList(recyclerView: RecyclerView, list: List<PredictDTO>) {
    (recyclerView.adapter as? PredictAdapter)?.update(list)
}

@BindingAdapter("dateLong")
fun setDateLong(textView: TextView, date: Date?) {
    if (date == null) {
        textView.hide()
    } else {
        textView.show()
        textView.text = dateFormatLong.format(date)
    }
}