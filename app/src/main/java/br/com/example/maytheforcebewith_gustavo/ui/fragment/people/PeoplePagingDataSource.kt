package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.example.domain.entity.People
import br.com.example.domain.entity.PeopleWithPagingInfo
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeoplePagingDataSource(
    private val useCase: GetPaginatedPeopleUseCase
) : PageKeyedDataSource<Int, People>() {

    var state: MutableLiveData<PeopleDataSourceState> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, People>
    ) {
        load(1) {
            callback.onResult(
                it.peoples,
                0,
                it.total,
                null,
                2
            )
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, People>
    ) {
        load(params.key) {
            callback.onResult(
                it.peoples,
                params.key + 1
            )
        }
    }

    private fun load(page: Int, callback: (PeopleWithPagingInfo) -> Unit) {
        state.toLoading()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = useCase.execute(page, null)
                if (response.total == 0) state.toEnd()
                else {
                    callback(response)
                }
            } catch (error: Throwable) {
                state.toError(error)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, People>
    ) {
    }

    fun retry() {
        if (retryCompletable != null) {
            retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        }
    }
}