package br.com.example.maytheforcebewith_gustavo.ui.fragment.people


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.data.remote.datasource.State

import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.people_list_fragment.*
import kotlinx.android.synthetic.main.people_list_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PeopleListFragment : Fragment() {

    private lateinit var viewModel: PeopleListViewModel

    private lateinit var peopleListAdapter: PeopleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try{
            viewModel = getViewModel<PeopleListViewModel>()
        } catch (e: Throwable){
            Log.e("View Model", e.stackTrace.toString())
        }

        viewModel.initPeopleList()
        initAdapter(view)
        initState()
    }

    private fun initAdapter(view: View) {
        peopleListAdapter = PeopleListAdapter()
        view.rvPeopleList.apply {
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

    private fun initState() {
        viewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                peopleListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    companion object {
        fun newInstance() =
            PeopleListFragment()
    }
}
