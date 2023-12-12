package com.example.epharma.models

import androidx.compose.ui.graphics.painter.Painter

class ItemModel(
    var name : String,
    var image : String,
    var description : String,
    var price : Int,
    ) {

    constructor() : this("", "", "", 0)
}