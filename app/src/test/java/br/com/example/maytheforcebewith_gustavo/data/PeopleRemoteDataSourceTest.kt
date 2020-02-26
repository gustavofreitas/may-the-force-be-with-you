package br.com.example.maytheforcebewith_gustavo.data

import br.com.example.data.remote.api.ApiMockInterceptor
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.PeopleRemoteDataSource
import br.com.example.data.remote.datasource.PeopleRemoteDataSourceImpl
import br.com.example.data.remote.datasource.parser.UriToIdParser
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(JUnit4::class)
class PeopleRemoteDataSourceTest : KoinTest {

    val parser: UriToIdParser = mockk()

    private val repositoryMockModule = module {
        single<OkHttpClient> {
            OkHttpClient
                .Builder()
                .addInterceptor(ApiMockInterceptor()).build()
        }
        single<StarWarsApi> {
            StarWarsApi.getApi(get())
        }
        single<PeopleRemoteDataSource> {
            PeopleRemoteDataSourceImpl(get(), parser)
        }

    }

    val peopleRemoteDataSource: PeopleRemoteDataSource by inject()


    @Before
    fun setUp() {
        startKoin {
            loadKoinModules(listOf(repositoryMockModule))
        }
    }

    @Test
    fun assertDataMapper() {
            every{
                parser.parse(any<String>())
            } returns 1

            peopleRemoteDataSource.getPeople(1, null)
                .test()
                .assertComplete()
                .assertValue{
                    it.peoples[0].name == "Luke Skywalker"
                }
    }
}