package feri.com.githubapps.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import feri.com.githubapps.R
import java.text.SimpleDateFormat

object Helpers {

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    @BindingAdapter("android:date")
    fun setDate(view: TextView?, date: String?) {
        if (!date.isNullOrEmpty()) {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm")
            view?.text = view?.context?.resources?.getString(
                R.string.gabung_pada,
                formatter.format(parser.parse(date))
            )
        } else {
            view?.text = "-"
        }
    }

    @JvmStatic
    @BindingAdapter("android:avatarurl")
    fun setImageUrl(view: ImageView?, icon: String?) {
        if (view != null && icon != null) {
            Glide.with(view.context).load(icon).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("android:bio")
    fun setBio(view: TextView?, string: String?) {
        view?.text = string ?: "\"....\""
    }
}