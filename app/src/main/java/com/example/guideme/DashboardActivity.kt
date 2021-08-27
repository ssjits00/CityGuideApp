package com.example.guideme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import com.example.guideme.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    /*Declaring the textview used for displaying the data*/
    lateinit var txtData: TextView
    lateinit var btnSignOut: Button
    private lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    private var mGoogleSignInClient: GoogleSignInClient? = null


    /*Life-cycle method*/
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        /*Initialising the textview and linking it with the textview created in XML*/
        txtData = findViewById(R.id.txtData)

        btnSignOut = findViewById(R.id.btnSignOut)

        btnSignOut.setOnClickListener {
            signOut()
        }
        auth = Firebase.auth


        /*Checking whether any data was received through the intent or not*/
        if (intent != null) {

            /*Fetching the details from the intent*/
            val details = intent.getBundleExtra("details")

            /*Getting the value of data from the bundle object*/
            val data = details?.getString("data")

            /*Checking the location from which data was sent*/
            if (data == "login") {
                /*Creating the text to be displayed*/
                val text = "Mobile Number : ${details?.getString("mobile")} \n " +
                        "Password : ${details?.getString("password")}"
                txtData.text = text
            }

            if (data == "register") {
                val text = " Name : ${details?.getString("name")} \n " +
                        "Mobile Number : ${details?.getString("mobile")} \n " +
                        "Password : ${details?.getString("password")} \n " +
                        "Address: ${details?.getString("address")}"
                txtData.text = text
            }

            if (data == "forgot") {
                val text = "Mobile Number : ${details?.getString("mobile")} \n " +
                        "Email : ${details?.getString("email")}"
                txtData.text = text
            }

        } else {
            /*No data was received through the intent*/
            txtData.text = "No data received!!"
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