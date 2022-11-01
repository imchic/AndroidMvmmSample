package com.example.imchic.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.imchic.R
import com.example.imchic.base.AppLog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialogFragment : DialogFragment() {

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return MaterialAlertDialogBuilder(requireContext())
//            .setView(R.layout.include_loading)
//            .show()
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.include_loading, null))
//                .setPositiveButton("확인") { _, _ ->
//                    AppLog.d("확인")
//                }
//                .setNegativeButton("취소") { _, _ ->
//                    dialog?.cancel()
//                    AppLog.d("취소")
//                }

            builder.create()
            builder.show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    companion object {
        val TAG = LoadingDialogFragment::class.java.simpleName
    }
}

