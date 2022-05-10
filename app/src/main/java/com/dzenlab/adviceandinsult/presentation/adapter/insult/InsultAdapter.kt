package com.dzenlab.adviceandinsult.presentation.adapter.insult

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dzenlab.adviceandinsult.models.InsultGetDB

class InsultAdapter(diffCallback: DiffUtil.ItemCallback<InsultGetDB>,
                    private val clickCallback: ClickCallback) :
    ListAdapter<InsultGetDB, InsultViewHolder>(diffCallback) {

    interface ClickCallback {

        fun onClickListener(id: Long, insult: String)
    }

    class InsultDiff : DiffUtil.ItemCallback<InsultGetDB>() {

        override fun areItemsTheSame(oldItem: InsultGetDB, newItem: InsultGetDB): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: InsultGetDB, newItem: InsultGetDB): Boolean =
            oldItem.insult == newItem.insult
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsultViewHolder =
        InsultViewHolder.create(parent)

    override fun onBindViewHolder(holder: InsultViewHolder, position: Int) {

        val advice = getItem(position)

        holder.bind(advice, clickCallback)
    }

    fun getAdviceId(position: Int): Long = currentList[position].id
}