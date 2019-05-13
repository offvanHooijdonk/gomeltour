package com.tobe.prediction.presentation.ui.predict.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobe.prediction.R
import com.tobe.prediction.databinding.PredictListBinding
import com.tobe.prediction.helper.setUpDefault
import kotlinx.android.synthetic.main.fr_predict_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class PredictListFragment : Fragment() {

    private val viewModel: PredictListViewModel by viewModel()

    private lateinit var adapter: PredictAdapter
    private lateinit var ctx: Context
    private var prevScrollDirDown = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = requireContext()
        val binding = DataBindingUtil.inflate<PredictListBinding>(inflater, R.layout.fr_predict_list, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPredicts.layoutManager = LinearLayoutManager(ctx)
        adapter = PredictAdapter(ctx)
        rvPredicts.adapter = adapter
        refreshPredicts.setUpDefault()
        refreshPredicts.setOnRefreshListener { viewModel.updatePredicts() }

        rvPredicts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val dirDown = dy > 0
                if (prevScrollDirDown xor dirDown) {
                    viewModel.onListSroll(dirDown)
                }
                prevScrollDirDown = dirDown
            }
        })

        viewModel.viewStart()
    }
}