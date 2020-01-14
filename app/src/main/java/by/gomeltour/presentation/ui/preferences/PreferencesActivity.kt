package by.gomeltour.presentation.ui.preferences

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import by.gomeltour.R

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.prefs)
        }

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            return preference?.let {
                if(preference.key == preference.context.getString(R.string.pref_key_init_data)) {
                    Toast.makeText(preference.context, "Oh Click!", Toast.LENGTH_LONG).show()
                    true
                } else {
                    super.onPreferenceTreeClick(preference)
                }
            } ?: super.onPreferenceTreeClick(preference)
        }
    }
}