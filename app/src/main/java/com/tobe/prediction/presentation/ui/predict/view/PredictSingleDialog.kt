package com.tobe.prediction.presentation.ui.predict.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.databinding.PredictSingleDataBinding
import com.tobe.prediction.helper.setUpDefault
import kotlinx.android.synthetic.main.fr_predict_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 10/10/2018 12:30 PM.
 */
class PredictSingleDialog : DialogFragment() {

    private val viewModel: PredictSingleViewModel by viewModel()

    private lateinit var binding: PredictSingleDataBinding

    private var predictId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.DialogFragmentTheme)
        predictId = arguments?.getString(PredictSingleFragment.EXTRA_PREDICT_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fr_predict_view, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener { dialog?.dismiss() }
        binding.rlPredict.setUpDefault()

        predictId?.let {
            viewModel.setPredictId(it)
        } // todo else show error
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setWindowAnimations(R.style.DialogFadeAnimation)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}