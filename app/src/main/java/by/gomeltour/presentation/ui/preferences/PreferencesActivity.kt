package by.gomeltour.presentation.ui.preferences

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import by.gomeltour.R
import by.gomeltour.repository.CheckInRepo
import by.gomeltour.service.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private val checkInRepo: CheckInRepo by inject()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.prefs)
        }

        override fun onPreferenceTreeClick(preference: Preference?): Boolean =
                when (preference?.key) {
                    getString(R.string.pref_key_clear_checks_in) -> {
                        checkInRepo.removeByUser(Session.user!!.id)
                                .onEach { context?.let { Toast.makeText(it, "Done", Toast.LENGTH_LONG).show() } }
                                .launchIn(CoroutineScope(Dispatchers.Main))
                        true
                    }
                    else -> super.onPreferenceTreeClick(preference)
                }

    }
}