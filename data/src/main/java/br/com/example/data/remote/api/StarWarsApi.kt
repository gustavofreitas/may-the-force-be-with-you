package br.com.example.data.remote.api

import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {
    @GET("people?format=json")
    fun getPeopleList(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int
    ): Single<PagedRequestPayload<PeoplePayload>>

    @GET("people?format=json")
    fun getPeopleByName(
        @Query("search") name: String,
        @Query("limit") pageSize: Int = 10
    ): Single<PagedRequestPayload<PeoplePayload>>

    companion object {
        fun getApi(): StarWarsApi =
            Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StarWarsApi::class.java)
    }
}