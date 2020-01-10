package by.gomeltour.presentation.ui.predict.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.gomeltour.R
import by.gomeltour.databinding.ItemPredictBinding
import by.gomeltour.domain.dto.PredictDTO

/**
 * Created by Yahor_Fralou on 9/21/2018 4:43 PM.
 */

class PredictAdapter(var ctx: Context) : RecyclerView.Adapter<PredictAdapter.ViewHolder>() {
    private val predicts = mutableListOf<PredictDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPredictBinding>(LayoutInflater.from(ctx), R.layout.item_predict, parent, false)

        return ViewHolder(binding, ItemPredictViewModel())
    }

    override fun getItemCount(): Int = predicts.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val predict = predicts[position]
        vh.bind(predict)

        // todo remove
        /*with(vh.itemView) {
            txtPredictTitle.text = predict.title
            txtPredictText.text = predict.text
            txtAuthorName.text = predict.authorName
            imgPredictLogo.loadAvatar(predict.authorPic)

            card.setOnClickListener { handler(predict) }
        }*/
    }

    fun update(list: List<PredictDTO>) {
        predicts.clear()
        predicts.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemPredictBinding, val model: ItemPredictViewModel) : RecyclerView.ViewHolder(binding.getRoot()) {
        fun bind(predictDTO: PredictDTO) {
            model.predict = predictDTO
            binding.model = model
        }
    } // todo use DataBinding here
}