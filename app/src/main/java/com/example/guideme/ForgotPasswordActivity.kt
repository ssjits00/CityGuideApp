package com.example.guideme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


import com.example.guideme.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    /*Declaring the different variables used for this activity*/
    lateinit var etForgotMobile: EditText
    lateinit var etForgotEmail: EditText
    lateinit var btnForgotNext: Button
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    /*Life-cycle method of the activity*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Linking the view*/
        setContentView(R.layout.activity_forgot_password)

        /*Initialising the views with the ones defined in the XML*/
        etForgotMobile = findViewById(R.id.etForgotMobile)
        etForgotEmail = findViewById(R.id.etForgotEmail)
        btnForgotNext = findViewById(R.id.btnForgotNext)

        /*Handling the click on the button using the setOnClickListener method*/
        btnForgotNext.setOnClickListener {
            resetPassword()
        }


    }

    private fun resetPassword() {
        if (etForgotEmail.text.toString().isEmpty()) {
            etForgotEmail.error = "Please enter email"
            etForgotEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etForgotEmail.text.toString()).matches()) {
            etForgotEmail.error = "Please enter valid email"
            etForgotEmail.requestFocus()
            return
        }


        fAuth.sendPasswordResetEmail(etForgotEmail.text.toString().trim())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Password Reset Link Sent to your Email.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Password Reset Failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
