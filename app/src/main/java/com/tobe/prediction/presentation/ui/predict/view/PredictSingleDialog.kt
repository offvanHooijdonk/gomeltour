package com.tobe.prediction.presentation.ui.predict.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.*
import com.tobe.prediction.model.loadAvatar
import com.tobe.prediction.presentation.presenter.predict.view.PredictSinglePresenter
import kotlinx.android.synthetic.main.fr_predict_view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

/**
 * Created by Yahor_Fralou on 10/10/2018 12:30 PM.
 */
class PredictSingleDialog : DialogFragment(), IPredictSingleView {

    companion object {
        const val EXTRA_PREDICT_ID = "predict_id"
    }

    lateinit var presenter: PredictSinglePresenter

    private lateinit var ctx: Context

    private var predictId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogFragmentTheme)
        predictId = arguments?.getString(PredictSingleFragment.EXTRA_PREDICT_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_predict_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        ctx = requireContext()

        imgBack.setOnClickListener { dialog?.dismiss() }
        rlPredict.setUpDefault()

        root.hide()
        rlPredict.isRefreshing = true
        presenter.onViewStarted(predictId)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setWindowAnimations(R.style.DialogFadeAnimation)
    }

    override fun displayMainInfo(dto: PredictDTO) {
        txtPredictTitle.text = dto.title
        txtPredictText.text = dto.text
        txtAuthorName.text = dto.authorName
        txtVotesPos.text = dto.votePosCount.toString()
        txtVotesNeg.text = dto.voteNegCount.toString()

        imgAuthorPic.loadAvatar(dto.authorPic)

        rlPredict.isRefreshing = false
        rlPredict.isEnabled = false
        root.show()
    }

    override fun showDataError(message: String) {
        ctx.toast(message)
        txtPredictTitle.snackbar("Error loading data").colorError()
    }

    override fun showNoIdError() {
        txtPredictTitle.snackbar("Selected item ID was not provided").colorWarn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}