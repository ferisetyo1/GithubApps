package feri.com.githubapps.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import feri.com.consumerapp.database.Favorit
import feri.com.consumerapp.databinding.UserItemBinding
import kotlinx.android.extensions.LayoutContainer


class AdapterUser(val ctx: Context, val list: List<Favorit?>?) :
    RecyclerView.Adapter<AdapterUser.ViewHolder>() {
    inner class ViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(get: Favorit?) {
            binding.fav=get
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

}
