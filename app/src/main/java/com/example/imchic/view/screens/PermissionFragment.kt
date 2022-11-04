package com.example.imchic.view.screens

import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentPermissionBinding
import com.example.imchic.view.MainActivity

class PermissionFragment: BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    override fun initView() {
        binding.permissionlayoutButton.setOnClickListener {
            permissionSharedPreferences.edit().putBoolean("permission", true).apply()
            (activity as MainActivity).navController.navigate(R.id.HomeFragment)
        }

    }
}
