package br.com.example.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.example.data.remote.api.PeopleApi
import br.com.example.data.remote.model.PeoplePayload
import io.reactivex.disposables.CompositeDisposable

class PeopleDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val peopleApi: PeopleApi
): DataSource.Factory<Int, PeoplePayload>() {

    val peopleDataSourceLiveData = MutableLiveData<PeopleDataSource>()

    override fun create(): DataSource<Int, PeoplePayload> {
        val peopleDataSource = PeopleDataSource(peopleApi, compositeDisposable)
        peopleDataSourceLiveData.postValue(peopleDataSource)
        return peopleDataSource
    }

}