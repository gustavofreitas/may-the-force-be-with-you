package br.com.example.data.remote.api

import br.com.example.domain.entity.People
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Completable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FavoriteWebHookService {

    @POST("/b9fca708-ac4c-4f1e-a9d5-138b4dcaaa5b/")
    fun postFavorite(@Body people: People): Completable

    companion object {
        fun getService(): FavoriteWebHookService =
            Retrofit.Builder()
                .baseUrl("https://webhook.site/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient()
                    .newBuilder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
                )
                .build()
                .create(FavoriteWebHookService::class.java)


    }
}