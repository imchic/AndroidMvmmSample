package com.example.imchic.view.screens.devmode

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.imchic.R
import com.example.imchic.util.AppUtil
import com.example.imchic.view.MainActivity

class DevModeSettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.dev_mode_settings, rootKey)

        // 서버를 눌렀을 때
        findPreference<Preference>("developer_mode_server_url")?.setOnPreferenceClickListener {
            AppUtil.logD("developer_mode_server_url")
            (context as MainActivity).navController.navigate(R.id.DevModeUrlSettingFragment)
            true
        }

        // 사용자 정보를 눌렀을 때
        findPreference<Preference>("developer_mode_user_info")?.setOnPreferenceClickListener {
            AppUtil.logD("developer_mode_user_info")
            (context as MainActivity).navController.navigate(R.id.DevModeUserInfoFragment)
            true
        }

        // 기기 정보를 눌렀을 때
        findPreference<Preference>("developer_mode_device_info")?.setOnPreferenceClickListener {
            (context as MainActivity).navController.navigate(R.id.DevModeDevicesInfoFragment)
            true
        }
    }

}