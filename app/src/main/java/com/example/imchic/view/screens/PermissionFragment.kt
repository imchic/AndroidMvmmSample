package com.example.imchic.view.screens

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentPermissionBinding
import com.example.imchic.view.MainActivity

class PermissionFragment: BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {
    override fun initView() {

        binding.permissionlayoutButton.setOnClickListener {
            val bundle = bundleOf("flag" to true)
            (activity as MainActivity).navController.navigate(R.id.HomeFragment, bundle)
        }

    }
}
