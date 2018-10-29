package com.tobe.prediction.presentation.ui.predict.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.*
import com.tobe.prediction.model.loadAvatar
import com.tobe.prediction.presentation.presenter.predict.view.PredictSinglePresenter
import kotlinx.android.synthetic.main.fr_predict_view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import javax.inject.Inject

class PredictSingleFragment : Fragment(), IPredictSingleView {
    companion object {

        const val EXTRA_PREDICT_ID = "predict_id"

        fun instance(): PredictSingleFragment {
            return PredictSingleFragment()
        }

    }

    @Inject
    lateinit var presenter: PredictSinglePresenter

    private lateinit var ctx: Context

    private var predictId: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        predictId = arguments?.getString(EXTRA_PREDICT_ID)

        return inflater.inflate(R.layout.fr_predict_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dependency().predictComponent().inject(this)
        presenter.attachView(this)
        ctx = requireContext()

        rlPredict.setUp()

        root.hide()
        rlPredict.isRefreshing = true
        presenter.onViewStarted(predictId)
    }

    override fun displayMainInfo(dto: PredictDTO) {
        txtPredictTitle.text = dto.title
        txtPredictText.text = dto.text
        txtAuthorName.text = dto.authorName
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
