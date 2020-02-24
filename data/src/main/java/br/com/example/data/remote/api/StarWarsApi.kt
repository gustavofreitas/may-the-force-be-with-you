package br.com.example.data.remote.api

import android.content.Context
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.data.utils.isNetworkAvailable
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
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
    ): Single<retrofit2.Response<PagedRequestPayload<PeoplePayload>>>

    companion object {
        fun getApi(appContext: Context): StarWarsApi =
            Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(appContext))
                .build()
                .create(StarWarsApi::class.java)

        private fun getOkHttpClient(context: Context): OkHttpClient {
            val myCache = Cache(context.cacheDir, (50 * 1024).toLong())

            val client: OkHttpClient = OkHttpClient
                .Builder()
                .cache(myCache)
                .addInterceptor(ApiCache(context)).build()
            return client
        }

        internal class ApiCache(private val context: Context) : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                chain.request().newBuilder().apply {
                    addHeader("Content-Type", "application/json")

                    if (context.isNetworkAvailable())
                        addHeader("Cache-Control", "public, max-age=" + 60)
                    else
                        addHeader(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + (60 * 60 * 24 * 7)
                        )

                    return chain.proceed(build())
                }
            }

        }
    }
}