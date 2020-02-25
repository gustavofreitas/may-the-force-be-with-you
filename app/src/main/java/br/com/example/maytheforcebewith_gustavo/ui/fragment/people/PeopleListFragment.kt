package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.domain.entity.People

import br.com.example.maytheforcebewith_gustavo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.people_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PeopleListFragment : Fragment() {

    private val viewModel: PeopleListViewModel by lazy {
        getViewModel<PeopleListViewModel>()
    }

    private lateinit var peopleListAdapter: PeopleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initPeopleList()
        initAdapter()
        initObserves()
        initListeners()

    }

    override fun onPause() {
        super.onPause()
        etSearch.text?.clear()
        viewModel.clearSearch()
    }

    private fun initListeners() {
        etSearch.setOnEditorActionListener { editText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.updatePeopleList(editText.text.toString())
            }
            true
        }
        ilSearch.setEndIconOnClickListener {
            etSearch.text?.clear()
            etSearch.clearFocus()
            viewModel.updatePeopleList()

        }
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
        toggleLoading(state)
        when (state) {
            is PeopleDataSourceState.Error -> onError(state.error)
            is PeopleDataSourceState.Done -> onSuccess()
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

    private fun toggleLoading(state: PeopleDataSourceState) {
        progress_bar.visibility =
            when {
                state is PeopleDataSourceState.Loading && (viewModel.listIsEmpty() || viewModel.fromSearch) -> View.VISIBLE
                else -> View.GONE
            }
    }

    private fun saveFavorite(people: People) {
        viewModel.saveFavorite(people)
    }

}
