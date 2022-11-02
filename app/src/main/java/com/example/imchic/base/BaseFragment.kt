package com.example.imchic.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.imchic.util.AppUtil

/**
 * @param T: ViewDataBinding
 * @property layoutRes Int
 * @property binding T
 * @constructor
 */

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@BaseFragment
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initView()

    fun globalToast(type: AppUtil.ToastType, msg:String, duration: Int) {
        when (type) {
            AppUtil.ToastType.SUCCESS -> AppUtil.successToast(requireContext(), msg, duration)
            AppUtil.ToastType.NORMAL -> AppUtil.normalToast(requireContext(), msg, duration)
            AppUtil.ToastType.ERROR -> AppUtil.errorToast(requireContext(), msg, duration)
            AppUtil.ToastType.WARNING -> AppUtil.warningToast(requireContext(), msg, duration)
            AppUtil.ToastType.INFO -> AppUtil.infoToast(requireContext(), msg, duration)
            else -> {}
        }
    }

    fun customToast(msg:String, icon: Int, color: Int, duration: Int){
        AppUtil.customToast(requireContext(), msg, icon, color, duration)
    }

}