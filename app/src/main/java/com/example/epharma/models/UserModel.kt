package com.example.epharma.models

data class UserModel(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val image_url : String,
    val user_id : String
){
    constructor() : this("", "", "", "", "" , "")
}
