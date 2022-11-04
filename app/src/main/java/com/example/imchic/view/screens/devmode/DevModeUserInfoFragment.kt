package com.example.imchic.view.screens.devmode

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.imchic.R
import com.example.imchic.util.AppUtil

class DevModeUserInfoFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.dev_mode_user_info, rootKey)

        // setValue
        findPreference<Preference>("developer_mode_user_hash_id")?.summary = AppUtil.getHashId(requireContext())
        findPreference<Preference>("developer_mode_user_uuid")?.summary = AppUtil.getUUID(requireContext())
    }
}