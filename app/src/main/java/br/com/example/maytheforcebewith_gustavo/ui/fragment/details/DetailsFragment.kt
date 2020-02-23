package br.com.example.maytheforcebewith_gustavo.ui.fragment.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    val args: DetailsFragmentArgs  by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.people?.apply {
            tvDetailsTitle.text = name
            itemHeight.setValue(height)
            itemMass.setValue( mass)
            itemHairColor.setValue(hairColor)
            itemSkinColor.setValue(skinColor)
            itemGender.setValue(gender)
        }
    }
}
