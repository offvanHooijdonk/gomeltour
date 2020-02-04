package by.gomeltour.presentation.ui.achievements.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.app.LOGCAT
import by.gomeltour.databinding.ItemAchievementBinding
import by.gomeltour.model.AchievementModel

class AchievementsAdapter : PagedListAdapter<AchievementModel, AchievementsAdapter.ViewHolder>(DIFF_CALLBACK) {
    /*private val achievements = mutableListOf<AchievementModel>()*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_achievement, parent, false),
                    ItemAchievementViewModel()
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.i(LOGCAT, "Position at $position")
    }

/*    fun update(list: List<AchievementModel>) {
        achievements.apply { clear(); addAll(list) }
        notifyDataSetChanged()
    }*/

    class ViewHolder(private val binding: ItemAchievementBinding, private val viewModel: ItemAchievementViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AchievementModel?) {
            viewModel.achievement.set(model ?: AchievementModel("", "", "", ""))
            binding.model = viewModel
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AchievementModel>() {
            override fun areItemsTheSame(oldItem: AchievementModel, newItem: AchievementModel): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: AchievementModel, newItem: AchievementModel): Boolean = oldItem == newItem
        }
    }
}