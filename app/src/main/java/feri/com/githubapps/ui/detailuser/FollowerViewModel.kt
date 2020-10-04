package feri.com.githubapps.ui.detailuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feri.com.githubapps.data.Repository
import feri.com.githubapps.model.User
import kotlinx.coroutines.launch
import java.lang.Exception

class FollowerViewModel() : ViewModel() {
    var dataRepo = MutableLiveData<List<User>>().apply { value = listOf() }

    fun getFollower(string: String?) = viewModelScope.launch {
        try {
            val respon=Repository.instance.getFollower(string)
            dataRepo.postValue(respon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}