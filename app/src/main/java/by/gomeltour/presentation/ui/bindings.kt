package by.gomeltour.presentation.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.gomeltour.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import by.gomeltour.domain.dto.PredictDTO
import by.gomeltour.helper.*
import by.gomeltour.model.loadAppBarUserPhoto
import by.gomeltour.model.loadAvatar
import by.gomeltour.presentation.ui.predict.list.PredictAdapter
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

@BindingAdapter("refreshing")
fun setRefreshing(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    refreshLayout.isRefreshing = refreshing
}

@BindingAdapter("predictsList")
fun setPredictsList(recyclerView: RecyclerView, list: List<PredictDTO>) {
    (recyclerView.adapter as? PredictAdapter)?.update(list)
}

@BindingAdapter("numText")
fun setNumberText(textView: TextView, number: Int) {
    textView.text = number.toString() // todo implement numbers formatting as 28.4k
}

@BindingAdapter("dateLong")
fun setDateLong(textView: TextView, date: Date?) {
    if (date == null) {
        textView.gone()
    } else {
        textView.visible()
        textView.text = dateFormatLong.format(date)
    }
}

@BindingAdapter("extendState")
fun setExtendState(efab: ExtendedFloatingActionButton, isExtend: Boolean) {
    if (isExtend xor efab.isExtended) {
        if (isExtend) efab.extend() else efab.shrink()
    }
}

@BindingAdapter("visible")
fun setEFABVisibility(efab: ExtendedFloatingActionButton, isShow: Boolean) {
    if (isShow xor efab.isShown) {
        if (isShow) efab.show() else efab.hide()
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    imageView.loadAvatar(url)
}

@BindingAdapter("appBarImageUrl")
fun setAppBarImageUrl(imageView: ImageView, url: String?) {
    if (url == null) imageView.setImageResource(R.drawable.ic_user_white_24) else imageView.loadAppBarUserPhoto(url)
}

@BindingAdapter("votes")
fun setVotesNumber(textView: TextView, votesNumber: Int) {
    textView.text = convertVotesPresentation(votesNumber, textView.context)
}
