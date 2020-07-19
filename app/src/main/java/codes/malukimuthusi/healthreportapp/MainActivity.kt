package codes.malukimuthusi.healthreportapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import codes.malukimuthusi.healthreportapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navigationView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }



    private fun checkAdminRights() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return

        val isAdmin = sharedPref.getBoolean(getString(R.string.admin_string), false)

        if (isAdmin) {
//            logInForAdmin()
        }

    }



    companion object {
        const val RC_SIGN_IN = 8925
    }

}
