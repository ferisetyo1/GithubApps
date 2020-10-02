package feri.com.githubapps

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import kotlinx.android.synthetic.main.search_item.view.*


class AdapterUserV2(val ctx: Context, val list: List<ModelUserGeneral?>?) :
    RecyclerView.Adapter<AdapterUserV2.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama = itemView.nama
        val img = itemView.image
        val lyt = itemView.lyt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.search_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(position)
        holder.nama.text = item?.login
        AndroidNetworking.get(item?.avatarUrl)
            .setTag("imageRequestTag")
            .setPriority(Priority.MEDIUM)
            .setBitmapMaxHeight(50)
            .setBitmapMaxWidth(50)
            .setBitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
            .getAsBitmap(object : BitmapRequestListener {
                override fun onResponse(bitmap: Bitmap) {
                    holder.img.setImageBitmap(bitmap)
                }

                override fun onError(error: ANError) {
                }
            })
    }

}
