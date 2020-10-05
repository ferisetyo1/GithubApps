package feri.com.githubapps.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import feri.com.githubapps.R
import feri.com.githubapps.databinding.ActivityMainBinding
import feri.com.githubapps.model.ResponSearchUser
import feri.com.githubapps.network.ApiListener
import feri.com.githubapps.shared.getViewModel
import feri.com.githubapps.ui.adapter.AdapterUser
import feri.com.githubapps.ui.setting.Setting
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity(), ApiListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var vm: HomeViewModel
    private lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = getViewModel { HomeViewModel(this) }
        activityMainBinding.vm = vm
        activityMainBinding.lifecycleOwner = this
        job= Job()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
        setDefaultViewConfig()
        setObserver()
    }

    private fun setObserver() {
        vm.dataRepo.observe(this, Observer {
            if (it.items != null) {
                activityMainBinding.rvSearch.adapter =
                    AdapterUser(this, it.items,true)
                activityMainBinding.tvDefault.visibility = View.GONE
                activityMainBinding.tvResult.visibility = View.VISIBLE
                activityMainBinding.rvSearch.visibility = View.VISIBLE
            }else{
                activityMainBinding.tvDefault.visibility = View.VISIBLE
                activityMainBinding.tvResult.visibility = View.INVISIBLE
                activityMainBinding.rvSearch.visibility = View.INVISIBLE
            }
        })
    }

    private fun setDefaultViewConfig() {
        activityMainBinding.tvDefault.visibility = View.VISIBLE
        activityMainBinding.tvResult.visibility = View.INVISIBLE
        activityMainBinding.rvSearch.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val ubahbahasa = menu.findItem(R.id.ubah_bahasa)
        ubahbahasa.setOnMenuItemClickListener {
            startActivity(Intent(this,Setting::class.java))
            return@setOnMenuItemClickListener true
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showDataSearch(query)
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                showDataSearch(newText)
                return false
            }
        })
        searchView.setOnCloseListener {
            if (progressBar.visibility == View.GONE && rv_search.visibility == View.INVISIBLE) {
                progressBar.visibility = View.GONE
            }
            return@setOnCloseListener true
        }
        return true
    }

    private fun showDataSearch(newText: String) {
        job.cancel()
        if (newText.length > 0) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
            job=vm.searchUser(newText)
        }else{
            vm.dataRepo.postValue(ResponSearchUser())
        }
    }

    override fun onSuccess(message: String) {
        activityMainBinding.progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        activityMainBinding.progressBar.visibility = View.GONE
    }
}