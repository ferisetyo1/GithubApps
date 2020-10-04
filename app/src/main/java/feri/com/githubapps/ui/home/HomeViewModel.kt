package feri.com.githubapps.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feri.com.githubapps.data.Repository
import feri.com.githubapps.model.ResponSearchUser
import feri.com.githubapps.network.ApiListener
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val listener: ApiListener) : ViewModel() {

    val dataRepo = MutableLiveData<ResponSearchUser>().apply {
        value = ResponSearchUser()
    }
    val data: LiveData<ResponSearchUser>
        get() = dataRepo

    fun searchUser(string: String) = viewModelScope.launch {
        try {
            val respon = Repository.instance.search(string)
            Log.d("data", "data")
            dataRepo.postValue(respon)
            listener.onSuccess("sukses")
        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError("error")
        }
    }
}