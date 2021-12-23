package com.bartoszdrozd.mediapp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: IUsersRepository
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController)

        // Set up the top app bar
        val coordinator = findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        val layout = coordinator.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val appbar = layout.findViewById<MaterialToolbar>(R.id.top_app_bar)

        setSupportActionBar(appbar)

        appbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment,
                R.id.registerFormPageOneFragment,
                R.id.registerFormPageTwoFragment,
                R.id.resetPasswordFragment -> {
                    appbar.visibility = VISIBLE
                    bottomNavView.visibility = GONE
                }
                R.id.landingPageFragment -> {
                    appbar.visibility = GONE
                    bottomNavView.visibility = GONE
                }
                else -> {
                    appbar.visibility = VISIBLE
                    bottomNavView.visibility = VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            repository.isLogged().collect { isLogged ->
                if (!isLogged) {
                    navController.navigate(R.id.action_to_landingPage)
                }
            }
        }
    }
}