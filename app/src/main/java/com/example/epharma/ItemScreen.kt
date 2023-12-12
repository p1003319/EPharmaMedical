package com.example.epharma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.epharma.models.CartItem
import com.example.epharma.models.ItemModel
import com.example.epharma.ui.theme.ui.theme.EPharmaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ItemScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("cost")
        val description = intent.getStringExtra("description")
        val image_url = intent.getStringExtra("image_url")
        Log.e("manan2",price.toString()+"")

        val data = ItemModel(name = name ?: "", description = description ?: "", image = image_url ?: "", price = price?.toInt() ?: 0)
        setContent {
            EPharmaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ItemDetailScreen(data)
                }
            }
        }
    }

    @Composable
    fun ItemDetailScreen(foodItem: ItemModel) {
        var quantity by remember { mutableStateOf(1) }
        var totalPrice by remember { mutableStateOf(foodItem.price) }

        var isSnackbarVisible by remember { mutableStateOf(false) }
        val context = LocalContext.current

        val uriHandler = LocalUriHandler.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Food Image
            Image(
                painter = rememberImagePainter(foodItem.image),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .border(1.dp, Color(android.graphics.Color.parseColor("#FF000000")), shape = RoundedCornerShape(8.dp)).padding(10.dp)
            )

            // Food Name
            Text(
                text = foodItem.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Food Description inside an outlined box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp),
                    ).border(2.dp, Color(android.graphics.Color.parseColor("#FF2DA62D")), shape = RoundedCornerShape(8.dp)).padding(10.dp)
            ) {
                Text(
                    text = foodItem.description,
                    style = MaterialTheme.typography.body1,
                )
            }

            // Food Price
            Text(
                text = foodItem.price.toString(),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Quantity Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        if (quantity > 1) {
                            quantity--
                            totalPrice = totalPrice - foodItem.price
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }

                Text(
                    text = "Quantity: $quantity",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 4.dp)
                )

                IconButton(
                    onClick = {
                        quantity++
                        totalPrice = totalPrice + foodItem.price
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

            // Total Price
            Text(
                text = totalPrice.toString(),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 4.dp)
            )


            Button(
                onClick = {
                    var uid = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
                    if (uid != null) {
                        val timestamp = System.currentTimeMillis()
                        val data = CartItem(name = foodItem.name, description = foodItem.description, quantity = quantity, price = foodItem.price, image_url = foodItem.image, item_id = timestamp.toString())
                        FirebaseFirestore
                            .getInstance()
                            .collection("User")
                            .document(uid)
                            .collection("Cart").document(timestamp.toString()).set(data).addOnCompleteListener {
                                val intent = Intent(this@ItemScreen, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }

                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#4CAF50")))

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to Cart", color = Color.White)
            }

            // Snackbar
            if (isSnackbarVisible) {
                // ... (existing code for snackbar)
            }
        }
    }

}
