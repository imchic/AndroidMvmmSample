package com.example.imchic.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.imchic.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.fragment_loading, null))
            builder.create()
            builder.show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    companion object {
        val TAG = LoadingDialogFragment::class.java.simpleName
    }
}

