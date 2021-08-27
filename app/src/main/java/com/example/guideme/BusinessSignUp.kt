package com.example.guideme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*

import androidx.appcompat.widget.Toolbar

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BusinessSignUp : AppCompatActivity() {


    private lateinit var database : DatabaseReference
    lateinit var toolbar: Toolbar
    lateinit var btnBusinessRegister: Button
    lateinit var etName: EditText
    lateinit var etShopName: EditText
    lateinit var etShopType: EditText
    lateinit var etPinCode: EditText
    lateinit var etCountry: EditText
    lateinit var etCity: EditText
    lateinit var etState: EditText
    lateinit var txtOpenTime: EditText
    lateinit var txtCloseTime: EditText

    lateinit var etLastName: EditText

    lateinit var etPhoneNumber: EditText
    lateinit var etPassword: EditText
    lateinit var etEmail: EditText
    lateinit var etAddress: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var rlRegister: RelativeLayout
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var rootNode1: FirebaseDatabase
    lateinit var reference1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_business_sign_up)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        rlRegister = findViewById(R.id.rlRegister)
        etName = findViewById(R.id.etName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etAddress = findViewById(R.id.etAddress)
        btnBusinessRegister = findViewById(R.id.btnRegister)

        btnBusinessRegister.setOnClickListener {

          rootNode1 = FirebaseDatabase.getInstance()
            reference1 = rootNode1.getReference("users");


            val firstName: String = etName.getText().toString()
            val email : String = etEmail.getText().toString()
            val address: String = etAddress.getText().toString()
            val phoneNumber: String = etPhoneNumber.getText().toString()
            val password: String = etPassword.getText().toString()

            val lastName: String = etLastName.getText().toString()
            val shopType: String = etShopType.getText().toString()
            val shopName: String = etShopName.getText().toString()
            val openTime: String = txtOpenTime.getText().toString()
            val closeTime: String = txtCloseTime.getText().toString()

            val state:  String = etState.getText().toString()
            val country: String = etCountry.getText().toString()
            val pinCode: String = etPinCode.getText().toString()
            val city: String = etCity.getText().toString()





            val personalDetails = User(firstName,lastName,phoneNumber, email,password)
            reference1.child(phoneNumber).setValue(personalDetails)
           val shopDetails = User(shopName, shopType,
                openTime,closeTime, phoneNumber )
            reference1.child(phoneNumber).setValue(shopDetails )
            val addressDetails = User( address,  phoneNumber,pinCode,city, state,)
            reference1.child(phoneNumber).setValue(addressDetails)

            signUpUser()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun signUpUser() {
        if (etEmail.text.toString().isEmpty()) {
            etEmail.error = "Please Enter Email"
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = "Please Enter Valid Email"
            etEmail.requestFocus()
            return
        }

        if (etPassword.text.toString().isEmpty()) {
            etPassword.error = "Please Enter Password"
            etPassword.requestFocus()
            return
        }
        var p1 = etConfirmPassword.text.toString()
        var p2 = etConfirmPassword.text.toString()
        if (p1!=p2) {

            etPassword.error = "Password didn't Match"
            etPassword.requestFocus()
            return
        }


        fAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this@BusinessSignUp, LoginActivity::class.java))
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

