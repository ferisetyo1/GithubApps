package feri.com.githubapps.ui.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feri.com.githubapps.data.Repository
import feri.com.githubapps.model.ResponDetailUser
import feri.com.githubapps.network.ApiListener
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailUserViewModel(val listener: ApiListener) : ViewModel() {
    var dataRepo = MutableLiveData<ResponDetailUser>().apply {
        value = ResponDetailUser()
    }
    val data: LiveData<ResponDetailUser>
        get() = dataRepo

    fun getDetaiuser(string: String) = viewModelScope.launch {
        try {
            val respon = Repository.instance.getDetail(string)
            dataRepo.postValue(respon)
            listener.onSuccess("sukses")
        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError("error")
        }
    }
}