package com.dzenlab.adviceandinsult.presentation.adapter.advice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.models.AdviceGetDB
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)

    private val textView: MaterialTextView = itemView.findViewById(R.id.advice_text_view)

    companion object {

        fun create(parent: ViewGroup): AdviceViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_advice, parent, false)

            return AdviceViewHolder(view)
        }
    }

    fun bind(adviceGetDB: AdviceGetDB,
             list: List<Long>,
             clickCallback: AdviceAdapter.ClickCallback) {

        cardView.isChecked = list.contains(adviceGetDB.id)

        cardView.setOnLongClickListener {

            cardView.isChecked = !cardView.isChecked

            clickCallback.onLongClickListener(adviceGetDB.id)

            true
        }

        textView.text = adviceGetDB.advice
    }
}