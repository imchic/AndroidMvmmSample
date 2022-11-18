package com.example.imchic.view.screens

import android.widget.ImageView
import android.widget.TextView
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentHomeBinding
import com.example.imchic.view.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {
        initPermissionAuthChk()
        setDashBoardCardView()
    }

    /**
     * 카드뷰 세팅
     */
    private fun setDashBoardCardView() {

        val arr = arrayOf(binding.cardView1, binding.cardView2, binding.cardView3, binding.cardView4)
        for (i in arr.indices) {
            val iv = arr[i].findViewById<ImageView>(R.id.cardViewImage)
            val tvTitle = arr[i].findViewById<TextView>(R.id.tvCardViewTitle)
            val tvSubTitle = arr[i].findViewById<TextView>(R.id.tvCardViewSubTitle)

            when (i) {
                0 -> {
                    iv.setImageResource(R.drawable.card_bg_1)
                    tvTitle.text = "제목 1"
                    tvSubTitle.text = "내용 1"
                }

                1 -> {
                    iv.setImageResource(R.drawable.card_bg_2)
                    tvTitle.text = "제목 2"
                    tvSubTitle.text = "내용 2"
                }

                2 -> {
                    iv.setImageResource(R.drawable.card_bg_3)
                    tvTitle.text = "제목 3"
                    tvSubTitle.text = "내용 3"
                }

                3 -> {
                    iv.setImageResource(R.drawable.card_bg_1)
                    tvTitle.text = "제목 4"
                    tvSubTitle.text = "내용 4"
                }
            }
            arr[i].setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(" ")
                    .setIcon(R.drawable.ic_baseline_support_agent_24)
                    .setMessage("이 기능은 준비중입니다.")
                    //.setView(R.layout.fragment_loading)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
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