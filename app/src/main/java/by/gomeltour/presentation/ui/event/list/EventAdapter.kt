package by.gomeltour.presentation.ui.event.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemEventBinding
import by.gomeltour.model.EventModel
import kotlinx.android.synthetic.main.item_event.view.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:43 PM.
 */

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private val events = mutableListOf<EventModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemEventBinding>(LayoutInflater.from(parent.context), R.layout.item_event, parent, false)

        return ViewHolder(binding, ItemEventViewModel())
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val predict = events[position]
        vh.bind(predict)
    }

    fun update(list: List<EventModel>) {
        events.clear()
        events.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemEventBinding, val model: ItemEventViewModel) : RecyclerView.ViewHolder(binding.getRoot()) {
        fun bind(eventModel: EventModel) {
            model.event = eventModel
            binding.model = model
        }
    }
}