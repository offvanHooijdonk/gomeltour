package com.tobe.prediction.presentation.ui.predict.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tobe.prediction.R
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.helper.setUp
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.ui.predict.view.PredictEditDialog
import kotlinx.android.synthetic.main.fr_predict_list.*
import org.jetbrains.anko.design.snackbar
import java.util.*

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class PredictListFragment : Fragment(), IPredictListView {
    private lateinit var adapter: PredictAdapter
    private val predicts = mutableListOf<Predict>()
    private lateinit var ctx: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = requireContext()
        return inflater.inflate(R.layout.fr_predict_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        rvPredicts.layoutManager = LinearLayoutManager(ctx)
        adapter = PredictAdapter(ctx, predicts, this::onItemPicked)
        rvPredicts.adapter = adapter
        refreshPredicts.setUp()
        refreshPredicts.setOnRefreshListener { /* todo sort of refresh */ Handler().postDelayed({ refreshPredicts.isRefreshing = false }, 1500) }

        rvPredicts.snackbar("${Session.user?.name}") // todo remove when user is shown somewhere

        fabAdd.setOnClickListener {
            PredictEditDialog().show(fragmentManager, "one")
        }
    }

    private fun onItemPicked(predict: Predict) {
        rvPredicts.snackbar("Oh you clicked", "Yes") { }
    }

    @Deprecated("For sample data till a source appears")
    private fun initData() {
        predicts.apply {
            val list = arrayOf<String>()
            add(Predict("", "", "A question number #1", Date(), true, list))
            add(Predict("", "", "How much wood would the woodchuck chuck?", Date(), true, list))
            add(Predict("", "", "What if God had a name?", Date(), true, list))
            add(Predict("", "", "How many roads must a man walk down?", Date(), true, list))
        }
    }
}