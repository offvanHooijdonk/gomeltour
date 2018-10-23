package com.tobe.prediction.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tobe.prediction.R
import kotlinx.android.synthetic.main.fr_bottom_options_dialog.*

/**
 * Created by Yahor_Fralou on 10/10/2018 2:40 PM.
 */

class BottomOptionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val EVENT_SIGN_OUT = 0
    }

    private var pickEventListener: (Int) -> Unit = {}

    fun setMenuPickListener(listener: (option: Int) -> Unit) {
        this.pickEventListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_bottom_options_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignOutOption()
    }

    private fun setupSignOutOption() {
        itemConfirmSignOut.onConfirm { pickEventListener(EVENT_SIGN_OUT) }
    }
}