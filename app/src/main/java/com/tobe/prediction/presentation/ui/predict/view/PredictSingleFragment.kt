package com.tobe.prediction.presentation.ui.predict.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tobe.prediction.R
import com.tobe.prediction.databinding.PredictSingleDataBinding
import com.tobe.prediction.helper.setUpDefault
import kotlinx.android.synthetic.main.fr_predict_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PredictSingleFragment : Fragment() {
    companion object {

        const val EXTRA_PREDICT_ID = "predict_id"

        fun instance(): PredictSingleFragment {
            return PredictSingleFragment()
        }

    }

    //lateinit var presenter: PredictSinglePresenter
    private val viewModel: PredictSingleViewModel by viewModel()
    private lateinit var binding: PredictSingleDataBinding

    private var predictId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        predictId = arguments?.getString(EXTRA_PREDICT_ID)

        binding = DataBindingUtil.inflate(inflater, R.layout.fr_predict_view, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rlPredict.setUpDefault()

        predictId?.let {
            viewModel.setPredictId(it)
        } // todo else show error
    }

}
