package com.example.imchic.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.imchic.BuildConfig
import com.example.imchic.view.MainActivity
import es.dmoral.toasty.Toasty
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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

        @SuppressLint("CommitPrefEdits")
        fun applyTheme(context: Context, theme: String) {
            when (theme) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "system", "" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            val themePref = (context as MainActivity).pref
            val editor = themePref.edit()
            editor.putString("theme", theme)
            editor.apply()

        }

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

        fun getHashId(context: Context): String {
            var hashKey = ""
            try {
                val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    hashKey = String(Base64.encode(md.digest(), 0))
                    logV("Hash Key : $hashKey")
                }
            } catch (e: NoSuchAlgorithmException) {
                logE("해시키 가져오기 에러 : $e")
            } catch (e: Exception) {
                logE("해시키 가져오기 에러 : $e")
            }
            return hashKey
        }

        fun getUUID(context: Context): String {
            val uuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            logV("UUID : $uuid")
            return uuid
        }

        private fun getAndroidAPI(): Int = android.os.Build.VERSION.SDK_INT

        private fun getAndroidVersionCodeNames(): String =
            android.os.Build.VERSION_CODES::class.java.fields[android.os.Build.VERSION.SDK_INT].name

        fun getAndroidVersion(): String {
            val version = "${android.os.Build.VERSION.RELEASE}, ${getAndroidVersionCodeNames()}, (API: ${getAndroidAPI()})"
            logV("Android Version : $version")
            return version
        }

        fun getModelName(): String {
            val model = android.os.Build.MODEL
            logV("Model Name : $model")
            return model
        }

        fun getDevicesName(): String {
            val device = android.os.Build.DEVICE
            logV("Devices Name : $device")
            return device
        }

        fun getProductName(): String {
            val product = android.os.Build.PRODUCT
            logV("Product Name : $product")
            return product
        }

        fun getAppVersion(context: Context): String {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            logV("App Version : $version")
            return version
        }

        fun getAppDisplaySize(context: Context): String {
            val display = context.resources.displayMetrics
            val width = display.widthPixels
            val height = display.heightPixels
            val size = "$width x $height"
            logV("App Display Size : $size")
            return size
        }

        fun getAppMetrics(context: Context): Array<String> {
            val display = context.resources.displayMetrics
            val density = display.density
            val densityDpi = display.densityDpi
            val scaledDensity = display.scaledDensity
            val xdpi = display.xdpi
            val ydpi = display.ydpi
            val metrics =
                "density: $density, densityDpi: $densityDpi, scaledDensity: $scaledDensity, xdpi: $xdpi, ydpi: $ydpi"
            logV("App Metrics : $metrics")

            return arrayOf(
                density.toString(),
                densityDpi.toString(),
                scaledDensity.toString(),
                xdpi.toString(),
                ydpi.toString()
            )
        }

        fun setDevServerUrl(requireContext: Context, url: String) {
            //val pref = requireContext.getSharedPreferences("dev_server_url", AppCompatActivity.MODE_PRIVATE)
            val pref = PreferenceManager.getDefaultSharedPreferences(requireContext)
            val editor = pref.edit()
            editor.putString("developer_mode_server_seturl", url)
            editor.apply()

            logV("개발자 모드 서버 URL : ${pref.getString("developer_mode_server_seturl", "")}")
        }

    }

}