package com.example.esgithub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_frag) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomNavigation, navController)

        this.listenToBottomNavigation(bottomNavigation, navController)
    }

    private fun listenToBottomNavigation(
        bottomNavigation: BottomNavigationView,
        navController: NavController
    ) {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    this.onNavigationItemClick(navController, R.id.homeFragment)
                }
                R.id.groupsFragment -> {
                    this.onNavigationItemClick(navController, R.id.groupsFragment)
                }
                R.id.likedFragment -> {
                    this.onNavigationItemClick(navController, R.id.likedFragment)
                }
                R.id.profileFragment -> {
                    this.onNavigationItemClick(navController, R.id.profileFragment)
                }
                else -> false
            }
        }
    }

    private fun onNavigationItemClick(
        navController: NavController,
        id: Int
    ): Boolean {
        navController.navigate(id)

        return true
    }
}
