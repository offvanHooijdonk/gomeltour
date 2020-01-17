package by.gomeltour.presentation.ui.achievements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.model.LocationModel

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {
    private val locations = mutableListOf<LocationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false), ItemLocationViewModel())

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(locations[position])
    }

    fun update(list: List<LocationModel>) {
        locations.apply { clear(); addAll(list) }
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemView: View, private val viewModel: ItemLocationViewModel) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: LocationModel) {
            // todo
        }
    }
}