package com.example.navigationdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(R.id.nav_host_fragment), drawerLayout)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)

        //Update action bar
        setupActionBarWithNavController( navController, drawerLayout)

        // Handle nav drawer
        navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        // Tie nav graph
        setupWithNavController(navigationView, navController)
    }
}
