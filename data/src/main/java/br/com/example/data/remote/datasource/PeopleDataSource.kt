package br.com.example.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.mapper.PeoplePayloadMapper
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

import io.reactivex.schedulers.Schedulers

class PeopleDataSource(
    private val starWarsApi: StarWarsApi,
    private val compositeDisposable: CompositeDisposable,
    private val search: String?
) : PageKeyedDataSource<Int, People>() {

    var state: MutableLiveData<PeopleDataSourceState> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, People>
    ) {
        state.toLoading()
        compositeDisposable.add(
            starWarsApi.getPeopleList(1, search)
                .subscribe(
                    { response ->
                        state.toDone()
                        response.apply {
                            if (code() == 200 && body() != null) {
                                val pagedRequestPayload =
                                    (body() as PagedRequestPayload<PeoplePayload>)

                                callback.onResult(
                                    pagedRequestPayload.results.map {
                                        PeoplePayloadMapper.map(it)
                                    },
                                    0,
                                    pagedRequestPayload.count,
                                    null,
                                    2
                                )
                            } else {
                                state.toError(Throwable("${code()} - ${errorBody()?.string()}"))
                            }
                        }
                    },
                    {
                        state.toError(it)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, People>
    ) {
        state.toLoading()
        compositeDisposable.add(
            starWarsApi.getPeopleList(params.key, search)
                .subscribe(
                    { response ->
                        state.toDone()
                        response.apply {
                            if (code() == 200 && body() != null) {
                                val pagedRequestPayload =
                                    (body() as PagedRequestPayload<PeoplePayload>)

                                callback.onResult(
                                    pagedRequestPayload.results.map {
                                        PeoplePayloadMapper.map(it)
                                    },
                                    params.key + 1
                                )
                            } else {
                                if (code() == 404) state.toEnd()
                                else state.toError(Throwable("${code()} - ${errorBody()?.string()}"))
                            }
                        }
                    },
                    {
                        state.toError(it)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, People>
    ) {
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}