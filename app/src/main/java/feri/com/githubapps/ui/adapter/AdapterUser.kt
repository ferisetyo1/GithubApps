package feri.com.githubapps.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import feri.com.githubapps.databinding.SearchItemBinding
import feri.com.githubapps.model.User
import feri.com.githubapps.ui.detailuser.DetailUser
import kotlinx.android.extensions.LayoutContainer


class AdapterUser(val ctx: Context, val list: List<User?>?,val clicked:Boolean) :
    RecyclerView.Adapter<AdapterUser.ViewHolder>() {
    inner class ViewHolder(private var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(get: User?) {
            binding.user=get
            if (clicked){
                containerView?.context.let {
                    containerView?.setOnClickListener {
                        ctx.startActivity(
                            Intent(
                                ctx,
                                DetailUser::class.java
                            ).putExtra("nama", get?.login)
                        )
                    }
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

}
