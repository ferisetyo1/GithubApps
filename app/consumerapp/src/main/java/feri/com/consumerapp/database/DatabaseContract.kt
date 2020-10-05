package feri.com.consumerapp.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "feri.com.githubapps"
    const val SCHEME = "content"

    class FavoritColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorit"
            const val LOGIN = "login"
            const val AVATARURL = "avatarUrl"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}