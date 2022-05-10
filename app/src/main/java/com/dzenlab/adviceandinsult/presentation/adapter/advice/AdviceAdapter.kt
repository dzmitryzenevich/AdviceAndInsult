package com.dzenlab.adviceandinsult.presentation.adapter.advice

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dzenlab.adviceandinsult.models.AdviceGetDB

class AdviceAdapter(diffCallback: DiffUtil.ItemCallback<AdviceGetDB>,
                    private val list: List<Long>,
                    private val clickCallback: ClickCallback) :
    ListAdapter<AdviceGetDB, AdviceViewHolder>(diffCallback) {

    interface ClickCallback {

        fun onLongClickListener(id: Long)
    }

    class AdviceDiff : DiffUtil.ItemCallback<AdviceGetDB>() {

        override fun areItemsTheSame(oldItem: AdviceGetDB, newItem: AdviceGetDB): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: AdviceGetDB, newItem: AdviceGetDB): Boolean =
            oldItem.advice == newItem.advice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder =
        AdviceViewHolder.create(parent)

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {

        val advice = getItem(position)

        holder.bind(advice, list, clickCallback)
    }

    fun getAdviceId(position: Int): Long = currentList[position].id
}