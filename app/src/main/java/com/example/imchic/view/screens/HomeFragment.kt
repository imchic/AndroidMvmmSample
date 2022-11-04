package com.example.imchic.view.screens

import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentHomeBinding
import com.example.imchic.view.MainActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initView() {
        initPermissionAuthChk()
    }

    private fun initPermissionAuthChk() {
        permissionSharedPreferences.getBoolean("permission", false).let {
            if (!it) {
                (activity as MainActivity).navController.navigate(R.id.PermissionFragment)
            } else {
                (activity as MainActivity).showToolbar(true)
            }
        }
    }
}