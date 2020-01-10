package by.gomeltour.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tobe.gomeltour.R
import com.tobe.gomeltour.databinding.ProfileViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    companion object {
        private const val EXTRA_USER_ID = "extra_user_id"

        fun instance(userId: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply { putString(EXTRA_USER_ID, userId) }
                }
    }

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ProfileViewBinding>(inflater, R.layout.fr_profile, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getString(EXTRA_USER_ID)
        if (userId != null && userId.isNotEmpty()) {
            viewModel.setupUser(userId)
        } else {
            // todo handle
        }
    }
}