package com.example.imchic.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.example.imchic.BuildConfig
import com.example.imchic.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.dmoral.toasty.Toasty

/**
 * 글로벌 앱 유틸
 */

class AppUtil {

    enum class ToastType(val value: Int) { NORMAL(1), SUCCESS(2), ERROR(3), WARNING(4), INFO(5) }

    init {
        Toasty.Config.getInstance().tintIcon(true).setTextSize(14).allowQueue(true).apply()
    }

    companion object {

        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        private val classNm = Thread.currentThread().stackTrace[4].className.replace(".java", "::")

        fun appExit() = android.os.Process.killProcess(android.os.Process.myPid())

        // 로그
        fun logV(msg: String) { if (BuildConfig.DEBUG) Log.v(classNm, "상세 \uD83D\uDC49\uD83D\uDC49" + buildLogMsg(msg)) }
        fun logD(msg: String) { if (BuildConfig.DEBUG) Log.d(classNm, "디버깅 \uD83D\uDC49\uD83D\uDC49" + buildLogMsg(msg)) }
        fun logI(msg: String) { if (BuildConfig.DEBUG) Log.i(classNm, "인포 \uD83D\uDC49\uD83D\uDC49" + buildLogMsg(msg)) }
        fun logW(msg: String) { if (BuildConfig.DEBUG) Log.w(classNm, "경고 \uD83D\uDC49\uD83D\uDC49" + buildLogMsg(msg)) }
        fun logE(msg: String) { if (BuildConfig.DEBUG) Log.e(classNm, "오류 \uD83D\uDC49\uD83D\uDC49" + buildLogMsg(msg)) }

        private fun buildLogMsg(logMsg: String): String {
            val ste = Thread.currentThread().stackTrace[4]
            val sb = StringBuilder()
            sb.append("  [")
            sb.append(ste.fileName.replace(".java", "::"))
            sb.append("]")
            sb.append("[")
            sb.append(ste.lineNumber)
            sb.append("]")
            sb.append("[")
            sb.append(ste.methodName)
            sb.append("]")
            sb.append(" → $logMsg")
            return sb.toString()
        }

        fun successToast(context: Context, msg: String, duration: Int) {
            context.let {
                Toasty.success(it, msg, duration, true).show()
            }
        }

        fun errorToast(context: Context, msg: String, duration: Int) {
            context.let {
                Toasty.error(it, msg, duration, true).show()
            }
        }

        fun warningToast(context: Context, msg: String, duration: Int) {
            context.let {
                Toasty.warning(it, msg, Toast.LENGTH_SHORT, true).show()
            }
        }

        fun infoToast(context: Context, msg: String, duration: Int) {
            context.let {
                Toasty.info(it, msg, duration, true).show()
            }
        }

        fun normalToast(context: Context, msg: String, duration: Int) {
            context.let {
                Toasty.normal(it, msg, duration).show()
            }
        }

        fun customToast(context: Context, msg: String, iconRes: Int, tintColorRes: Int, duration: Int) {
            context.let {
                Toasty.custom(it, msg, iconRes, tintColorRes, duration, true, true).show()
            }
        }

    }

}