package com.example.imchic.view.screens

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.imchic.R
import com.example.imchic.util.AppUtil
import com.example.imchic.view.MainActivity

class SettingsFragment: PreferenceFragmentCompat() {

    private val themePreference by lazy { findPreference<Preference>("settings_mode_theme") }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 최초값 세팅
        initTheme((context as MainActivity).pref.getString("theme", "").toString())

        // 테마 선택
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                "밝은 테마" -> {/* 앱 테마를 밝은 테마로 변경 */ (context as MainActivity).viewModel.setTheme("light") }
                "어두운 테마" -> {/* 앱 테마를 어두운 테마로 변경 */ (context as MainActivity).viewModel.setTheme("dark") }
                else -> {/* 앱 테마를 시스템 테마로 변경 */ (context as MainActivity).viewModel.setTheme("system") }
            }

            initTheme((context as MainActivity).viewModel.theme.value)
            AppUtil.applyTheme(context as MainActivity, (context as MainActivity).viewModel.theme.value)

            true
        }
    }

    private fun initTheme(themeStr: String) {
        when (themeStr) {
            "light" -> themePreference?.summary = "밝은 테마"
            "dark" -> themePreference?.summary = "어두운 테마"
            else -> themePreference?.summary = "시스템 테마"
        }
    }
}