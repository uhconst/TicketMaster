package com.uhc.presentation.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uhc.domain.model.Event
import com.uhc.presentation.R
import com.uhc.presentation.databinding.ItemEventBinding

class EventRecyclerAdapter : RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>() {

    private val events = mutableListOf<Event>()

    var listener: OnEventClickListener? = null

    interface OnEventClickListener {
        fun onClickFavouriteEvent(event: Event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = events.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position], listener)
    }

    fun getEventByPosition(position: Int) = events[position]

    fun notifyChanged(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event, listener: OnEventClickListener?) {
            binding.event = event

            binding.ivStar.setOnClickListener { listener?.onClickFavouriteEvent(event) }
            binding.executePendingBindings()
        }
    }
}