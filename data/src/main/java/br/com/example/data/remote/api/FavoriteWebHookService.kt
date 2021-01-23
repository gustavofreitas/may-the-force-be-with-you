package br.com.example.data.remote.api

import br.com.example.domain.entity.People
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface FavoriteWebHookService {

    @POST("/4ac8e5fa-d054-459a-bbe0-e3d55955fde9")
    suspend fun postFavorite(@Body people: People): Response<ResponseBody>

    companion object {
        fun getService(): FavoriteWebHookService =
            Retrofit.Builder()
                .baseUrl("https://webhook.site/")
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