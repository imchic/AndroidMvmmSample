package com.example.imchic.view

import android.content.SharedPreferences
import android.os.Build
import android.os.Environment
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.preference.PreferenceManager
import com.example.imchic.R
import com.example.imchic.base.BaseActivity
import com.example.imchic.base.BaseViewModel
import com.example.imchic.databinding.ActivityMainBinding
import com.example.imchic.util.AppUtil
import com.example.imchic.util.CallAPI
import com.google.android.material.navigation.NavigationView
import org.gdal.ogr.ogr

class MainActivity
    : BaseActivity<ActivityMainBinding,
        BaseViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModel: BaseViewModel = BaseViewModel()
    override val pref: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(this)

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 권한 허용
            Toast.makeText(this, "권한 허용", Toast.LENGTH_SHORT).show()
        } else {
            // 권한 거부
            Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show()
        }
    }

    init {
        ogr.RegisterAll()
        //val ds = ogr.OpenShared(readScopedPath())
    }

    fun readScopedPath(): String {
        // 안드로이드 11 외부저장소
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val scopedStorageDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            scopedStorageDir?.absolutePath + "/99050/bnd_adm_pg.shp"
        } else {
            Environment.getExternalStorageDirectory().absolutePath + "/test.shp"
        }
    }

    // 경로에 파일이 있는지 확인
    fun isExitFile(path: String): Boolean {
        val file = java.io.File(path)
        return file.exists()
    }

    override fun initStartView() {

        setContentView(binding.root)

        // 네비게이션 드로어 설정
        initUI()
        AppUtil.logI("readScopedPath => ${readScopedPath()}")
        AppUtil.logI(isExitFile(readScopedPath()).toString())

        // gdal dataset
        val ds = org.gdal.ogr.ogr.OpenShared(readScopedPath())
        AppUtil.logI("ds => $ds")
    }

    fun showToolbar(isShow: Boolean) {
        if (isShow) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    private fun initUI() {

        CallAPI.appVersionCheck()
        CallAPI.login()

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

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.HomeFragment, R.id.PermissionFragment -> {
                    showToolbar(false)
                    this.supportActionBar?.setTitle("")
                }

                else -> {
                    showToolbar(true)
                }
            }
        }

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//        return if(navController.currentDestination?.id == R.id.HomeFragment) {
//            false
//        } else {
//            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if(navController.currentDestination?.id == R.id.HomeFragment) {
                //finish()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}