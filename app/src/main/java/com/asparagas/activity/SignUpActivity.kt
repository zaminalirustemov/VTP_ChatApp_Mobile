package com.asparagas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asparagas.vtp_chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = FirebaseAuth.getInstance()
        db= Firebase.firestore

        binding.btnSignUp.setOnClickListener {

            val userName = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (userName == "") {
                Toast.makeText(this@SignUpActivity, "username is required", Toast.LENGTH_SHORT)
                    .show()
            } else if (email == "") {
                Toast.makeText(this@SignUpActivity, "email is required", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(this@SignUpActivity, "password is required", Toast.LENGTH_SHORT)
                    .show()
            } else if (confirmPassword == "") {
                Toast.makeText(
                    this@SignUpActivity,
                    "confirm password is required",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this@SignUpActivity, "password not match", Toast.LENGTH_SHORT).show()
            } else {

                registerUser(userName, email, password)
            }

        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(
                this@SignUpActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                // Kullanıcı başarıyla kaydedildi
                val user: FirebaseUser? = authResult.user
                val userId: String = user!!.uid

                // Kullanıcı verilerini Firebase Realtime Database'e ekle
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                val hashMap: HashMap<String, String> = HashMap()
                hashMap["userId"] = userId
                hashMap["userName"] = userName
                hashMap["profileImage"] = ""

                databaseReference.setValue(hashMap)
                    .addOnSuccessListener {
                        // Başarılı bir şekilde veriler kaydedildi, ana ekrana yönlendir
                        binding.etName.text.clear()
                        binding.etEmail.text.clear()
                        binding.etPassword.text.clear()
                        binding.etConfirmPassword.text.clear()

                        val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        // Veriler kaydedilirken bir hata oluştu, kullanıcıya hata mesajı gösterin
                        Toast.makeText(this@SignUpActivity, "Verileri kaydederken hata oluştu: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                // Kullanıcı kaydı başarısız oldu, kullanıcıya hata mesajı gösterin
                Toast.makeText(this@SignUpActivity, "Kullanıcı kaydı başarısız oldu: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


//    private fun registerUser(userName:String,email:String,password:String){
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener(this){
//                if (it.isSuccessful){
//                    val user: FirebaseUser? = auth.currentUser
//                    val userId:String = user!!.uid
//
//
//                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
//
//                    val hashMap:HashMap<String,String> = HashMap()
//                    hashMap.put("userId",userId)
//                    hashMap.put("userName",userName)
//                    hashMap.put("profileImage","")
//
//                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
//                        if (it.isSuccessful){
//                            //open home activity
//                            binding.etName.setText("")
//                            binding.etEmail.setText("")
//                            binding.etPassword.setText("")
//                            binding.etConfirmPassword.setText("")
//                            val intent = Intent(this@SignUpActivity,
//                                UsersActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                }
//            }
//    }


//    private fun registerUser(userName:String,email:String,password:String){
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener(this) {
//                val intent = Intent(this@SignUpActivity,
//                    LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this@SignUpActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
//            }
//    }


}