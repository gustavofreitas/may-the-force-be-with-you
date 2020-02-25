package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.example.domain.entity.People

class PeopleListAdapter(
    private val saveFavorite: (People) -> Unit
) : PagedListAdapter<People, PeopleViewHolder>(PeopleDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder.create(parent, saveFavorite)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val PeopleDiffCallback = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem == newItem
            }
        }
    }
}