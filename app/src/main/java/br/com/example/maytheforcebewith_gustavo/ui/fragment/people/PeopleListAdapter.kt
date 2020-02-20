package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.example.data.remote.datasource.State
import br.com.example.data.remote.model.PeoplePayload

class PeopleListAdapter: PagedListAdapter<PeoplePayload, PeopleViewHolder>(PeopleDiffCallback) {


    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



    companion object {
        val PeopleDiffCallback = object : DiffUtil.ItemCallback<PeoplePayload>() {
            override fun areItemsTheSame(oldItem: PeoplePayload, newItem: PeoplePayload): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: PeoplePayload, newItem: PeoplePayload): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}