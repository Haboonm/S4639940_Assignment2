package com.example.s4639940_assignment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

// Hosts the NavHostFragment and wires the toolbar to Navigation.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use MaterialToolbar as the ActionBar (gives us screen titles + Up button).
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get NavController from the NavHostFragment in the layout.
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        // Hook ActionBar to nav so the back arrow works automatically.
        setupActionBarWithNavController(navController)
    }
    // Ensure the toolbar Up button actually navigates up in the graph.
    override fun onSupportNavigateUp(): Boolean {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }
}
