package by.gomeltour.presentation.ui.predict.edit

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tobe.gomeltour.R
import com.tobe.gomeltour.databinding.PredictEditDataBinding
import by.gomeltour.helper.attachTo
import by.gomeltour.helper.hideKeyBoard
import by.gomeltour.helper.showKeyboard
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fr_predict_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by Yahor_Fralou on 9/27/2018 3:05 PM.
 */

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

    // todo handle Back button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = requireContext()

        viewModel.closeCommand.subscribe { closeDialog() }.attachTo(cd)
        viewModel.closeConfirmCommand.subscribe { showCloseConfirm() }.attachTo(cd)
        viewModel.setupDialog(null)

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

    private fun showCloseConfirm() {
        AlertDialog.Builder(ctx)
                .setTitle(R.string.confirm_cancel_title)
                .setMessage(R.string.confirm_cancel_message)
                .setCancelable(true)
                .setNegativeButton(R.string.confirm_btn_stay) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(android.R.string.yes) { _, _ -> closeDialog() }
                .show()
    }

    private fun closeDialog() {
        hideKeyboard()
        dismiss()
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