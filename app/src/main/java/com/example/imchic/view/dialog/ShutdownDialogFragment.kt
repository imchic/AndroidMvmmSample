package com.example.imchic.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.imchic.R
import com.example.imchic.util.AppUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ShutdownDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater

            builder.run {
                setView(inflater.inflate(R.layout.fragment_shutdown, null))
                setPositiveButton("확인") { dialog, which ->
                    // 앱 종료
                    //activity?.finishAffinity()
                    AppUtil.appExit()
                }
                setNeutralButton("취소") { dialog, which ->
                    AppUtil.logD("Cancel")
                    dialog.dismiss()
                }
                create()
                show()
            }

        } ?: throw IllegalStateException("Activity cannot be null")
    }


    companion object {
        val TAG = ShutdownDialogFragment::class.java.simpleName
    }
}

