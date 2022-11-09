package com.example.imchic.base

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
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
import javax.inject.Inject


/**
 * @param B : ViewDataBinding
 * @param V : BaseViewModel
 * @property layoutResourceId Int
 * @property viewModel V
 * @property binding B
 * @property fm FragmentManager
 * @property snackbar Snackbar?
 * @property toolbar Toolbar?
 * @property appBarConfiguration AppBarConfiguration
 * @property navHostFragment NavHostFragment
 * @property navController NavController
 * @property loadingDialogFragment LoadingDialogFragment
 */

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    abstract val layoutResourceId: Int
    abstract val viewModel: V

    abstract val pref : SharedPreferences

    lateinit var binding: B

    private val fm: FragmentManager = supportFragmentManager

    // UI widget
    private var snackbar: Snackbar? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject lateinit var loadingDialogFragment: LoadingDialogFragment


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

        // Theme
        val theme = pref.getString("theme", "system")
        if (theme != null) {
            AppUtil.applyTheme(this, theme)
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
            R.id.action_shutdown -> {
                viewModel.shutdownAlertDialog(true)
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
            } as Any
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
