package com.example.guideme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*

import androidx.appcompat.widget.Toolbar
import com.google.firebase.FirebaseApp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BusinessSignUp : AppCompatActivity() {


    private lateinit var database: DatabaseReference
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
    lateinit var rootNode: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_business_sign_up)

        //  if(!FirebaseApp.getApps(this).isEmpty()) {
        //     FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //   }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {

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


           /* rootNode = FirebaseDatabase.getInstance()
           reference = rootNode.getReference("businessuser");


           val firstName: String = etName.getText().toString()
            val lastName: String = etLastName.getText().toString()
            val shopName: String = etShopName.getText().toString()
            val email : String = etEmail.getText().toString()
            val openTime: String = txtOpenTime.getText().toString()*/


              rootNode = FirebaseDatabase.getInstance()
    reference = rootNode.getReference("BusinessUsers");


    val name: String = etName.getText().toString()
    val email: String = etEmail.getText().toString()
    val address: String = etAddress.getText().toString()
    val phoneNo: String = etPhoneNumber.getText().toString()
    val password: String = etPassword.getText().toString()



    val helperClass = BusinessUser(name, address, email, phoneNo,password)
    reference.child(phoneNo).setValue(helperClass)




           /*val personalDetails = BusinessUser(firstName, lastName, shopName, email, openTime)
            reference.child(email).setValue(personalDetails)
             val shopDetails = BusinessUser(shopName, shopType,
                openTime,closeTime, phoneNumber )
            reference1.child(phoneNumber).setValue(shopDetails )
            val addressDetails = BusinessUser( address,  phoneNumber,pinCode,city, state,)
            reference1.child(phoneNumber).setValue(addressDetails)*/

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

