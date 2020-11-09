package com.jwhh.notekeeper.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.viewmodel.Popularity

@BindingAdapter("app:popularityIcon")
fun popularityIcon(view: ImageView, popularity: Popularity) {
    val color = getAssociatedColor(popularity, view.context)
    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color as Int))
    view.setImageDrawable(getDrawablePopularity(popularity, view.context))
}

@BindingAdapter("app:progressTint")
fun tintPopularity(view: ProgressBar, popularity: Popularity) {

    val color = getAssociatedColor(popularity, view.context)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.progressTintList = ColorStateList.valueOf(color as Int)
    }
}

@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)
}

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

fun getAssociatedColor(popularity: Popularity, context: Context?): Any {
    return when (popularity) {
        Popularity.NORMAL -> ContextCompat.getColor(context!!, R.color.colorPrimary)
        Popularity.POPULAR -> ContextCompat.getColor(context!!, R.color.popular)
        Popularity.STAR -> ContextCompat.getColor(context!!, R.color.start)
    }
}

private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
    return when (popularity) {
        Popularity.NORMAL -> {
            ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
        }
        Popularity.POPULAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
        Popularity.STAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
    }
}
