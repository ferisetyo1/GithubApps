package feri.com.githubapps.ui.detailuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feri.com.githubapps.data.Repository
import feri.com.githubapps.model.User
import kotlinx.coroutines.launch
import java.lang.Exception

class FollowingViewModel() : ViewModel() {
    var dataRepo = MutableLiveData<List<User>>().apply { value = listOf() }

    fun getFollowing(string: String?) = viewModelScope.launch {
        try {
            val respon=Repository.instance.getFollowing(string)
            dataRepo.postValue(respon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}