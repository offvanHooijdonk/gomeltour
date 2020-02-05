package by.gomeltour.presentation.ui.achievements.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemLocationBinding
import by.gomeltour.model.LocationModel
import org.koin.core.KoinComponent
import org.koin.core.get

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>(), KoinComponent {
    private val locations = mutableListOf<LocationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_location, parent, false),
                    ItemLocationViewModel(get())
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
            binding.itemRoot.setOnClickListener {
                it.findNavController().navigate(AchievementsFragmentDirections.toLocationView(viewModel.location.get()?.id ?: ""))
            }
        }
    }
}