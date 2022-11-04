package com.example.imchic.view.screens.devmode

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.imchic.R
import com.example.imchic.util.AppUtil

class DevModeUrlSettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.dev_mode_url, rootKey)

        // editTextPreference 를 눌렀을 때
        findPreference<androidx.preference.EditTextPreference>("developer_mode_server_seturl")?.setOnPreferenceChangeListener { preference, newValue ->
            AppUtil.logD("server_url => $newValue")
            AppUtil.setDevServerUrl(requireContext(), newValue.toString())
            true
        }


    }
}