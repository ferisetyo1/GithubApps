package feri.com.consumerapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import feri.com.consumerapp.R
import feri.com.consumerapp.database.DatabaseContract.FavoritColumns.Companion.CONTENT_URI
import feri.com.consumerapp.databinding.ActivityMainBinding
import feri.com.consumerapp.shared.getViewModel
import feri.com.githubapps.ui.adapter.AdapterUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = getViewModel { HomeViewModel(contentResolver?.query(CONTENT_URI, null, null, null, null)) }
        binding.vm = vm
        binding.lifecycleOwner
        setObserver()
    }

    private fun setObserver() {
        vm.data?.observe(this, Observer {
            println(it.toString())
            binding.rvFav.visibility = View.GONE
            binding.textEmpty.visibility = View.VISIBLE
            if (it != null) {
                if (it.size>0){
                    binding.rvFav.adapter = AdapterUser(this, it)
                    binding.rvFav.visibility = View.VISIBLE
                    binding.textEmpty.visibility = View.GONE
                    println("show ${it.size}")
                }
            }
        })
    }
}
