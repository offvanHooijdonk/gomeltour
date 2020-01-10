package by.gomeltour.model

import android.widget.ImageView
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

fun ImageView.loadAvatar(url: String?) {
    GlideApp.with(this.context)
            .load(url)
            .fallback(R.drawable.ic_person_default_24)
            .placeholder(R.drawable.ic_person_default_24)
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

private val crossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()