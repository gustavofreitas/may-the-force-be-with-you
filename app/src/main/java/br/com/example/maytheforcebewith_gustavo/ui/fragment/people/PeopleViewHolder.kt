package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.people_list_item.view.*


class PeopleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var isFavorite = false

    fun bind(people: PeoplePayload?) {
        people?.let {
            view.tvName.text = it.name

            view.ibFavorite.setOnClickListener {
                val ib  = it as ImageButton
                isFavorite = !isFavorite
                val resource =
                    if(isFavorite) android.R.drawable.btn_star_big_on
                    else android.R.drawable.btn_star_big_off

                ib.setImageDrawable(view.context.getDrawable(resource))
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): PeopleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.people_list_item, parent, false)
            return PeopleViewHolder(view)
        }
    }
}