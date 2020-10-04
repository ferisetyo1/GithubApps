package feri.com.githubapps.ui.detailuser


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import feri.com.githubapps.shared.PagerAdapter
import feri.com.githubapps.R
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
        activityDetailUserBinding.toolbar.setNavigationIcon(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_arrow_back_24, null
            )
                ?.apply {
                    setTint(
                        ResourcesCompat.getColor(
                            resources,
                            android.R.color.black,
                            null
                        )
                    )
                })
        activityDetailUserBinding.toolbar.setNavigationOnClickListener(object :
            View.OnClickListener {
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })
    }

    override fun onSuccess(message: String) {
        activityDetailUserBinding.progressBar2.visibility = View.GONE
    }

    override fun onError(message: String) {
        activityDetailUserBinding.progressBar2.visibility = View.GONE
    }
}