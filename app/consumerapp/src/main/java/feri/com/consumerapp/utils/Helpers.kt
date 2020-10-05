package feri.com.githubapps.utils

import android.annotation.SuppressLint
import android.provider.SyncStateContract
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

object Helpers {

    @JvmStatic
    @BindingAdapter("android:avatarurl")
    fun setImageUrl(view: ImageView?, icon: String?) {
        if (view != null && icon != null) {
            Glide.with(view.context).load(icon).into(view)
        }
    }

}