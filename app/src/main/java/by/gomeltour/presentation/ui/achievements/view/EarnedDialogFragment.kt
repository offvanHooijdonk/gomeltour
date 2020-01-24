package by.gomeltour.presentation.ui.achievements.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import by.gomeltour.R
import by.gomeltour.databinding.EarnedAchievementBinding
import kotlinx.android.synthetic.main.fr_earned_achievement_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EarnedDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_ACHIEVEMENT_ID = "ARG_ACHIEVEMENT_ID"

        fun getInstance(achievementId: String) =
                EarnedDialogFragment().apply { arguments = Bundle().apply { putString(ARG_ACHIEVEMENT_ID, achievementId) } }
    }

    private val viewModel: EarnedAchievementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.EarnedDialogTheme)
        //dialog?.window?.setWindowAnimations(R.style.EarnedDialogAnimation)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<EarnedAchievementBinding>(inflater, R.layout.fr_earned_achievement_dialog, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(ARG_ACHIEVEMENT_ID)?.let {
            viewModel.loadData(it)
        }

        btn_close.setOnClickListener { dismiss() }
    }
}