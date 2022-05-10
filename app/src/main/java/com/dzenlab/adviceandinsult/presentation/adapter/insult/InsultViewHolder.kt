package com.dzenlab.adviceandinsult.presentation.adapter.insult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.models.InsultGetDB
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class InsultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)

    private val insultTextView: MaterialTextView = itemView.findViewById(R.id.insult_text_view)

    private val commentTextView: MaterialTextView = itemView.findViewById(R.id.comment_text_view)

    companion object {

        fun create(parent: ViewGroup): InsultViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_insult, parent, false)

            return InsultViewHolder(view)
        }
    }

    fun bind(insultGetDB: InsultGetDB, clickCallback: InsultAdapter.ClickCallback) {

        cardView.setOnClickListener {

            clickCallback.onClickListener(insultGetDB.id, insultGetDB.insult)
        }

        insultTextView.text = insultGetDB.insult

        commentTextView.text = insultGetDB.comment
    }
}