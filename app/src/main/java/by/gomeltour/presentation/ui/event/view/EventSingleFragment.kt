package by.gomeltour.presentation.ui.event.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import by.gomeltour.R
import by.gomeltour.databinding.PredictSingleDataBinding
import by.gomeltour.helper.setUpDefault
import kotlinx.android.synthetic.main.fr_event_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventSingleFragment : Fragment() {
    companion object {
        const val EXTRA_EVENT_ID = "event_id"

        fun instance(predictId: String): EventSingleFragment {
            return EventSingleFragment().apply { arguments = Bundle().apply { putString(EXTRA_EVENT_ID, predictId) } }
        }
    }

    private val viewModel: EventSingleViewModel by viewModel()
    private lateinit var binding: PredictSingleDataBinding

    private var predictId: String? = null
    private var isAuthor = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        predictId = arguments?.getString(EXTRA_EVENT_ID)

        binding = DataBindingUtil.inflate(inflater, R.layout.fr_event_view, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh_predict_info.setUpDefault()

        viewModel.isAuthor.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                isAuthor = (sender as? ObservableBoolean)?.get() ?: false
            }
        })
        viewModel.setPredictId(predictId)
    }
}
