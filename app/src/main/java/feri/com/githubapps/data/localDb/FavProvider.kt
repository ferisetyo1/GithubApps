package feri.com.githubapps.data.localDb

import android.content.*
import android.database.Cursor
import android.net.Uri
import feri.com.githubapps.data.localDb.DBContract.AUTHORITY
import feri.com.githubapps.data.localDb.DBContract.CONTENT_URI


class FavProvider : ContentProvider() {

    companion object {

        /*
        Integer digunakan sebagai identifier antara select all sama select by id
         */
        private const val FAV = 1
        private const val FAV_ID = 2
        private const val TABLE_NAME = "favorit"

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAV_ID)
        }
    }

    private lateinit var cursorFavDao: CursorFavDao

    override fun onCreate(): Boolean {
        cursorFavDao = context?.let { AppDatabase.getDatabase(it)?.cursorFavDao() }!!
        return false
    }


    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAV -> cursor = cursorFavDao.findAll()
            FAV_ID -> cursor = cursorFavDao.find(uri.lastPathSegment.toString())
            else -> cursor = null
        }

        return cursor
    }


    override fun getType(uri: Uri): String? {
        return null
    }


    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> cursorFavDao.insert(contentValues?.let {
                Favorit.fromContentValue(
                    it
                )
            })
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }


    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        val updated: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> cursorFavDao.update(
                contentValues?.let { Favorit.fromContentValue(it) }
            )
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> cursorFavDao.delete(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}