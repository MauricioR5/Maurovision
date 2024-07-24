package com.cacuango_alexander.maurovision.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.databinding.ActivityMainBinding
import com.cacuango_alexander.maurovision.ui.fragments.ListNowPlayingFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListPopularsFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListTopRatedFragment
import com.cacuango_alexander.maurovision.ui.fragments.ListUpcomingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

    }

    private fun initListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val manager = supportFragmentManager

            when (item.itemId) {
                R.id.it_new -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, ListNowPlayingFragment())
                    transaction.commit()
                    true
                }

                R.id.it_popular -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, ListPopularsFragment())
                    transaction.commit()
                    true
                }

                R.id.it_rated -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, ListTopRatedFragment())
                    transaction.commit()
                    true
                }

                R.id.it_upcoming -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, ListUpcomingFragment())
                    transaction.commit()
                    true
                }

                else -> {
                    false
                }
            }
        }

    }


}