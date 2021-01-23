package br.com.example.data.remote.api

import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {
    @GET("people?format=json")
    suspend fun getPeopleList(
        @Query("page") page: Int,
        @Query("search") name: String?
    ): Response<PagedRequestPayload<PeoplePayload>>

    companion object {
        fun getApi(okHttpClient: OkHttpClient): StarWarsApi =
            Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(StarWarsApi::class.java)
    }
}