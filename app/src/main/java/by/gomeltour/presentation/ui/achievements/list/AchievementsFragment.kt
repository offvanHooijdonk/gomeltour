package by.gomeltour.presentation.ui.achievements.list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.gomeltour.R
import by.gomeltour.databinding.AchievementsBinding
import by.gomeltour.presentation.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fr_achievements.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementsFragment : Fragment() {
    companion object {
        private const val PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_REQUEST_LOCATION = 1001
    }

    private val viewModel: AchievementsViewModel by viewModel()
    private var forceRequestPermission = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AchievementsBinding>(inflater, R.layout.fr_achievements, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.permissionRequestLiveData.observe(viewLifecycleOwner, Observer<Boolean> { value ->
            if (value == true) {
                if (checkPermission()) {
                    viewModel.onPermissionResult(true)
                } else {
                    requestPermission(false)
                }
            }
        }
        )

        (activity as? MainActivity)?.setTitle(getString(R.string.achievements_title))

        rv_locations.adapter = LocationsAdapter()
        rv_achievements.adapter = AchievementsAdapter()

        viewModel.achievements.observe(viewLifecycleOwner, Observer {
            (rv_achievements.adapter as AchievementsAdapter).submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.onViewActive()
    }

    override fun onStop() {
        super.onStop()

        viewModel.onViewInactive()
    }

    private fun checkPermission() =
            context?.let { ContextCompat.checkSelfPermission(it, PERMISSION) == PackageManager.PERMISSION_GRANTED } ?: false

    private fun requestPermission(force: Boolean) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), PERMISSION) && !force) {
            (requireActivity() as MainActivity)
                    .createSnackbar("Please grant location permission", Snackbar.LENGTH_LONG)
                    .setAction("Grant") { requestPermission(forceRequestPermission) }
                    .show()
            forceRequestPermission = true
            viewModel.onPermissionResult(false)
        } else {
            requestPermissions(arrayOf(PERMISSION), PERMISSION_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val result = when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
            }
            else -> false
        }

        viewModel.onPermissionResult(result)
    }
}