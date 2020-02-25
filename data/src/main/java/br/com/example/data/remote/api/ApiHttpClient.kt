package br.com.example.data.remote.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object ApiHttpClient {
    fun getClient(context: Context, interceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .cache(Cache(context.cacheDir, (50 * 1024).toLong()))
            .addInterceptor(interceptor).build()


}