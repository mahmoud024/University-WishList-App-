package com.example.universitywishlistapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.universitywishlistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ربط التولبار
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        // ربط ActionBar مع NavController و DrawerLayout (يعطي سلوك الهامبرغر والسهم الصحيح)
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // ربط BottomNavigationView مع NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // التعامل مع عناصر القائمة الجانبية (NavigationView)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val country = when (menuItem.itemId) {
                R.id.menu_uk -> "United Kingdom"
                R.id.menu_usa -> "United States"
                R.id.menu_canada -> "Canada"
                R.id.menu_germany -> "Germany"
                else -> null
            }

            country?.let {
                // ارجع إلى UniversitiesFragment إن لم تكن فيه
                navController.popBackStack(R.id.universitiesFragment, false)
                // أرسل الدولة المختارة للـ Fragment عبر SavedStateHandle
                navController.currentBackStackEntry?.savedStateHandle?.set("selected_country", it)
            }

            // اغلق القائمة الجانبية
            binding.drawerLayout.closeDrawers()
            true
        }

        // التعامل مع زر الرجوع في الجهاز (إغلاق القائمة الجانبية إن كانت مفتوحة)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // السماح بالسلوك الافتراضي لزر الرجوع
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    // زر السهم في التولبار (back arrow) - يدير التنقل ويغلق القائمة الجانبية عند الحاجة
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(binding.drawerLayout) || super.onSupportNavigateUp()
    }
}
