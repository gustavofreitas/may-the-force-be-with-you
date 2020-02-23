package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.animation.AnimatorInflater
import android.animation.StateListAnimator
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.example.domain.entity.People
import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.people_list_item.view.*


class PeopleViewHolder(private val view: View, private val saveFavorite: (People) -> Unit) : RecyclerView.ViewHolder(view) {

    private var isFavorite = false

    fun bind(people: People?) {
        if (people != null) {
            setUpFilledItem(people)
        } else {
            setUpPlaceHolderItem()
        }
    }

    private fun setUpPlaceHolderItem() {
        setUpPeopleNamePlaceHolder(view.tvName)
        setUpFavoriteImageButtonPlaceHolder(view.ibFavorite)
        view.setOnClickListener { }
    }

    private fun setUpFilledItem(people: People) {
        setUpPeopleName(view.tvName, people)
        setUpFavoriteImageButton(view.ibFavorite, people)
        setUpNavigation(people)
    }

    private fun setUpNavigation(people: People) {

        view.cardContainer.apply {
            val stateListAnimator: StateListAnimator = AnimatorInflater
                .loadStateListAnimator(view.context, R.animator.lift_on_touch)

            setStateListAnimator(stateListAnimator)

            setOnClickListener { view ->
                PeopleListFragmentDirections.actionPeopleListFragmentToDetailsFragment(people)
                    .also {
                        view.findNavController().navigate(it)
                    }
            }
        }

    }

    private fun setUpPeopleNamePlaceHolder(tvName: TextView) {
        tvName.apply {
            text = resources.getString(R.string.placeholderText)
            setTextAppearanceCompat()
        }
    }

    private fun setUpFavoriteImageButtonPlaceHolder(ibFavorite: ImageButton) {
        isFavorite = false
        ibFavorite.apply {
            setOnClickListener {}
            setImageDrawable(view.context.getDrawable(getDrawableFavoriteOnOff()))
        }
    }

    private fun setUpPeopleName(tvName: TextView, people: People) {
        tvName.apply {
            text = people.name
            setTextAppearanceCompat()
        }
    }

    @Suppress("DEPRECATION")
    private fun TextView.setTextAppearanceCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            setTextAppearance(android.R.style.TextAppearance_Material_SearchResult_Title)
        else {
            setTextAppearance(context, android.R.style.TextAppearance_Material_SearchResult_Title)
        }
    }

    private fun setUpFavoriteImageButton(ibFavorite: ImageButton, people: People) {
        ibFavorite.apply {
            setOnClickListener {
                isFavorite = !isFavorite
                setImageDrawable(view.context.getDrawable(getDrawableFavoriteOnOff()))
                saveFavorite(people)
            }
        }
    }

    private fun getDrawableFavoriteOnOff() =
        if (isFavorite) android.R.drawable.btn_star_big_on
        else android.R.drawable.btn_star_big_off

    companion object {
        fun create(parent: ViewGroup, saveFavorite: (People) -> Unit): PeopleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.people_list_item, parent, false)
            return PeopleViewHolder(view, saveFavorite)
        }
    }
}