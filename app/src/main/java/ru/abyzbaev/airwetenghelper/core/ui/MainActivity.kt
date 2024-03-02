package ru.abyzbaev.airwetenghelper.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.abyzbaev.airwetenghelper.R
import ru.abyzbaev.airwetenghelper.databinding.ActivityMainBinding
import ru.abyzbaev.airwetenghelper.autentification.features.pdffiles.PdfListFragment
import ru.abyzbaev.airwetenghelper.autentification.features.home.SensorsFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(SensorsFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    replaceFragment(PdfListFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(SensorsFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, fragment)
//            .commit()
    }
}