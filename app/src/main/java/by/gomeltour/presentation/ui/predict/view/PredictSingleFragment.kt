package by.gomeltour.presentation.ui.predict.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import com.tobe.gomeltour.R
import com.tobe.gomeltour.databinding.PredictSingleDataBinding
import by.gomeltour.helper.setUpDefault
import by.gomeltour.presentation.ui.main.EFABTransformer
import by.gomeltour.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.fr_predict_view.*
import org.jetbrains.anko.design.longSnackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PredictSingleFragment : Fragment(), MainActivity.FABClickListener {
    companion object {

        const val EXTRA_PREDICT_ID = "predict_id"

        fun instance(predictId: String): PredictSingleFragment {
            return PredictSingleFragment().apply { arguments = Bundle().apply { putString(EXTRA_PREDICT_ID, predictId) } }
        }
    }

    //lateinit var presenter: PredictSinglePresenter
    private val viewModel: PredictSingleViewModel by viewModel()
    private val fabTransformer: EFABTransformer by inject()
    private lateinit var binding: PredictSingleDataBinding

    private var predictId: String? = null
    private var isAuthor = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        predictId = arguments?.getString(EXTRA_PREDICT_ID)

        binding = DataBindingUtil.inflate(inflater, R.layout.fr_predict_view, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh_predict_info.setUpDefault()

        viewModel.isAuthor.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                isAuthor = (sender as? ObservableBoolean)?.get() ?: false
                updateFAB()
            }
        })
        viewModel.setPredictId(predictId)
    }

    override fun onFabClicked() {
        refresh_predict_info.longSnackbar("Not implemented")
    }

    private fun updateFAB() {
        if (isAuthor) fabTransformer.transformTo(R.drawable.ic_edit_24, false) else fabTransformer.hide()
    }
}
