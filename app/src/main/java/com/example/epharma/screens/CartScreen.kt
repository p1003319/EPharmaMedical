package com.example.epharma.screens

import android.content.Context
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.epharma.models.CartItem
import com.example.epharma.models.ItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.*

var uid = ""
@Composable
fun CartScreen(context: Context) {
    val uid = FirebaseAuth.getInstance().getCurrentUser()?.uid
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(uid) {
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("User").document(uid).collection("Cart")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val items = mutableListOf<CartItem>()
                        for (document in task.result!!) {
                            val name = document.getString("name") ?: ""
                            val image = document.getString("image_url") ?: ""
                            val description = document.getString("description") ?: ""
                            val price = document.getLong("price")?.toInt() ?: 0
                            val quantity = document.getLong("quantity")?.toInt() ?: 0
                            val item_id = document.getString("item_id") ?: ""

                            val item = CartItem(name, price, description, image, quantity, item_id)
                            items.add(item)
                        }
                        cartItems = items
                    }
                    loading = false
                }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (loading) {
            // Display a loading indicator while data is being fetched
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            // Data has been fetched, display the EditProfileScreen1
            // Render the UI with the loaded cart items
            Cart(cartItems,context)
        }
    }


}



@Composable
fun Cart(cartItems2: List<CartItem>,context: Context) {
    var cartItems by remember { mutableStateOf<List<CartItem>>(cartItems2) }
    uid = FirebaseAuth.getInstance().getCurrentUser()?.getUid() ?: ""
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isNotEmpty()) {
// Inside the Box that contains LazyColumn
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(430.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(cartItems) { item ->
                        CartItem(
                            medicine = item,
                            onRemoveItem = {
                                FirebaseFirestore.getInstance().collection("User").document(uid).collection("Cart").document(item.item_id).delete()
                                val updatedCartItems = cartItems.toMutableList()
                                updatedCartItems.remove(item)
                                cartItems = updatedCartItems

                            }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            val totalPrice = cartItems.sumBy { it.price * it.quantity }
            Text(
                text = "Total: \$${String.format("%d", totalPrice)}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    FirebaseFirestore.getInstance().collection("User").document(uid)
                    .collection("Cart").get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result!!) {
                                val docRef = FirebaseFirestore.getInstance().collection("User").document(uid)
                                    .collection("Cart").document(document.id)
                                docRef.delete()
                                    .addOnSuccessListener {
                                        // Document successfully deleted
                                        cartItems = emptyList()
                                        Toast.makeText(context,"Items Have been Checkout",Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        // Handle errors here
                                    }
                            }
                        } else {

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
                Text("Checkout")
            }

            Spacer(modifier = Modifier.height(106.dp))


        } else {
            Text(
                text = "Your cart is empty",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}


data class Medicine(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    var quantity: Int
)
@Composable
fun CartItem(medicine: CartItem, onRemoveItem: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, Color(android.graphics.Color.parseColor("#2DA62D")))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Medicine Image
            Image(
                painter = rememberImagePainter(medicine.image_url),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Medicine Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.h6.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text ="Price: \$${String.format("%.2f", medicine.price.toDouble())}",
                    style = MaterialTheme.typography.body1.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Quantity: ${medicine.quantity}",
                    style = MaterialTheme.typography.body1.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 14.sp
                    )
                )

            }

            // Remove Button
            IconButton(
                onClick = {
                    onRemoveItem()
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}
