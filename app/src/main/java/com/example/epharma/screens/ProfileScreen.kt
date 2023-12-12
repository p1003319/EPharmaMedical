package com.example.epharma.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import coil.compose.rememberImagePainter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.epharma.EditScreen
import com.example.epharma.LoginActivity
import com.example.epharma.R
import com.example.epharma.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Settings(context : Context) {

    var user by remember { mutableStateOf(
        UserModel(
            name = "John Doe",
            email = "john.doe@example.com",
            phone = "+1 123-456-7890",
            address = "123 Main Street, City",
            image_url = "",
            user_id =  ""
        )
    ) }

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(uid) {
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("User").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userData = document.toObject(UserModel::class.java)
                        if (userData != null) {
                            // Update the user data
                            user = UserModel(
                                userData.name,
                                userData.email,
                                userData.phone,
                                userData.address,
                                userData.image_url,
                                userData.user_id
                            )
                        }
                    } else {
                        // The document does not exist
                    }
                }
        }
    }
    val localContext = LocalContext.current
    ProfileScreen(context = context,user = user, onEditProfileClick = {
        val intent = Intent(context, EditScreen::class.java)
        localContext.startActivity(intent)
    })
}

@Composable
fun ProfileScreen(context:Context,user: UserModel, onEditProfileClick: () -> Unit) {
    val localContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = onEditProfileClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    Color(android.graphics.Color.parseColor("#D3F8D3")),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .border(2.dp, Color(android.graphics.Color.parseColor("#316431")), shape = RoundedCornerShape(16.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            // Assuming you have a user profile picture
            Image(
                painter = rememberImagePainter(user.image_url),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoItem("Name", user.name)
        ProfileInfoItem("Email", user.email)
        ProfileInfoItem("Phone", user.phone)
        ProfileInfoItem("Address", user.address)

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.9f))
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(context, LoginActivity::class.java)
                localContext.startActivity(intent)
                (context as? Activity)?.finish()
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#4CAF50")))
        ){
            Text("Logout", color = Color.White)
        }

    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = Color(android.graphics.Color.parseColor("#65A765"))
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1
        )
    }
}


@Composable
fun SettingItem(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Green
        )
    }
}
