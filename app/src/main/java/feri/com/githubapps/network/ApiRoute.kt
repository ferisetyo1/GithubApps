package feri.com.githubapps.network

import feri.com.githubapps.model.ResponDetailUser
import feri.com.githubapps.model.ResponSearchUser
import feri.com.githubapps.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRoute {

    @Headers("Authorization:token 8132a56327cd541be68ed0804d82e437a179a7dc")
    @GET("search/users")
    suspend fun searchUser(@Query("q") string: String?): Response<ResponSearchUser>

    @Headers("Authorization:token 8132a56327cd541be68ed0804d82e437a179a7dc")
    @GET("users/{username}")
    suspend fun detailUser(@Path("username") string: String?): Response<ResponDetailUser>

    @Headers("Authorization:token 8132a56327cd541be68ed0804d82e437a179a7dc")
    @GET("users/{username}/followers")
    suspend fun followerUser(@Path("username") string: String?): Response<List<User>>

    @Headers("Authorization:token 8132a56327cd541be68ed0804d82e437a179a7dc")
    @GET("users/{username}/following")
    suspend fun followingUser(@Path("username") string: String?): Response<List<User>>

}