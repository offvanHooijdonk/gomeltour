package com.tobe.prediction.presentation.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tobe.prediction.R
import com.tobe.prediction.app.App.Companion.LOGCAT
import com.tobe.prediction.presentation.navigation.RouterHelper
import kotlinx.android.synthetic.main.fr_bottom_options_dialog.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 10/10/2018 2:40 PM.
 */

class BottomOptionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val EVENT_SIGN_OUT = 0
    }

    private val viewModel: BottomOptionsViewModel by viewModel()
    //private var pickEventListener: (Int) -> Unit = {}

    /*fun setMenuPickListener(listener: (option: Int) -> Unit) {
        this.pickEventListener = listener
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_bottom_options_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignOutOption()
    }

    private fun setupSignOutOption() {
        itemConfirmSignOut.onConfirm {
            viewModel.logOut()
            dismiss()
            //pickEventListener(EVENT_SIGN_OUT)
        }
    }
}