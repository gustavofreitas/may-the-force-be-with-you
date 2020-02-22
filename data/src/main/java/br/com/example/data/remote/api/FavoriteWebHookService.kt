package br.com.example.data.remote.api

import br.com.example.domain.entity.People
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface FavoriteWebHookService {

    @POST
    fun postFavite(@Body people: People)

    companion object {
        fun getService(): FavoriteWebHookService =
            Retrofit.Builder()
                .baseUrl("https://webhook.site/b9fca708-ac4c-4f1e-a9d5-138b4dcaaa5b")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FavoriteWebHookService::class.java)
    }
}