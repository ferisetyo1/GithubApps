package feri.com.githubapps.ui.favorit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import feri.com.githubapps.data.localDb.DBRepository
import feri.com.githubapps.data.localDb.Favorit

class MyFavoritViewModel(application: Application) : ViewModel() {
    private val db = DBRepository(application)
    var dataFav: LiveData<List<Favorit>?>?

    init {
        dataFav = db.getAllFav_Live()
    }
}