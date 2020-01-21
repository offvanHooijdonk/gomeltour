package by.gomeltour.presentation

import android.content.Context
import android.location.Location
import androidx.annotation.IntegerRes
import by.gomeltour.R
import kotlin.math.abs

fun getInteger(ctx: Context, @IntegerRes res: Int) = ctx.resources.getInteger(res)

fun Context.getInt(@IntegerRes res: Int) = this.resources.getInteger(res)

fun formatDegrees(ctx: Context, value: Double): String {
    val coordString = Location.convert(abs(value), Location.FORMAT_SECONDS)
    val degreeSign = ctx.getString(R.string.degree_sign)
    val minutesSign = ctx.getString(R.string.minutes_sign)
    return try {
        coordString.split(":").let {
            val degree = it[0]
            val minutes = it[1]
            "${if (value < 0) "-" else ""}$degree$degreeSign $minutes$minutesSign"
        }
    } catch (e: Exception) {
        coordString
    }

}

fun formatDistance(ctx: Context, distance: Int): String =
        when (distance.toString().length) {
            in 1..3 -> distance.toString() + ctx.getString(R.string.meter_sign)
            4 -> ((distance / 100) / 10f).toString() + ctx.getString(R.string.kilometer_sign)
            else -> (distance / 1000).toString() + ctx.getString(R.string.kilometer_sign)
        }