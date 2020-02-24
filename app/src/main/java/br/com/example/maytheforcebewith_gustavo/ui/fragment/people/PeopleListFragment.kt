package br.com.example.maytheforcebewith_gustavo.ui.fragment.people


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.data.remote.datasource.State
import br.com.example.domain.entity.People

import br.com.example.maytheforcebewith_gustavo.R
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
        initListeners()
        initAdapter()
        initObserves()
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
        viewModel.uiState.observe(viewLifecycleOwner, Observer(::updateUiState))
        viewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            viewModel.handleStateChange(state)
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
            viewModel.peopleList.observe(viewLifecycleOwner, Observer {
                peopleListAdapter.submitList(it)
            })
        }
    }

    private fun updateUiState(state: PeopleListFragmentUIState) {
        when (state) {
            is PeopleListFragmentUIState.Error -> onError(state.error)
            PeopleListFragmentUIState.Loading -> toggleLoading(true)
            is PeopleListFragmentUIState.Success -> onSuccess(state.fromSearch)
        }
    }

    private fun onError(error: Throwable) {
        toggleLoading()
    }

    private fun onSuccess(fromSeach: Boolean) {
        toggleLoading()
        if (!viewModel.listIsEmpty()) {
            peopleListAdapter.setState(State.DONE)
        }
    }

    private fun toggleLoading(show: Boolean = false) {
        progress_bar.visibility =
            if (show) View.VISIBLE
            else View.GONE
    }

    private fun saveFavorite(people: People) {
        viewModel.saveFavorite(people)
    }

}
