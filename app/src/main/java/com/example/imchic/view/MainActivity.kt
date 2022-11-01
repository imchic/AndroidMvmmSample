package com.example.imchic.view

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.imchic.R
import com.example.imchic.base.AppLog
import com.example.imchic.base.BaseActivity
import com.example.imchic.base.BaseViewModel
import com.example.imchic.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModel: BaseViewModel = BaseViewModel(this)

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun initStartView() {

        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 네비게이션 드로어 설정
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Set up ActionBar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        navigationView.setupWithNavController(navController)

        CoroutineScope(Dispatchers.Main).launch {
//            val job = async(Dispatchers.IO) {
//                viewModel.showLoadingBar(true)
//            }
//            val job2 = async(Dispatchers.IO) {
//                delay(1000)
//                viewModel.showLoadingBar(false)
//            }
//            job.await()
//            job2.await()

        }


    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}