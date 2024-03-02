package ru.abyzbaev.airwetenghelper.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.abyzbaev.airwetenghelper.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.navigation_home -> {
//                    replaceFragment(SensorsFragment())
//                    true
//                }
//                R.id.navigation_dashboard -> {
//                    replaceFragment(PdfListFragment())
//                    true
//                }
//                else -> false
//            }
//        }
//        replaceFragment(SensorsFragment())
//    }

//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, fragment)
//            .commit()
    }
}