package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

import io.reactivex.schedulers.Schedulers

class PeoplePagingDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val search: String?,
    private val useCase: GetPaginatedPeopleUseCase
) : PageKeyedDataSource<Int, People>() {

    var state: MutableLiveData<PeopleDataSourceState> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, People>
    ) {
        state.toLoading()
        compositeDisposable.add(
            useCase.execute(1, search)
                .subscribe(
                    { response ->
                        state.toDone()
                        response.apply {
                            callback.onResult(
                                peoples,
                                0,
                                total,
                                null,
                                2
                            )
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
            useCase.execute(params.key, search)
                .subscribe(
                    { response ->
                        callback.onResult(
                            response.peoples,
                            params.key + 1
                        )
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