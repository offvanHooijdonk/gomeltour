package com.tobe.prediction.presentation.ui.predict.view

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.tobe.prediction.R
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.Vote
import com.tobe.prediction.domain.createVote
import com.tobe.prediction.helper.colorWarn
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.views.AnswersGroup
import kotlinx.android.synthetic.main.fr_predict_edit.*
import kotlinx.android.synthetic.main.item_predict.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.progressDialog
import java.util.*

/**
 * Created by Yahor_Fralou on 9/27/2018 3:05 PM.
 */

class PredictEditDialog : DialogFragment() {
    private var maxAnswerNum: Int = 0
    private var minAnswerNum: Int = 0
    private lateinit var answersGroup: AnswersGroup
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogFragmentTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_predict_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maxAnswerNum = resources.getInteger(R.integer.max_answers)
        minAnswerNum = resources.getInteger(R.integer.min_answers)
        answersGroup = AnswersGroup(maxAnswerNum, minAnswerNum, this::onAnswerRemove)

        for (i in 0 until minAnswerNum) addAnswerForm()
        btnAddAnswer.setOnClickListener { addAnswerForm() }
        imgSave.setOnClickListener { _ ->
            requireContext().progressDialog(
                    message = "Publishing..."
            )
            collectData(
                    success = { predict, vote ->
                        Handler().postDelayed({
                            progressDialog?.dismiss()
                            dismiss()
                        }, 1500)
                    },
                    invalid = {}
            )
        }
    }

    override fun onStart() {
        super.onStart()

        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window.setWindowAnimations(R.style.DialogFadeAnimation)
    }

    private fun addAnswerForm() {
        try {
            val v = answersGroup.addNewAnswer(requireContext())

            blockForm.addView(v)
            updateControls()
        } catch (e: AnswersGroup.AnswersLimitViolationException) {
            blockForm.snackbar("You're adding too much answers").colorWarn() // todo to resources
        }
    }

    private fun onAnswerRemove(view: View, position: Int) {
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
    }

    private fun collectData(success: (Predict, Vote) -> Unit, invalid: (String) -> Unit) {
        val options = answersGroup.getAnswers()
        val predict = Predict(
                title = inputTitle.text.toString(),
                text = inputText.text.toString(),
                dateWhen = Date(),
                isActive = true,
                options = options.toTypedArray()
        )

        val vote = createVote(Session.user!!.id, predict.id, answersGroup.selectedPosition)

        success(predict, vote)
    }

    private fun updateControls() {
        btnAddAnswer.isEnabled = answersGroup.size < maxAnswerNum
    }
}