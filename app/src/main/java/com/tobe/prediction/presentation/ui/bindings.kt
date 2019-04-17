package com.tobe.prediction.presentation.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.presentation.ui.predict.list.PredictAdapter

@BindingAdapter("errorMsg")
fun setErrorMessage(textView: TextView, message: String?) {
    textView.visibility = if (message != null) View.VISIBLE else View.GONE
    textView.text = message
}

@BindingAdapter("enabled")
fun setEnabled(view: View, enabled: Boolean) {
    view.isEnabled = enabled
}

@BindingAdapter("visible")
fun setVisibleOrGone(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("predictsList")
fun setPredictsList(recyclerView: RecyclerView, list: List<PredictDTO>) {
    (recyclerView.adapter as? PredictAdapter)?.update(list)
}