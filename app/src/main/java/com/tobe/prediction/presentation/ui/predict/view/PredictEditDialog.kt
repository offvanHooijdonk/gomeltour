package com.tobe.prediction.presentation.ui.predict.view

import android.app.DatePickerDialog
import android.content.Context
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
    private var optPos: Int = 0
    private var optNeg: Int = 0
    private var dateFulfill: Date? = null
    private var dateOpenTill: Date? = null
    private lateinit var ctx: Context

    private var dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL) // todo move to fun

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
        ctx = requireContext()
        /*dateFormat = DateFormat.getLongDateFormat(ctx)*/

        optPos = resources.getInteger(R.integer.option_positive)
        optNeg = resources.getInteger(R.integer.option_negative)

        imgSave.setOnClickListener { processFormAndSave() }
        imgDialogCancel.setOnClickListener { dismiss() } // todo add confirmation dialog

        radioPositive.setOnCheckedChangeListener { _, isChecked -> if (isChecked) setRadioState(true) }
        radioNegative.setOnCheckedChangeListener { _, isChecked -> if (isChecked) setRadioState(false) }

        imgPickFulfillDate.setOnClickListener {
            startDatePicker { datePicked ->
                dateFulfill = datePicked;
                txtDateFulfill.text = dateFormat.format(dateFulfill)
                imgPickFulfillDate.hide()
                txtDateFulfill.show()
            }
        }
        imgPickOpenTillDate.setOnClickListener {
            startDatePicker { datePicked ->
                dateOpenTill = datePicked
                txtDateOpenTill.text = dateFormat.format(dateOpenTill)
                imgPickOpenTillDate.hide()
                txtDateOpenTill.show()
            }
        }
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
            processFormAndSave()
        }.colorError()
    }

    override fun showSavingProgress(isShow: Boolean) {
        if (isShow) {
            progressDialog = ctx.alert { isCancelable = false }.show()
            imgSave.hide()
            pbSave.show()
        } else {
            progressDialog?.dismiss()
            pbSave.hide()
            imgSave.show()
        }
    }

    private fun setRadioState(isPositive: Boolean) {
        if (isPositive) {
            radioNegative.isChecked = false
        } else {
            radioPositive.isChecked = false
        }
    }

    private fun processFormAndSave() {
        collectData(
                success = { predict, optionSelected ->
                    presenter.savePredict(predict, optionSelected)
                },
                invalid = { errorMessage ->
                    blockForm.longSnackbar(errorMessage).colorWarn()
                }
        )
    }

    private fun collectData(success: (Predict, optionSelected: Int) -> Unit, invalid: (String) -> Unit) {
        val options = listOf(inputPositive.text.toString(), inputNegative.text.toString())
        val optionSelected = if (radioPositive.isChecked) optPos else if (radioNegative.isChecked) optNeg else NONE_SELECTED

        if (inputTitle.text.isEmpty() || inputText.text.isEmpty() || inputPositive.text.isEmpty() || inputNegative.text.isEmpty()) {
            invalid("Please fill all the inputs")
            return
        }
        if (optionSelected == NONE_SELECTED) {
            invalid("Please select one of options as your answer")
            return
        }
        if (dateFulfill == null) {
            invalid("Please pick fulfill date")
            return
        }

        val predict = Predict(
                title = inputTitle.text.toString(),
                text = inputText.text.toString(),
                dateOpenTill = dateOpenTill ?: dateFulfill!!,
                dateFulfillment = dateFulfill!!,
                userId = Session.user!!.id,
                isActive = true,
                options = options
        )

        success(predict, optionSelected)
    }

    private fun startDatePicker(listener: (datePicked: Date) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
                ctx,
                { _, year, month, dayOfMonth ->
                    val datePicked = Calendar.getInstance()
                            .apply { timeInMillis = 0; set(Calendar.YEAR, year); set(Calendar.MONTH, month); set(Calendar.DAY_OF_MONTH, dayOfMonth); }
                            .time
                    listener(datePicked)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).apply { datePicker.minDate = System.currentTimeMillis() }.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}