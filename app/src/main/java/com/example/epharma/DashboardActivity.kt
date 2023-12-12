package com.example.epharma

import android.content.Intent
import android.util.Log

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.epharma.models.CartItem
import com.example.epharma.models.ItemModel
import com.example.epharma.screens.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker

class DashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }


    @Composable
    fun Navigation() {

        val navController = rememberNavController()

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Settings,
            NavigationItem.Notifications,
            NavigationItem.Account
        )
//    val profilePicture: Painter = painterResource(id = R.drawable.profile_picture)
//    val name = "Hi, " +"John Doe"


        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.background) {

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route


                    items.forEach {
                        BottomNavigationItem(selected = currentRoute == it.route,
                            label = {
                                Text(
                                    text = it.label,
                                    color = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = it.icons, contentDescription = null,
                                    tint = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                                )

                            },

                            onClick = {
                                if(currentRoute!=it.route){

                                    navController.graph?.startDestinationRoute?.let {
                                        navController.popBackStack(it,true)
                                    }

                                    navController.navigate(it.route){
                                        launchSingleTop = true
                                    }

                                }

                            })

                    }


                }


            }) {

            NavigationController(navController = navController)

        }

    }


    @Composable
    fun NavigationController(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavigationItem.Home.route) {

            composable(NavigationItem.Home.route) {
                Home(this@DashboardActivity)
            }

            composable(NavigationItem.Notifications.route) {
                CartScreen(this@DashboardActivity)
            }

            composable(NavigationItem.Settings.route) {
                val intent = Intent(this@DashboardActivity, MapsActivity::class.java)
                startActivity(intent)
            }

            composable(NavigationItem.Account.route) {
                Settings(this@DashboardActivity)
            }

        }

    }



    @Composable
    fun Notifications() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Notifications")

            }
        }
    }


}




