package com.example.imchic.view.screens

import androidx.navigation.fragment.findNavController
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentFirstBinding

class FirstFragment: BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    override fun initView() {
        binding.run {
//            binding.buttonFirst.setOnClickListener {
//                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//            }
        }
    }
}
