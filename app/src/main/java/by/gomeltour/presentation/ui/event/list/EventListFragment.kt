package by.gomeltour.presentation.ui.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import by.gomeltour.R
import by.gomeltour.databinding.PredictListBinding
import by.gomeltour.helper.setUpDefault
import by.gomeltour.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.fr_event_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class EventListFragment : Fragment() {

    private val viewModel: EventListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<PredictListBinding>(inflater, R.layout.fr_event_list, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setTitle(getString(R.string.events_list_title))

        rv_events.adapter = EventAdapter()
        refresh_predict_info.setUpDefault()
        refresh_predict_info.setOnRefreshListener { viewModel.updatePredicts() }
    }
}