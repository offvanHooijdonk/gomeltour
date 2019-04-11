package com.tobe.prediction.presentation

import android.content.Context
import androidx.annotation.IntegerRes

fun getInteger(ctx: Context, @IntegerRes res: Int) = ctx.resources.getInteger(res)
fun Context.getInt(@IntegerRes res: Int) = this.resources.getInteger(res)