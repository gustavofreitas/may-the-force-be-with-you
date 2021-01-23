package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.domain.entity.People
import br.com.example.maytheforcebewith_gustavo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.people_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PeopleListFragment : Fragment(R.layout.people_list_fragment) {

    private val viewModel: PeopleListViewModel by lazy {
        getViewModel<PeopleListViewModel>()
    }

    private lateinit var peopleListAdapter: PeopleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initPeopleList()
        initAdapter()
        initObserves()

    }

    private fun initObserves() {
        viewModel.getState().observe(viewLifecycleOwner, Observer(::updateUIState))
        viewModel.peopleList.observe(viewLifecycleOwner, Observer {
            peopleListAdapter.submitList(it)
        })
    }

    private fun initAdapter() {
        peopleListAdapter = PeopleListAdapter(::saveFavorite)
        rvPeopleList.apply {
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = true
            peopleListAdapter
            adapter = peopleListAdapter
        }
    }

    private fun updateUIState(state: PeopleDataSourceState) {
        toggleLoading(false)
        when (state) {
            is PeopleDataSourceState.Error -> onError(state.error)
            is PeopleDataSourceState.Done -> onSuccess()
            is PeopleDataSourceState.Loading -> toggleLoading(true)
        }
    }

    private fun onError(error: Throwable) {
        Log.e("pagination", error.message ?: "")
        MaterialAlertDialogBuilder(context)
            .setTitle("Error")
            .setMessage(error.message)
            .setPositiveButton("Retry") { _, _ ->
                viewModel.retry()
            }
            .show()
    }

    private fun onSuccess() {

    }

    private fun toggleLoading(isLoading: Boolean) {
        progress_bar.visibility =
            if (isLoading && (viewModel.listIsEmpty())) View.VISIBLE
            else View.GONE

    }

    private fun saveFavorite(people: People) {
        viewModel.saveFavorite(people)
    }

}
