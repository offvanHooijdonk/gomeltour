package com.tobe.prediction.presentation.ui.predict.edit

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.databinding.PredictEditDataBinding
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.helper.hideKeyBoard
import com.tobe.prediction.helper.showKeyboard
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fr_predict_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by Yahor_Fralou on 9/27/2018 3:05 PM.
 */
const val NONE_SELECTED = -1

class PredictEditDialog : DialogFragment() {

    private lateinit var ctx: Context

    private val viewModel: PredictEditViewModel by viewModel()

    private val cd = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.DialogFragmentTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<PredictEditDataBinding>(inflater, R.layout.fr_predict_edit, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = requireContext()

        viewModel.closeCommand.subscribe { hideKeyboard(); this.dismiss() }.attachTo(cd)

        blockPickFulfillDate.setOnClickListener {
            startDatePicker(null) { datePicked -> viewModel.dateFulfillField.set(datePicked) }
            hideKeyboard()
        }
        blockPickOpenTillDate.setOnClickListener {
            startDatePicker(viewModel.dateFulfillField.get()) { datePicked -> viewModel.dateOpenTillField.set(datePicked) }
            hideKeyboard()
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        dialog?.window?.setWindowAnimations(R.style.DialogFadeAnimation)
        dialog?.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        inputTitle.showKeyboard()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        cd.clear()
    }

    private fun startDatePicker(maxDate: Date?, listener: (datePicked: Date) -> Unit) {
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
        ).apply { datePicker.minDate = System.currentTimeMillis() }
                .apply { if (maxDate != null) datePicker.maxDate = maxDate.time }
                .show()
    }

    private fun hideKeyboard() {
        view?.rootView?.hideKeyBoard()
    }
}