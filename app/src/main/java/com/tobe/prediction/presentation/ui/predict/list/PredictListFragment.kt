package com.tobe.prediction.presentation.ui.predict.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.setUp
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.presenter.predict.list.PredictListPresenter
import kotlinx.android.synthetic.main.fr_predict_list.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class PredictListFragment : Fragment(), IPredictListView {
    companion object {
        fun instance(pick: (String) -> Unit): PredictListFragment {
            val fr = PredictListFragment()
            fr.pick = pick

            return fr
        }
    }


    @Inject
    lateinit var presenter: PredictListPresenter

    lateinit var scroll: (isDown: Boolean) -> Unit
    private lateinit var pick: (String) -> Unit
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

        rvPredicts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var prevDirDown = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val dirDown = dy > 0
                if (prevDirDown xor dirDown) {
                    scroll(dirDown)
                }
                prevDirDown = dirDown
            }
        })

        /*fabAdd.setOnClickListener {
            PredictEditDialog().show(fragmentManager, "one")
        }*/
    }

    override fun onStart() {
        super.onStart()
        presenter.loadPredictList()
    }

    override fun onDataLoaded(list: List<PredictDTO>) {
        predicts.clear() // todo move this inside the Adapter
        predicts.addAll(list)
        adapter.notifyDataSetChanged()

        if (predicts.isEmpty()) {
            rvPredicts.hide()
            txtEmptyList.show()
        } else {
            txtEmptyList.hide()
            rvPredicts.show()
        }
    }

    override fun showError(th: Throwable?) { // TODO use snackbars when they are fixed
        //rvPredicts.snackbar("Error loading data").colorError()
        ctx.longToast("Error loading data. ${th.toString()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun onItemPicked(predict: PredictDTO) {
        pick(predict.id)
        rvPredicts.snackbar("Oh you clicked", "Yes") { }
    }
}