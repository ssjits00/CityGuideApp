package com.example.guideme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/*The tasks performed in this activity are similar to the ones done in the previous activities.
* Task : Try to make out the usage of each line of code and asd appropriate comments yourself
* */


class RegisterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var btnRegistration: Button
    lateinit var btnBusinessRegistration: Button
    lateinit var etName: EditText
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
        setContentView(R.layout.activity_register)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rlRegister = findViewById(R.id.rlRegister)
        etName = findViewById(R.id.etName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etAddress = findViewById(R.id.etAddress)
        btnRegistration = findViewById(R.id.btnRegistration)

        btnBusinessRegistration = findViewById(R.id.btnBusinessRegistration)
        btnBusinessRegistration.setOnClickListener {
       val intent = Intent(this@RegisterActivity, BusinessSignUp::class.java)
            startActivity(intent)


           // startActivity(Intent(this@RegisterActivity, BusinessSignUp::class.java))

        }
btnRegistration.setOnClickListener {

    rootNode = FirebaseDatabase.getInstance()
    reference = rootNode.getReference("users");


    val name: String = etName.getText().toString()
    val email: String = etEmail.getText().toString()
    val address: String = etAddress.getText().toString()
    val phoneNo: String = etPhoneNumber.getText().toString()
    val password: String = etPassword.getText().toString()
    val confirmPassword: String = etConfirmPassword.getText().toString()



    val helperClass = User(name, address, email, phoneNo,password,confirmPassword)
    reference.child(phoneNo).setValue(helperClass)
signUpUser()
/*val intent = Intent(this@RegisterActivity, DashboardActivity::class.java)
val bundle = Bundle()

bundle.putString("data", "register")
bundle.putString("name", etName.text.toString())
bundle.putString("mobile", etPhoneNumber.text.toString())
bundle.putString("password", etPassword.text.toString())
bundle.putString("address", etAddress.text.toString())
intent.putExtra("details", bundle)
startActivity(intent)*/
}
}

override fun onSupportNavigateUp(): Boolean {
onBackPressed()
return true
}

private fun signUpUser() {
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

fAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
.addOnCompleteListener(this) { task ->
   if (task.isSuccessful) {
       startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
       finish()
   } else {
       Toast.makeText(baseContext, "Sign Up failed. Try again after some time.",
           Toast.LENGTH_SHORT).show()
   }
}
}}