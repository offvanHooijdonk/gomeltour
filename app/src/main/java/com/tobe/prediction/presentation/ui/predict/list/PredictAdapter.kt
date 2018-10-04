package com.tobe.prediction.presentation.ui.predict.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tobe.prediction.R
import com.tobe.prediction.domain.dto.PredictDTO
import kotlinx.android.synthetic.main.item_predict.view.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:43 PM.
 */
class PredictAdapter(var ctx: Context, private val predicts: List<PredictDTO>, var listener: (PredictDTO) -> Unit) : RecyclerView.Adapter<PredictAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_predict, parent, false))

    override fun getItemCount(): Int = predicts.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val predict = predicts[position]
        with (vh.itemView) {
            txtPredictText.text = predict.title
            txtAuthorName.text = predict.authorName
            if (predict.authorPic != null) Glide.with(ctx).load(predict.authorPic).into(imgPredictLogo)

            card.setOnClickListener { listener(predict) }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}