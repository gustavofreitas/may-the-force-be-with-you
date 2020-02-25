package br.com.example.maytheforcebewith_gustavo

import androidx.paging.DataSource
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.maytheforcebewith_gustavo.ui.fragment.people.PeoplePagingDataSource
import br.com.example.domain.entity.People
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class PeopleListDataSourceTest: KoinComponent {

    private lateinit var datasource: DataSource<Int, People>
    private val api: StarWarsApi by inject(named("apiMock"))

    //@Before
    fun setUp(){
        /*datasource =
            PeoplePagingDataSource(
                api,
                CompositeDisposable(),
                null
            )*/
    }

    //@Test
    fun result(){

    }

}