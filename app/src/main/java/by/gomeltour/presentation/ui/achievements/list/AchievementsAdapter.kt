package by.gomeltour.presentation.ui.achievements.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemAchievementBinding
import by.gomeltour.model.AchievementModel

class AchievementsAdapter : RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {
    private val achievements = mutableListOf<AchievementModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_achievement, parent, false),
                    ItemAchievementViewModel()
            )

    override fun getItemCount(): Int = achievements.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(achievements[position])
    }

    fun update(list: List<AchievementModel>) {
        achievements.apply { clear(); addAll(list) }
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemAchievementBinding, private val viewModel: ItemAchievementViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AchievementModel) {
            viewModel.achievement.set(model)
            binding.model = viewModel
        }
    }
}