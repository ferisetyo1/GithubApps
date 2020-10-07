package feri.com.githubapps.data.localDb

import android.app.Application
import androidx.lifecycle.LiveData
import feri.com.githubapps.model.ResponDetailUser

class DBRepository(application: Application) {

    private var dao: FavoritDao?
    private var cdao: CursorFavDao?

    init {
        val db = AppDatabase.getDatabase(application)
        dao = db?.favDao()
        cdao = db?.cursorFavDao()
        println("DB isopen:${db?.isOpen}")
    }

    fun getFav(string: String) = dao?.get(string)
    fun getAllFav(): ArrayList<Favorit> {
        val favList = ArrayList<Favorit>()
        val data = cdao?.findAll()
        while (data?.moveToNext()!!) {
            favList.add(
                Favorit(
                    data.getString(data.getColumnIndex("login")),
                    data.getString(data.getColumnIndex("avatarUrl"))
                )
            )
        }
        return favList
    }

    fun getAllFav_Live(): LiveData<List<Favorit>?>? = dao?.getAll()

    fun insertFav(user: ResponDetailUser) {
        println(user.toString())
        user.login?.let { user.avatarUrl?.let { it1 -> Favorit(it, it1) } }?.let { dao?.insert(it) }
        println("inserted")
    }

    fun deleteFav(string: String) {
        dao?.delete(string)
        println("deleted")
    }
}