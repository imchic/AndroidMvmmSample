package com.example.imchic.base

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imchic.extension.repeatOnStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar

/**
 * @param T : ViewDataBinding
 * @param R : BaseViewModel
 * @property layoutResourceId Int
 * @property viewModel R
 * @property binding T
 * @property snackbar Snackbar?
 * @property progressIndicator CircularProgressIndicator?
 * @property sharedPref SharedPreferences
 */

@Suppress("IMPLICIT_CAST_TO_ANY")
abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity() {

    abstract val layoutResourceId: Int
    abstract val viewModel: R
    lateinit var binding: T

    // global widget
    private var snackbar: Snackbar? = null
    private var progressIndicator: CircularProgressIndicator? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null

    private lateinit var sharedPref: SharedPreferences

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    abstract fun initStartView()
    abstract fun initDataBinding()
    abstract fun initAfterBinding()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this@BaseActivity

        // 공통 툴바 생성
        toolbar = findViewById(com.example.imchic.R.id.toolbar)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

        sharedPref = getSharedPreferences("theme", MODE_PRIVATE)
        applyTheme(sharedPref.getString("theme", "").toString())

        setContentView(layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.imchic.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.example.imchic.R.id.action_theme -> {
                val themeArr = resources.getStringArray(com.example.imchic.R.array.themeArr)
                viewModel.themeSelectAlertDialog(themeArr)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleEvent(event: BaseViewModel.Event) = when (event) {

        is BaseViewModel.Event.ShowLoadingBar -> {

            val bool = event.isShow
            initWidgetUI()

            progressIndicator?.visibility = when (bool) {
                true -> View.VISIBLE
                false -> View.GONE
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

            val singleChoiceAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, event.data)
            var pos = when(sharedPref.getString("theme", "").toString()) {
                "light" -> 0
                "dark" -> 1
                "system" -> 2
                else -> 0
            }

            MaterialAlertDialogBuilder(this)
                .setTitle("테마변경")
                .setSingleChoiceItems(singleChoiceAdapter, pos) { dialog, which ->

                    pos = which

                    when (pos) {
                        0 -> viewModel.setTheme("light")
                        1 -> viewModel.setTheme("dark")
                        2 -> viewModel.setTheme("system")
                    }

                    viewModel.setThemePos(pos)

                    try {
                        AppLog.i("pos : ${viewModel.themePos.value}")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    applyTheme(viewModel.theme.value)
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    /**
     * 테마 변경
     * @param theme String
     */
    private fun applyTheme(theme: String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system", "" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        sharedPref.edit().putString("theme", theme).apply()

    }

    private fun initWidgetUI() {

        if (progressIndicator == null) {
            progressIndicator = CircularProgressIndicator(this)
            progressIndicator?.run {
                isIndeterminate = true
                isClickable = false
                isFocusable = false
                isFocusableInTouchMode = false
                isActivated = false
                isSaveEnabled = false
                isSaveFromParentEnabled = false
            }

            val layout = findViewById<View>(android.R.id.content).rootView as ViewGroup
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            val ll = LinearLayout(this)

            ll.gravity = Gravity.CENTER
            ll.addView(progressIndicator)

            layout.addView(ll, params)

        }

        if (snackbar == null) snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)

    }

}