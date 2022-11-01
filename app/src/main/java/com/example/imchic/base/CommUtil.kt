package com.example.imchic.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.ViewGroup
import android.view.WindowManager

object CommUtil {

    /**
     * 팝업 디스플레이 width, hegiht 지정
     * @param dialog - 프래그먼트 다이얼로그 및 알럿 다이얼로그
     * @param activity
     * @param width
     * @param height
     */
    fun getDisplayDistance(dialog: Dialog?, activity: Activity?, width: Float, height: Float) {
        val outMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display: Display? = activity?.display
            display?.getRealMetrics(outMetrics)

        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getRealMetrics(outMetrics)
        }

        val deviceHeight: Int = outMetrics.heightPixels
        val deviceWidth: Int = outMetrics.widthPixels

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = (deviceWidth * width).toInt()
        params?.height = (deviceHeight * height).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}