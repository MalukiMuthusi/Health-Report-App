package codes.malukimuthusi.healthreportapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val drawer: DrawerLayout = findViewById(R.id.drawer)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        val navController = findNavController(R.id.nav_host_fragment)
        navigationView.setupWithNavController(navController)
        navView.setupWithNavController(navController)
    }
}
