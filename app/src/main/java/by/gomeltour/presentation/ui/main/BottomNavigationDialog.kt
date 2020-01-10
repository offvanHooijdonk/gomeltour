package by.gomeltour.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.gomeltour.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import by.gomeltour.model.Session
import by.gomeltour.model.loadAvatar
import kotlinx.android.synthetic.main.fr_bottom_navigation.*
import org.jetbrains.anko.toast

/**
 * Created by Yahor_Fralou on 10/22/2018 5:08 PM.
 */

@Deprecated("Replaced with NavBottomBar")
class BottomNavigationDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_bottom_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtUserName.text = Session.user!!.name
        txtUserEmail.text = Session.user!!.email
        imgAvatar.loadAvatar(Session.user!!.photoUrl)

        navigationView.setNavigationItemSelectedListener {
            requireContext().toast("Navigating!")
            dismiss()
            true
        }
        navigationView.setCheckedItem(R.id.it_list)
    }
}