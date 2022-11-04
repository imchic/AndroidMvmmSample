package com.example.imchic.base

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.imchic.R
import com.example.imchic.extension.repeatOnStarted
import com.example.imchic.util.AppUtil
import com.example.imchic.view.dialog.LoadingDialogFragment
import com.example.imchic.view.dialog.ShutdownDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.RuntimeException


/**
 * @param B : ViewDataBinding
 * @param V : BaseViewModel
 * @property layoutResourceId Int
 * @property viewModel V
 * @property binding B
 * @property snackbar Snackbar?
 * @property toolbar Toolbar?
 * @property sharedPref SharedPreferences
 * @property appBarConfiguration AppBarConfiguration
 * @property navHostFragment NavHostFragment
 * @property navController NavController
 */

@Suppress("IMPLICIT_CAST_TO_ANY")
abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    abstract val layoutResourceId: Int
    abstract val viewModel: V
    lateinit var binding: B

    private val fm: FragmentManager = supportFragmentManager

    // UI widget
    private var snackbar: Snackbar? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private lateinit var sharedPref: SharedPreferences


    abstract fun initStartView()
    abstract fun initDataBinding()
    abstract fun initAfterBinding()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        setContentView(layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()

    }

    private fun initUI() {

        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        //binding.lifecycleOwner = this@BaseActivity

        // 공통 툴바 생성
        toolbar = findViewById(R.id.toolbar)
        snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                val themeSelectAlertDialogArr = resources.getStringArray(R.array.themeArr)
                viewModel.themeSelectAlertDialog(themeSelectAlertDialogArr.toMutableList())
                true
            }

            R.id.action_shutdown -> {
                viewModel.shutdownAlertDialog(true)
                AppUtil.logD("shutdown")
                throw Exception("Test Crash")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        is BaseViewModel.Event.ShowLoadingBar -> {

            if (event.isShow) {
                LoadingDialogFragment().show(fm, LoadingDialogFragment.TAG)
                AppUtil.logD("show loading")
            } else {
                fm.findFragmentByTag(LoadingDialogFragment.TAG)?.let {
                    (it as LoadingDialogFragment).dismissAllowingStateLoss()
                }
                AppUtil.logD("dismiss loading")
            }
        }

        is BaseViewModel.Event.ShowSnackBar -> {
            snackbar?.run {
                setText(event.text)
                animationMode = ANIMATION_MODE_SLIDE
                show()
            }
        }

        is BaseViewModel.Event.ShowSnackbarString -> {
            snackbar?.run {
                setText(event.text)
                animationMode = ANIMATION_MODE_SLIDE
                show()
            }
        }

        is BaseViewModel.Event.ShowToast -> {
            Toast.makeText(this, event.text, Toast.LENGTH_SHORT).show()
        }

        is BaseViewModel.Event.ShowToastString -> {
            Toast.makeText(this, event.text, Toast.LENGTH_SHORT).show()
        }

        is BaseViewModel.Event.ShowAlertDialog -> {
            MaterialAlertDialogBuilder(this)
                .setTitle(event.data[0])
                .setMessage(event.data[1])
                .setCancelable(false)
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        is BaseViewModel.Event.ThemeSelectAlertDialog -> {

            val singleChoiceAdapter = ArrayAdapter(
                this,
                com.google.android.material.R.layout.mtrl_alert_select_dialog_singlechoice,
                event.data
            )
            //var pos = when(sharedPref.getString("theme", "").toString()) {
            var pos = when (viewModel.theme.value) {
                "light" -> 0
                "dark" -> 1
                "system" -> 2
                else -> 0
            }

            MaterialAlertDialogBuilder(this)
                .setTitle("테마변경")
                .setIcon(R.drawable.ic_baseline_brightness_6_24)
                .setSingleChoiceItems(singleChoiceAdapter, pos) { dialog, which ->

                    pos = which

                    when (pos) {
                        0 -> viewModel.setTheme("light")
                        1 -> viewModel.setTheme("dark")
                        2 -> viewModel.setTheme("system")
                    }

                    viewModel.setThemePos(pos)

                    try {
                        AppUtil.logI("pos : ${viewModel.themePos.value}")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    AppUtil.applyTheme(viewModel.theme.value)
                    dialog.dismiss()
                }
                .show()
        }

        is BaseViewModel.Event.ShutdownAlertDialog -> {

            if (event.isShow) {
                ShutdownDialogFragment().show(fm, ShutdownDialogFragment.TAG)
                AppUtil.logD("show shutdown")
            } else {
                fm.findFragmentByTag(ShutdownDialogFragment.TAG)?.let {
                    (it as ShutdownDialogFragment).dismissAllowingStateLoss()
                }
                AppUtil.logD("dismiss shutdown")
            }
        }

        else -> {}
    }


}
