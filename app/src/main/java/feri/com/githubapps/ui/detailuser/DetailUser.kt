package feri.com.githubapps.ui.detailuser


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import feri.com.githubapps.shared.PagerAdapter
import feri.com.githubapps.R
import feri.com.githubapps.data.localDb.DBRepository
import feri.com.githubapps.databinding.ActivityDetailUserBinding
import feri.com.githubapps.network.ApiListener
import feri.com.githubapps.shared.getViewModel


class DetailUser : AppCompatActivity(), ApiListener {
    private lateinit var activityDetailUserBinding: ActivityDetailUserBinding
    private lateinit var vm: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        activityDetailUserBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail_user)
        vm = getViewModel { DetailUserViewModel(this) }
        activityDetailUserBinding.vm = vm
        activityDetailUserBinding.lifecycleOwner = this
        supportActionBar?.hide()
        setToolbar()

        getDataRepository()
        setViewPager()
        setfavorit()
    }

    private fun setfavorit() {
        val db = DBRepository(application)
        db.getFav(intent.getStringExtra("nama"))?.observe(this, Observer {
            if (it != null) {
                activityDetailUserBinding.fabFav.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
                activityDetailUserBinding.fabFav.tag = 1
            }

        })
        activityDetailUserBinding.fabFav.setOnClickListener {
            if (activityDetailUserBinding.fabFav.tag == 1) {
                db.deleteFav(intent.getStringExtra("nama"))
                activityDetailUserBinding.fabFav.apply {
                    setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    tag = 0
                }
            } else {
                vm.dataRepo.value?.let {
                    db.insertFav(it)
                }
                activityDetailUserBinding.fabFav.apply {
                    setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
                    tag = 1
                }

            }
        }
    }

    private fun getDataRepository() {
        activityDetailUserBinding.progressBar2.visibility = View.VISIBLE
        vm.getDetaiuser(intent.getStringExtra("nama"))
    }

    private fun setViewPager() {
        activityDetailUserBinding.pager.adapter = PagerAdapter(
            this,
            supportFragmentManager
        ).apply {
            this.addFragment(FollowerFm().apply {
                arguments = Bundle().apply {
                    putString("nama", intent.getStringExtra("nama"))
                }
            }, getString(R.string.tittle_tab_follower))
            this.addFragment(FollowingFm().apply {
                arguments = Bundle().apply {
                    putString("nama", intent.getStringExtra("nama"))
                }
            }, getString(R.string.tittle_tab_following))
        }
        activityDetailUserBinding.tabs.setupWithViewPager(activityDetailUserBinding.pager)
    }

    private fun setToolbar() {
        activityDetailUserBinding.toolbar.setTitle(intent.getStringExtra("nama"))
        activityDetailUserBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onSuccess(message: String) {
        activityDetailUserBinding.progressBar2.visibility = View.GONE
    }

    override fun onError(message: String) {
        activityDetailUserBinding.progressBar2.visibility = View.GONE
    }
}