package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.people_list_item.view.*


class PeopleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var isFavorite = false

    fun bind(people: PeoplePayload?) {
        if(people != null){
            setUpPeopleName(view.tvName, people)
            setUpFavoriteImageButton(view.ibFavorite, people)
        } else {
            setUpPeopleNamePlaceHolder(view.tvName)
            setUpFavoriteImageButtonPlaceHolder(view.ibFavorite)
        }
    }

    private fun setUpPeopleNamePlaceHolder(tvName: TextView) {
        tvName.apply {
            text = resources.getString(R.string.placeholderText)
            setTextAppearanceCompat(R.style.TextAppearance_Placeholder)
        }
    }

    private fun setUpFavoriteImageButtonPlaceHolder(ibFavorite: ImageButton){
        isFavorite = false
        ibFavorite.apply {
            setOnClickListener {}
            setImageDrawable(view.context.getDrawable(getDrawableFavoriteOnOff()))
        }
    }

    private fun setUpPeopleName(tvName: TextView, people: PeoplePayload){
        tvName.apply {
            text = people.name
            setTextAppearanceCompat(android.R.style.TextAppearance_Material_SearchResult_Title)
        }
    }

    private fun TextView.setTextAppearanceCompat(resource: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            setTextAppearance(android.R.style.TextAppearance_Material_SearchResult_Title)
        else
            setTextAppearance(context, android.R.style.TextAppearance_Material_SearchResult_Title)
    }

    private fun setUpFavoriteImageButton(ibFavorite: ImageButton, people: PeoplePayload){


        ibFavorite.setOnClickListener { ibFavorite ->
            isFavorite = !isFavorite

            (ibFavorite as ImageButton)
                .setImageDrawable(view.context.getDrawable(getDrawableFavoriteOnOff()))
        }
    }

    private fun getDrawableFavoriteOnOff() =
        if (isFavorite) android.R.drawable.btn_star_big_on
        else android.R.drawable.btn_star_big_off

    companion object {
        fun create(parent: ViewGroup): PeopleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.people_list_item, parent, false)
            return PeopleViewHolder(view)
        }
    }
}