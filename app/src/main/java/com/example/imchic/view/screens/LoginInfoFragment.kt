package com.example.imchic.view.screens

import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentLogininfoBinding
import com.example.imchic.view.MainActivity

class LoginInfoFragment : BaseFragment<FragmentLogininfoBinding>(R.layout.fragment_logininfo) {

       override fun initView() {
           (context as MainActivity).supportActionBar?.title = "사용자 정보"
        }

}