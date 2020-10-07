package feri.com.githubapps.ui.favorit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import feri.com.githubapps.R
import feri.com.githubapps.databinding.ActivityMyFavoritBinding
import feri.com.githubapps.shared.getViewModel
import feri.com.githubapps.ui.adapter.AdapterUser

class MyFavorit : AppCompatActivity() {
    private lateinit var myFavoritBinding: ActivityMyFavoritBinding
    private lateinit var vm: MyFavoritViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myFavoritBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_favorit)
        vm = getViewModel { MyFavoritViewModel(application) }
        myFavoritBinding.vm = vm
        myFavoritBinding.lifecycleOwner
        supportActionBar?.hide()
        myFavoritBinding.toolbar2.setNavigationOnClickListener { onBackPressed() }
        setObserver()
    }

    private fun setObserver() {
        vm.dataFav?.observe(this, Observer {
            it?.let {
                val users = it.map { it.toUser() }
                myFavoritBinding.rvFav.adapter = AdapterUser(this, users, false)
            }
        })
    }
}