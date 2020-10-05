package feri.com.consumerapp.ui.home

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feri.com.consumerapp.database.Favorit
import feri.com.consumerapp.database.MappingHelper

class HomeViewModel(cursor: Cursor?) : ViewModel() {
    val data=MutableLiveData<List<Favorit>>().apply {
        value=MappingHelper.mapCursorToArrayList(cursor)
    }
}