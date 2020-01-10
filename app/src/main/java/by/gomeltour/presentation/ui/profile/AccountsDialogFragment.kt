package by.gomeltour.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import by.gomeltour.R
import by.gomeltour.databinding.AccountsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountsDialogFragment : DialogFragment() {

    private val viewModel: AccountsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogFramedTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AccountsBinding>(inflater, R.layout.fr_accounts_dialog, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.closeCommand.observe(this.viewLifecycleOwner, Observer {
            dismiss()
        })

        viewModel.start()
    }
}