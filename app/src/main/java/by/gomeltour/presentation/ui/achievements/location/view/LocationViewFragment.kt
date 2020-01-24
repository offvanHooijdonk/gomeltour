package by.gomeltour.presentation.ui.achievements.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.gomeltour.R
import by.gomeltour.databinding.LocationViewBinding
import by.gomeltour.presentation.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fr_location.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationViewFragment : Fragment() {
    companion object {
        private const val ARG_LOCATION_ID = "arg_location_id"

        fun getInstance(locationId: String) =
                LocationViewFragment().apply { arguments = Bundle().apply { putString(ARG_LOCATION_ID, locationId) } }
    }

    private val viewModel: LocationViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<LocationViewBinding>(inflater, R.layout.fr_location, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_checkins.adapter = CheckInAdapter()

        viewModel.onViewStart(arguments?.getString(ARG_LOCATION_ID))
    }
}