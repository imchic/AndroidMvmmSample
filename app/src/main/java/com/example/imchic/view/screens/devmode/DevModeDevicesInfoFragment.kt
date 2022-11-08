package com.example.imchic.view.screens.devmode

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.imchic.R
import com.example.imchic.util.AppUtil

class DevModeDevicesInfoFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.dev_mode_devices_info, rootKey)

        // setValue
        findPreference<Preference>("developer_mode_device_version_name")?.summary = AppUtil.getAndroidVersion()
        findPreference<Preference>("developer_mode_device_model_name")?.summary = AppUtil.getModelName()
        findPreference<Preference>("developer_mode_device_manufacturer")?.summary = AppUtil.getProductName()
        findPreference<Preference>("developer_mode_app_version_name")?.summary = AppUtil.getAppVersion(requireContext())
        findPreference<Preference>("developer_mode_screen_size")?.summary = AppUtil.getAppDisplaySize(requireContext())
        findPreference<Preference>("developer_mode_screen_destiny")?.summary = AppUtil.getAppMetrics(requireContext())[0]
        findPreference<Preference>("developer_mode_screen_width")?.summary = "${AppUtil.getAppMetrics(requireContext())[3]}dp"
    }
}