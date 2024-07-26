package com.cacuango_alexander.maurovision.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cacuango_alexander.maurovision.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener el NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frm_container)
        if (navHostFragment is NavHostFragment) {
            // Obtener el NavController
            navController = navHostFragment.navController

            // Configurar el BottomNavigationView con el NavController
            val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
            NavigationUI.setupWithNavController(bottomNavigationView, navController)

            // Navegar a ListNowPlayingFragment por defecto solo si la actividad se está creando por primera vez
            // y ListNowPlayingFragment no es el destino actual
            if (savedInstanceState == null && navController.currentDestination?.id != R.id.listNowPlayingFragment) {
                navController.navigate(R.id.listNowPlayingFragment)
            }

            // Establecer un OnItemSelectedListener en el BottomNavigationView
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.listNowPlayingFragment -> {
                        navController.navigate(R.id.listNowPlayingFragment)
                        true
                    }

                    R.id.listPopularsFragment -> {
                        navController.navigate(R.id.listPopularsFragment)
                        true
                    }

                    R.id.listTopRatedFragment -> {
                        navController.navigate(R.id.listTopRatedFragment)
                        true
                    }

                    R.id.listUpcomingFragment -> {
                        navController.navigate(R.id.listUpcomingFragment)
                        true
                    }

                    else -> false
                }
            }
        } else {
            // Manejar el caso en que navHostFragment es nulo
            // Puedes lanzar una excepción, mostrar un mensaje de error, etc.
        }
    }
}