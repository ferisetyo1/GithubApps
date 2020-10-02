package feri.com.githubapps


import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import kotlinx.android.synthetic.main.activity_detail_user.*
import java.time.format.DateTimeFormatter
import java.util.*


class DetailUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar?.hide()
        toolbar.setTitle(intent.getStringExtra("nama"))
        toolbar.setNavigationIcon(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_arrow_back_24, null)
                ?.apply {
                    setTint(
                        ResourcesCompat.getColor(
                            resources,
                            android.R.color.black,
                            null
                        )
                    )
                })
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })

        progressBar2.visibility=View.VISIBLE
        AndroidNetworking.get("https://api.github.com/users/{username}")
            .addPathParameter("username", intent.getStringExtra("nama"))
            .addHeaders(
                "Authorization",
                "token 8132a56327cd541be68ed0804d82e437a179a7dc"
            )
            .build()
            .getAsObject(ResponDetailUser::class.java,
                object : ParsedRequestListener<ResponDetailUser> {
                    override fun onResponse(response: ResponDetailUser?) {
                        progressBar2.visibility=View.GONE
                        setImage(response?.avatarUrl)
                        val dateTime = response?.createdAt?.substring(0, 10)?.split("-")
                        bio.text = response?.bio ?: "\"....\""
                        tgl_gabung.text = getString(
                            R.string.gabung_pada,
                            dateTime?.get(2), dateTime?.get(1), dateTime?.get(0)
                        )
                    }

                    override fun onError(anError: ANError?) {
                        progressBar2.visibility=View.GONE
                        Toast.makeText(this@DetailUser, anError?.message, Toast.LENGTH_LONG).show()
                    }

                })
        pager.adapter = Pageradapter(this, supportFragmentManager).apply {
            this.addFragment(FollowerFm().apply {
                arguments = Bundle().apply {
                    putString("nama",intent.getStringExtra("nama"))
                }
            }, getString(R.string.tittle_tab_follower))
            this.addFragment(FollowingFm().apply {
                arguments = Bundle().apply {
                    putString("nama",intent.getStringExtra("nama"))
                }
            }, getString(R.string.tittle_tab_following))
        }
        tabs.setupWithViewPager(pager)
    }

    private fun setImage(avatarUrl: String?) {
        AndroidNetworking.get(avatarUrl)
            .setTag("imageRequestTag")
            .setPriority(Priority.MEDIUM)
            .setBitmapMaxHeight(100)
            .setBitmapMaxWidth(100)
            .setBitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
            .getAsBitmap(object : BitmapRequestListener {
                override fun onResponse(bitmap: Bitmap) {
                    imageView.setImageBitmap(bitmap)
                }

                override fun onError(error: ANError) {
                }
            })
    }
}