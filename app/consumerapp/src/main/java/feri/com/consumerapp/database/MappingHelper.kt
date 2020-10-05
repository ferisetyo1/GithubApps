package feri.com.consumerapp.database

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): List<Favorit> {
        val favList = ArrayList<Favorit>()

        favCursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.LOGIN))
                val avatar =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.AVATARURL))
                favList.add(Favorit(login, avatar))
            }
        }
        return favList
    }
}