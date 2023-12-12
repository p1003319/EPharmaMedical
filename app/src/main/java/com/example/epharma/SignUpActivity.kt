package com.example.epharma

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.epharma.models.UserModel
import com.example.epharma.ui.theme.EPharmaTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EPharmaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SignUpScreen()
                }
            }
        }
    }

    @Composable
    fun SignUpScreen() {
        // State variables for name, email, and password
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input fields for name, email, and password
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign-up button
            Button(
                onClick = {
                    createUserInFirebase(email, password)
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#4CAF50")))
            ){
                Text("Sign Up", color = Color.White)
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Already have an account? Login button
            Text(
                text = "Already have an account? Log in",

                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        // Finish the current activity if needed
                        finish()
                    }
                    .padding(8.dp)
            )
        }
    }


    fun createUserInFirebase(email: String, password: String) {
        try {

            // Check if Firebase is initialized, if not, initialize it



            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        CreateUserDetailsInFirebase()
                        val intent = Intent(this@SignUpActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    exception ->
                    Toast.makeText(this@SignUpActivity, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Log.e("Manan", "Exception: ${e.message}")
        }
    }


    private fun CreateUserDetailsInFirebase(){
        var user = FirebaseAuth.getInstance().getCurrentUser()
        if (user != null) {
            val uid = user.uid
            val name = user.email.toString()
            val atIndex = name.indexOf('@')
            val username = if (atIndex != -1 && atIndex < name.length - 1) {
                name.substring(0, atIndex)
            } else {
                // Handle the case where the email doesn't contain '@' or is empty
                // For example, return a default value or show an error message
                "John Deo"
            }
            val userDoc = UserModel(
                name = username,
                phone = "123456789",
                image_url = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                user_id = uid,
                email = user.email.toString(),
                address = ""
            )
            FirebaseFirestore.getInstance().collection("User").document(uid).set(userDoc)
            // Now you can use the userDoc object as needed
        }
    }





}
