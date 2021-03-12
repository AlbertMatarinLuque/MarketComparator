package cat.copernic.daniel.marketcomparator


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cat.copernic.daniel.marketcomparator.databinding.ActivityMainBinding
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private val repo = Repo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupFirebaseAuth()
        title = "MarketComparator"
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.productFragment, R.id.authActivity,
                R.id.settingsFragment, R.id.aboutFragment, R.id.addProductsFragment, R.id.users,
                R.id.gestionMercados
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setnav(navView)

        setnavView(navView)

        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Coses del Firebase
    private fun setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.")
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(@NonNull firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.getCurrentUser()
                if (user != null) {
                    setcurrentUser(user)
                    repo.getUsername()
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid())
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }
    }


}