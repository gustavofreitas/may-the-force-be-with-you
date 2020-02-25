package br.com.example.data.remote.api

import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import io.reactivex.Maybe
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {
    @GET("people?format=json")
    fun getPeopleList(
        @Query("page") page: Int,
        @Query("search") name: String?
    ): Maybe<Response<PagedRequestPayload<PeoplePayload>>>

    companion object {
        fun getApi(okHttpClient: OkHttpClient): StarWarsApi =
            Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(StarWarsApi::class.java)
    }
}