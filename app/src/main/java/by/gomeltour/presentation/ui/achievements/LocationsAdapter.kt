package by.gomeltour.presentation.ui.achievements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemLocationBinding
import by.gomeltour.model.LocationModel

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {
    private val locations = mutableListOf<LocationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_location, parent, false),
                    ItemLocationViewModel()
            )

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(locations[position])
    }

    fun update(list: List<LocationModel>) {
        locations.apply { clear(); addAll(list) }
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemLocationBinding, private val viewModel: ItemLocationViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: LocationModel) {
            viewModel.location.set(model)
            binding.model = viewModel
        }
    }
}