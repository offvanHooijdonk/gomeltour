package by.gomeltour.service

import android.widget.ImageView
import androidx.annotation.DrawableRes
import by.gomeltour.R
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

/**
 * Created by Yahor_Fralou on 10/29/2018 6:30 PM.
 */

@GlideModule
class GlideAppModule : AppGlideModule()

fun ImageView.loadImageRounded(url: String?, @DrawableRes placeholderResId: Int) {
    GlideApp.with(this.context)
            .load(url)
            .fallback(placeholderResId)
            .placeholder(placeholderResId)
            .apply(RequestOptions().optionalCircleCrop())
            .into(this)
}

fun ImageView.loadAppBarUserPhoto(url: String?) {
    GlideApp.with(this.context)
            .load(url)
            .apply(RequestOptions().optionalCircleCrop())
            .transition(withCrossFade(crossFadeFactory))
            .fallback(R.drawable.ic_user_white_24)
            .placeholder(R.drawable.ic_user_white_24)
            .into(this)
}

fun ImageView.loadPoster(url: String?) {
    GlideApp.with(this.context)
            .load(url)
            .transition(withCrossFade(crossFadeFactory))
            .fallback(R.drawable.ic_museum_24)
            .placeholder(R.drawable.ic_museum_24)
            .into(this)
}

private val crossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()