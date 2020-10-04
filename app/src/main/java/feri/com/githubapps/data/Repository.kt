package feri.com.githubapps.data

import feri.com.githubapps.model.ResponDetailUser
import feri.com.githubapps.model.ResponSearchUser
import feri.com.githubapps.model.User
import feri.com.githubapps.network.BaseApi

class Repository {
    suspend fun search(string: String?): ResponSearchUser? {
        val response = BaseApi().api().searchUser(string)
        return response.body()
    }

    suspend fun getDetail(string: String?): ResponDetailUser? {
        val response = BaseApi().api().detailUser(string)
        return response.body()
    }

    suspend fun getFollower(string: String?): List<User>? {
        val response = BaseApi().api().followerUser(string)
        return response.body()
    }
    suspend fun getFollowing(string: String?): List<User>? {
        val response = BaseApi().api().followingUser(string)
        return response.body()
    }

    companion object {
        val instance by lazy { Repository() }
    }
}