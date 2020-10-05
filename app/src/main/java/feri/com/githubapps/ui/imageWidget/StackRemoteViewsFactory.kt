package feri.com.githubapps.ui.imageWidget

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import feri.com.githubapps.R
import feri.com.githubapps.data.localDb.DBRepository
import feri.com.githubapps.data.localDb.Favorit

internal class StackRemoteViewsFactory(
    private val mContext: Context,
    application: Application
) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Favorit>()
    private val DBRepo = DBRepository(application)

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val dataFav = DBRepo.getAllFav()
        dataFav.map {
            mWidgetItems.add(it)
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        val fav = mWidgetItems.get(position)
        val bitmap=Glide.with(mContext).asBitmap().load(fav.avatarUrl).submit().get()
        rv.setImageViewBitmap(R.id.imgView,bitmap)

        val extras = bundleOf(
            ImagesBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imgView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}