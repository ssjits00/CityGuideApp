package com.example.guideme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.example.guideme.ProfileFragment
import com.example.guideme.R
import com.example.guideme.AboutAppFragment
import com.example.guideme.DashboardFragment
import com.example.guideme.FavouritesFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {
    lateinit var drawerLayout : androidx.drawerlayout.widget.DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frame :FrameLayout
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var navigationView : NavigationView

    private lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    private var mGoogleSignInClient: GoogleSignInClient? = null

    var previousMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        auth = Firebase.auth

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frame = findViewById(R.id.frame)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)


        setUPToolbar()
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null)
            {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId)
            {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }


                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  FavouritesFragment())
                        .commit()
                    supportActionBar?.title ="Favorites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  ProfileFragment())
                        .commit()
                    supportActionBar?.title ="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp-> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,  AboutAppFragment())
                        .commit()
                    supportActionBar?.title ="AboutApp"
                    drawerLayout.closeDrawers()
                }


                R.id.signOut -> {
                    signOut()

                }




            }




            return@setNavigationItemSelectedListener true
        }


    }

    fun setUPToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){

        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()

        supportActionBar?.title ="Dashboard"

        navigationView.setCheckedItem(R.id.dashboard)


    }

    override fun onBackPressed()
    {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag)
        { !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }



    private fun signOut() {
        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestIdToken("659620307680-gf6qevtqk8ap1g45duivdnfmjp02ie2a.apps.googleusercontent.com")
            .requestEmail()
            .build()


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START auth_sign_out]
        Firebase.auth.signOut()
        mGoogleSignInClient?.run { revokeAccess() }
        val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
        // [END auth_sign_out]
    }










}