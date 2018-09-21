package com.tobe.prediction.presentation.presenter.predict.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tobe.prediction.R
import com.tobe.prediction.domain.Predict
import kotlinx.android.synthetic.main.item_predict.view.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:43 PM.
 */
class PredictAdapter(var ctx: Context, val predicts: List<Predict>, var listener: (Predict) -> Unit) : RecyclerView.Adapter<PredictAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_predict, parent, false))

    override fun getItemCount(): Int = predicts.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val predict = predicts[position]
        with (vh.itemView) {
            txtPredictText.text = predict.text

            root.setOnClickListener { listener(predict) }
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}