package br.com.example.maytheforcebewith_gustavo.ui.fragment.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.example.maytheforcebewith_gustavo.R
import kotlinx.android.synthetic.main.detail_item_custom_view.view.*

class DetailsItem
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        var labelText: String?

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DetailsItem,
            0, 0
        ).apply {

            try {
                labelText = getString(R.styleable.DetailsItem_labelText)

            } finally {
                recycle()
            }
        }
        val view =
            LayoutInflater.from(context).inflate(R.layout.detail_item_custom_view, this, false)

        labelText?.let {
            view.findViewById<TextView>(R.id.tvItemKey).text = it
        }

        addView(view)
    }

    fun setValue(value: String) {
        tvItemValue.text = value
    }


}