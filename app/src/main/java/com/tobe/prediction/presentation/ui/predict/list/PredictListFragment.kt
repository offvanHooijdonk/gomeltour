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
import com.tobe.prediction.di.dependency
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.helper.setUp
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.presenter.predict.list.PredictListPresenter
import com.tobe.prediction.presentation.ui.predict.view.PredictEditDialog
import kotlinx.android.synthetic.main.fr_predict_list.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class PredictListFragment : Fragment(), IPredictListView {
    @Inject
    lateinit var presenter: PredictListPresenter

    private lateinit var adapter: PredictAdapter
    private val predicts = mutableListOf<PredictDTO>()
    private lateinit var ctx: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = requireContext()
        return inflater.inflate(R.layout.fr_predict_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dependency().predictComponent().inject(this)
        presenter.attachView(this)

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

    override fun onStart() {
        super.onStart()
        presenter.loadPredictList()
    }

    override fun onDataLoaded(list: List<PredictDTO>) {
        predicts.clear()
        predicts.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showError(th: Throwable?) {
        rvPredicts.snackbar("Error loading data").colorError()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun onItemPicked(predict: PredictDTO) {
        rvPredicts.snackbar("Oh you clicked", "Yes") { }
    }
}