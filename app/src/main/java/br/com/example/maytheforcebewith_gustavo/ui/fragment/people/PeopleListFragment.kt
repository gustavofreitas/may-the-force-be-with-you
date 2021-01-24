package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.domain.entity.People
import br.com.example.maytheforcebewith_gustavo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.people_list_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PeopleListFragment : Fragment(R.layout.people_list_fragment) {

    private val viewModel: PeopleListViewModel by lazy {
        getViewModel()
    }

    private lateinit var peopleListAdapter: PeopleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        peopleListAdapter = PeopleListAdapter(::saveFavorite)
        rvPeopleList.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = true
            peopleListAdapter
            adapter = peopleListAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.peoples.collectLatest {
                peopleListAdapter.submitData(it)
            }
        }

        peopleListAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                toggleLoading(true)
            } else {
                toggleLoading(false)

                when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }?.also {
                    onError(it.error)
                }
            }
        }
    }

    private fun onError(error: Throwable) {
        Log.e("pagination", error.message ?: "")
        MaterialAlertDialogBuilder(context)
            .setTitle("Error")
            .setMessage(error.message)
            .show()
    }

    private fun toggleLoading(isLoading: Boolean) {
        progress_bar.visibility =
            if (isLoading) View.VISIBLE
            else View.GONE
    }

    private fun saveFavorite(people: People) {
        viewModel.saveFavorite(people)
    }
}
