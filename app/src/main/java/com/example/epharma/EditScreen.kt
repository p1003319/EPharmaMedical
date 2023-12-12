package com.example.epharma

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import coil.compose.rememberImagePainter
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.epharma.models.UserModel
import com.example.epharma.ui.theme.EPharmaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class EditScreen : ComponentActivity() {
    val is_image_upload = false
    val image_url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EPharmaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditProfile()
                }
            }
        }
    }


    @Composable
    fun EditProfile(){
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
        var loading by remember { mutableStateOf(true) }
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
                        loading = false
                    }
            }
        }
        val localContext = LocalContext.current

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (loading) {
                // Display a loading indicator while data is being fetched
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                // Data has been fetched, display the EditProfileScreen1
                EditProfileScreen(user = user)
            }
        }


    }


    @Composable
    fun EditProfileScreen(user: UserModel) {
//    var editedUser by remember { mutableStateOf(user.copy()) }
//        var name by remember { mutableStateOf(user.name) }
//        var email by remember { mutableStateOf(user.email) }
//        var phone by remember { mutableStateOf(user.phone) }
//        var address by remember { mutableStateOf(user.address) }
//        var image_url by remember { mutableStateOf(user.image_url) }
//        var user_id by remember { mutableStateOf(user.user_id) }

        var editedUser by remember { mutableStateOf(user.copy()) }

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
                    text = "Edit Profile",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                // Save and Cancel buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            var uid = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
                            if (uid != null) {
                                var userData = UserModel(editedUser.name, editedUser.email, editedUser.phone, editedUser.address, editedUser.image_url, editedUser.user_id)
                                FirebaseFirestore.getInstance().collection("User")
                                    .document(uid)
                                    .set(userData).addOnCompleteListener {
                                        val intent = Intent(this@EditScreen, DashboardActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
                    }

                    IconButton(
                        onClick = {
                            val intent = Intent(this@EditScreen, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Color(android.graphics.Color.parseColor("#D3F8D3")),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .border(
                        2.dp,
                        Color(android.graphics.Color.parseColor("#316431")),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp)
                    .clickable {
                        openCamera()
                    },
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

            // Editable fields
            EditableProfileInfoItem("Name", editedUser.name)
                { editedUser = editedUser.copy(name = it) }

            EditableProfileInfoItem("Email", editedUser.email) {
                editedUser = editedUser.copy(email = it)
            }
            EditableProfileInfoItem("Phone", editedUser.phone) {
                editedUser = editedUser.copy(phone = it)
            }
            EditableProfileInfoItem("Address", editedUser.address) {
                editedUser = editedUser.copy(address = it)
            }
        }
    }


    @Composable
    fun EditableProfileInfoItem(label: String, value: String, onValueChange: (String) -> Unit) {
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

            // Editable field
            BasicTextField(
                value = value,
                onValueChange = { updatedValue ->
                    onValueChange(updatedValue)
                },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(android.graphics.Color.parseColor("#E8E8E8")),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(8.dp)
            )
        }
    }


    private val REQUEST_IMAGE_CAPTURE = 1

    private fun openCamera() {
        Log.e("man log" , "Openning Camera")
        if (checkPermissionsCamera()) {
            if (isCameraPermissionEnabled()) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                }}
        }
        else{
            Log.e("man log" , "requesting for camera permission")
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the camera
                openCamera()
            } else {
                // Permission denied, handle accordingly (show a message, etc.)
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkPermissionsCamera(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isCameraPermissionEnabled(): Boolean {
        val permission = Manifest.permission.CAMERA
        val result = ContextCompat.checkSelfPermission(this, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private val CAMERA_PERMISSION_REQUEST_CODE = 123

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val storage = FirebaseStorage.getInstance()
            val imageBitmap = data?.extras?.get("data") as Bitmap
            //binding.image.setImageBitmap(imageBitmap)
            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child("profile_photo").child(fileName)
            // Convert the Bitmap to a byte array
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            // Upload the image to Firebase Storage
            val uploadTask = storageRef.putBytes(data)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()

                        Log.d("MainActivity", "Download URL: $downloadUrl")
                        val uid = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
                        if (uid != null) {

                            FirebaseFirestore.getInstance().collection("User").document(uid).get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        val userData = document.toObject(UserModel::class.java)
                                        if (userData != null) {
                                            // Update the user data
                                            var user = UserModel(
                                                userData.name,
                                                userData.email,
                                                userData.phone,
                                                userData.address,
                                                downloadUrl,
                                                userData.user_id
                                            )
                                            FirebaseFirestore.getInstance().collection("User").document(uid).set(user)
                                        }
                                    } else {
                                        // The document does not exist
                                    }
                                }

                        }
                        Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT)
                            .show()
                    } } else {
                    val exception = task.exception
                }
            }
        }
    }

}

