package com.example.imchic.view.screens

import android.graphics.Color
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentHomeBinding
import com.example.imchic.util.AppUtil

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initView() {
        //globalToast(AppUtil.ToastType.ERROR, "Hello World!", 1000)
        customToast("Hello World!", R.drawable.ic_baseline_brightness_6_24, R.color.seed, 1000)
    }

}