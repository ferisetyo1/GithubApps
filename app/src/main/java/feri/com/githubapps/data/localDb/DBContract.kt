package feri.com.githubapps.data.localDb

import android.net.Uri

object DBContract {
    const val AUTHORITY="feri.com.githubapps"
    const val SCHEME = "content"
    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath("favorit")
        .build()
}