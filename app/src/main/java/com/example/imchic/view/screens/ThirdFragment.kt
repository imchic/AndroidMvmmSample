package com.example.imchic.view.screens

import com.example.imchic.R
import com.example.imchic.base.AppLog
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment<FragmentThirdBinding>(R.layout.fragment_third) {

    override fun initView() {
        AppLog.i("ThirdFragment initView")
    }
}