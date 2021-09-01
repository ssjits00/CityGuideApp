package com.example.guideme


import android.content.ContentValues.TAG
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.guideme.DashboardActivity
import com.example.guideme.ForgotPasswordActivity
import com.example.guideme.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp


import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class LoginActivity : AppCompatActivity() {
    private var mGoogleSignInClient: GoogleSignInClient? = null


    /*Declaring the different variables used for this activity*/
    private lateinit var registerYourself: TextView
    private lateinit var login: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtForgotPassword: TextView
    lateinit var txtAdminLogin :TextView
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private lateinit var auth: FirebaseAuth
    private lateinit var btnGoogleSignIn: ImageButton

    companion object {

        private const val RC_SIGN_IN = 9001
    }

    lateinit var gso: GoogleSignInOptions


    /*Life-cycle method of the activity*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Linking the view*/
        setContentView(R.layout.activity_login)


        /*Initialising the views with the ones defined in the XML*/
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        registerYourself = findViewById(R.id.txtRegisterYourself)
        login = findViewById(R.id.btnLogin)
        auth = FirebaseAuth.getInstance()
        btnGoogleSignIn = findViewById(R.id.googleSignIn)
        txtAdminLogin = findViewById(R.id.txtAdminLogin)


        createRequest()
        auth = Firebase.auth

        /*Handling the clicks using the setOnClickListener method*/
        txtForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
        txtAdminLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, AdminLogin::class.java))
        }

        registerYourself.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }


        login.setOnClickListener {
            signInUser()

        }
        btnGoogleSignIn.setOnClickListener {
            signIn()
        }
    }
        override fun onStart() {
            super.onStart()
            val user = auth!!.currentUser

            if (user != null) {
                val intent = Intent(applicationContext, DashboardActivity::class.java)
                startActivity(intent)
            }
           /* else
            {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }*/
        }




    fun createRequest() {


        // Configure Google Sign In
         gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestIdToken("659620307680-gf6qevtqk8ap1g45duivdnfmjp02ie2a.apps.googleusercontent.com")
            .requestEmail()
            .build()


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signIn() {
        val signInIntent = this.mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        try {
            super.startActivityForResult(intent, requestCode)
        } catch (ignored: Exception) {
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {

                try {

                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!

                   firebaseAuthWithGoogle(account.idToken!!)


                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately

                }
            }
            } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }



    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }



    private fun signInUser() {
        if (etEmail.text.toString().isEmpty()) {
            etEmail.error = "Please enter email"
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = "Please enter valid email"
            etEmail.requestFocus()
            return
        }

        if (etPassword.text.toString().isEmpty()) {
            etPassword.error = "Please enter password"
            etPassword.requestFocus()
            return
        }

        fAuth.signInWithEmailAndPassword(etEmail.text.toString().trim(), etPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("data", "login")
                    bundle.putString("mobile", etEmail.text.toString())
                    bundle.putString("password", etPassword.text.toString())
                    intent.putExtra("details", bundle)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}




