package com.ct.framework.nav

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ct.framework.R
import kotlinx.android.synthetic.main.activity_nav.*


/**
 * 配合 popUpTo
 * popUpToInclusive true  表示销毁导航栈中所有在目的地Fragment上面的界面 并销毁目的地Fragment
 *                  false 表示销毁导航栈中所有在目的地Fragment上面的界面 并保留目的地Fragment
 *
 *
 * popBackStack() 弹出当前栈顶界面
 * popBackStack( int destinationId, boolean inclusive) destinationId 从栈顶弹出元素 直到界面Id 等于destinationId
 *                                                     inclusive  false 表示不保留和 destinationId 相等的界面
 *                                                                true  表示保留和 destinationId 相等的界面
 * */
class NavActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = fragment.navController
        configAppbar()

    }

    private fun configAppbar() {
        appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)

        //直接使用AppCompatActivity的setupActionBarWithNavController
        //需要重写onSupportNavigationUp()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun configMenu() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav_host_01, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            navController
        ) || super.onOptionsItemSelected(item)
    }
}