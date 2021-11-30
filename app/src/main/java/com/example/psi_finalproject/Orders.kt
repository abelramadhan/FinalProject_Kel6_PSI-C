package com.example.psi_finalproject

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

data class Orders(
    var productID:String = "",
    var productTitle:String = "",
    var size:String = "",
    var date:String = "") {

    fun Orders() {

    }

    fun Orders(userID: String, productTitle: String, size: String, date: String) {
        this.productID = productID
        this.productTitle = productTitle
        this.size = size
        this.date = date
    }

}
