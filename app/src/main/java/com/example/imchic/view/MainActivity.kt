package com.example.imchic.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.documentfile.provider.DocumentFile
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
import org.gdal.ogr.DataSource
import org.gdal.ogr.ogr
import java.io.File


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

    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var trrDocumentUri: Uri

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 권한 허용
            Toast.makeText(this, "권한 허용", Toast.LENGTH_SHORT).show()
        } else {
            // 권한 거부
            Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("Range")
    override fun initStartView() {

        setContentView(binding.root)

        // 네비게이션 드로어 설정
        initUI()

        // Storage Access Framework 사용
//        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//
//                trrDocumentUri = result.data?.data ?: return@registerForActivityResult
//                val takeFlags =
//                    (intent.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))
//
//                AppUtil.logI("uri : $trrDocumentUri")
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    contentResolver.takePersistableUriPermission(
//                        trrDocumentUri,
//                        takeFlags!!
//                    )
//                }
//
//                getGdalResourceFileList(trrDocumentUri)
//
//            }
//        }
//
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
//            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//        }
//        getResult.launch(intent)
    }

    fun getGdalResourceFileList(treeUri: Uri){

        // treeUri 를 이용하여 DocumentFile 객체 생성
        val treeDocumentFile = DocumentFile.fromTreeUri(this, treeUri)

        // DocumentFile 객체를 이용하여 하위 파일 목록 가져오기
        val children = treeDocumentFile?.listFiles()
        children?.forEach { child ->

            // 파일의 이름과 크기를 가져오기
            val name = child.name
            val size = child.length()
            val mimeType = child.type
            val file = File(applicationContext.getExternalFilesDir("mCAPI_MapDown/99050/"), name)
            val layerNm = file.name.split(".")[0]

            //AppUtil.logV("name : $name, size : $size, mimeType: $mimeType, layerNm : $layerNm")

            // 임시
            if(name == "bnd_poed_pg.shp") {

                // 파일 복사
                val inputStream = contentResolver.openInputStream(child.uri)
                val outputStream = file.outputStream()
                inputStream?.copyTo(outputStream)
                inputStream?.close()

                AppUtil.logD(file.name)
                AppUtil.logD(file.extension)
                AppUtil.logD(file.absolutePath)

                ogr.RegisterAll()

                val dataSource: DataSource = ogr.OpenShared(file.absolutePath, 0) ?: return
                AppUtil.logI("getShape Layer Name : $layerNm")
                val layerCount = dataSource.GetLayer(layerNm).GetFeatureCount()
                AppUtil.logI("getShape File Layer Count : $layerCount")

                for(i in 0  until layerCount) {
                    val feature = dataSource.GetLayer(layerNm).GetFeature(i)
                    val featureName = feature.GetFieldAsString("NAME")
                    AppUtil.logI("getShape File Feature Name : $featureName")
                }
            }

        }
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