package feri.com.githubapps

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
        tv_default.visibility = View.VISIBLE
        tv_result.visibility = View.INVISIBLE
        rv_search.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val ubahbahasa= menu?.findItem(R.id.ubah_bahasa)
        ubahbahasa.setOnMenuItemClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
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
        return true
    }

    private fun showDataSearch(newText: String) {
        progressBar.visibility=View.VISIBLE
        if (newText.length > 0) {
            AndroidNetworking.get("https://api.github.com/search/users?q={query}")
                .addPathParameter("query", newText)
                .addHeaders(
                    "Authorization",
                    "token 8132a56327cd541be68ed0804d82e437a179a7dc"
                )
                .build()
                .getAsObject(
                    ResponSearchModel::class.java,
                    object : ParsedRequestListener<ResponSearchModel> {
                        override fun onResponse(response: ResponSearchModel?) {
                            progressBar.visibility=View.GONE
                            tv_result.text =
                                getString(R.string.search_ditemukan, response?.totalCount)
                            rv_search.adapter = AdapterUser(
                                this@MainActivity,
                                response?.items
                            )
                        }

                        override fun onError(anError: ANError?) {
                            progressBar.visibility=View.GONE
                            Toast.makeText(this@MainActivity,anError?.message, Toast.LENGTH_LONG).show()
                        }
                    })
            tv_default.visibility = View.GONE
            tv_result.visibility = View.VISIBLE
            rv_search.visibility = View.VISIBLE
        } else {
            tv_default.visibility = View.VISIBLE
            tv_result.visibility = View.INVISIBLE
            rv_search.visibility = View.INVISIBLE
        }
    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.ubah_bahasa -> {
//                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//                println("test")
//            }
//        }
//        return super.onContextItemSelected(item)
//    }
}