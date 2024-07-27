package com.cacuango_alexander.maurovision.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.ui.fragments.ListNowPlayingFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListPopularsFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListTopRatedFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListUpcomingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var fragmentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar el BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Establecer el OnItemSelectedListener en el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.listNowPlayingFragment -> {
                    // Reemplazar el fragmento actual con ListNowPlayingFragment
                    replaceFragment(ListNowPlayingFragment())
                    true
                }

                R.id.listPopularsFragment -> {
                    // Reemplazar el fragmento actual con ListPopularsFragment
                    replaceFragment(ListPopularsFragment())
                    true
                }

                R.id.listTopRatedFragment -> {
                    // Reemplazar el fragmento actual con ListTopRatedFragment
                    replaceFragment(ListTopRatedFragment())
                    true
                }

                R.id.listUpcomingFragment -> {
                    // Reemplazar el fragmento actual con ListUpcomingFragment
                    replaceFragment(ListUpcomingFragment())
                    true
                }

                else -> false
            }
        }

        // Cargar el fragmento inicial si no hay ninguno cargado
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.listNowPlayingFragment
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Reemplaza el fragmento en el contenedor

        // Agrega la transacción a la pila de retroceso solo si fragmentCount es impar
        if (fragmentCount % 2 != 0) {
            transaction.addToBackStack(null) // Opcional: agrega la transacción a la pila de retroceso
        }

        transaction.commit()

        // Incrementa fragmentCount
        fragmentCount++
    }
}