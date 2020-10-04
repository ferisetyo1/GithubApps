package feri.com.githubapps.network

interface ApiListener {
    fun onSuccess(message: String)
    fun onError(message: String)
}