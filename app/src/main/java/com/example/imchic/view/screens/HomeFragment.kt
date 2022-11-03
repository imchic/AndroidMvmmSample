package com.example.imchic.view.screens

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentHomeBinding
import com.example.imchic.util.AppUtil
import com.example.imchic.view.MainActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initView() {

        // 번들 데이터 받기
        (activity as MainActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.PermissionFragment)
        AppUtil.logD("flag: ${arguments?.getBoolean("flag")}")

        if(arguments?.getBoolean("flag") == null || arguments?.getBoolean("flag") == false){
            (activity as MainActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.PermissionFragment)
        } else {
            globalToast(AppUtil.ToastType.SUCCESS, "Permission Granted!", 1000)
            (activity as MainActivity).navController.navigate(R.id.HomeFragment)
        }

    }
}