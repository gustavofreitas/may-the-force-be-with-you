package br.com.example.data.remote.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object ApiHttpClient {
    fun getClient(context: Context, interceptor: Interceptor): OkHttpClient {
        val myCache = Cache(context.cacheDir, (50 * 1024).toLong())

        val client: OkHttpClient = OkHttpClient
            .Builder()
            .cache(myCache)
            .addInterceptor(interceptor).build()
        return client
    }
}