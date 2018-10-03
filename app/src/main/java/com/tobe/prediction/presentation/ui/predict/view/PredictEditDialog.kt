package com.tobe.prediction.presentation.ui.predict.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.helper.colorWarn
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.show
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.presenter.predict.view.PredictEditPresenter
import kotlinx.android.synthetic.main.fr_predict_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import java.util.*
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/27/2018 3:05 PM.
 */
const val NONE_SELECTED = -1

class PredictEditDialog : DialogFragment(), IPredictEditView {
    /*private var maxAnswerNum: Int = 0
    private var minAnswerNum: Int = 0*/
    /*private lateinit var answersGroup: AnswersGroup*/
    private var optPos: Int = 0
    private var optNeg: Int = 0

    @Inject
    lateinit var presenter: PredictEditPresenter

    private var progressDialog: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogFragmentTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_predict_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dependency().predictComponent().inject(this)
        presenter.attachView(this)

// FIXME Implement RadioButtons switching!

        optPos = resources.getInteger(R.integer.option_positive)
        optNeg = resources.getInteger(R.integer.option_negative)

        /*maxAnswerNum = resources.getInteger(R.integer.max_answers)
        minAnswerNum = resources.getInteger(R.integer.min_answers)*/
        /*answersGroup = AnswersGroup(maxAnswerNum, minAnswerNum, this::onAnswerRemove)*/

        /*for (i in 0 until minAnswerNum) addAnswerForm()*/

        /*btnAddAnswer.setOnClickListener { addAnswerForm() }*/
        imgSave.setOnClickListener { _ ->
            collectData(
                    success = { predict, optionSelected ->
                        presenter.savePredict(predict, optionSelected)
                    },
                    invalid = { errorMessage ->
                        blockForm.longSnackbar(errorMessage).colorWarn()
                    }
            )
        }

        imgDialogCancel.setOnClickListener { dismiss() } // todo add confirmation dialog
    }

    override fun onStart() {
        super.onStart()

        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window?.setWindowAnimations(R.style.DialogFadeAnimation)
    }

    override fun onSavedComplete() {
        showSavingProgress(false)
        dismiss()
    }

    override fun showSavingError() {
        showSavingProgress(false)
        blockForm.snackbar("Error saving your prediction!", "Retry") {
            // todo implement retry
        }.colorError()
    }

    override fun showSavingProgress(isShow: Boolean) {
        if (isShow) {
            progressDialog = requireContext().alert { isCancelable = false }.show()
            imgSave.hide()
            pbSave.show()
        } else {
            progressDialog?.dismiss()
            pbSave.hide()
            imgSave.show()
        }
    }

    private fun collectData(success: (Predict, optionSelected: Int) -> Unit, invalid: (String) -> Unit) {
        val options = listOf(inputPositive.text.toString(), inputNegative.text.toString())
        val optionSelected = if (inputPositive.isSelected) optPos else if (inputNegative.isSelected) optNeg else NONE_SELECTED
        if (optionSelected == NONE_SELECTED) {
            invalid("Please select one of options as your answer")
            return
        }

        val predict = Predict(
                title = inputTitle.text.toString(),
                text = inputText.text.toString(),
                dateOpenTill = Date(),
                dateFulfillment = Date(),
                userId = Session.user!!.id,
                isActive = true,
                options = options
        )

        success(predict, optionSelected)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    /*private fun addAnswerForm() {
        try {
            val v = answersGroup.addNewAnswer(requireContext())

            blockForm.addView(v)
            updateControls()
        } catch (e: AnswersGroup.AnswersLimitViolationException) {
            blockForm.snackbar("You're adding too much answers").colorWarn() // todo to resources
        }
    }*/

    /*private fun updateControls() {
        btnAddAnswer.isEnabled = answersGroup.size < maxAnswerNum
    }*/

    /*private fun onAnswerRemove(view: View, position: Int) {
        if (answersGroup.selectedPosition == position) {
            blockForm.snackbar(R.string.form_remove_selected_answer_error).colorWarn()
        } else {
            try {
                answersGroup.removeAnswer(position)

                blockForm.removeView(view)
                updateControls()
            } catch (e: AnswersGroup.AnswersLimitViolationException) {
                blockForm.snackbar("Should be at least $minAnswerNum questions").colorWarn() //todo to resources
            }
        }
    }*/
}