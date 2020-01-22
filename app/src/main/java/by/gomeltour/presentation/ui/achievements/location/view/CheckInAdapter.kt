package by.gomeltour.presentation.ui.achievements.location.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemCheckInBinding
import by.gomeltour.model.CheckInModel

class CheckInAdapter : RecyclerView.Adapter<CheckInAdapter.ViewHolder>() {
    private val checkList = mutableListOf<CheckInModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_check_in, parent, false),
                    ItemChekInViewModel()
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(checkList[position])
    }

    override fun getItemCount(): Int = checkList.size

    fun update(list: List<CheckInModel>) {
        checkList.apply { clear(); addAll(list) }
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCheckInBinding, private val viewModel: ItemChekInViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CheckInModel) {
            viewModel.checkIn.set(model)
            binding.model = viewModel
        }
    }
}