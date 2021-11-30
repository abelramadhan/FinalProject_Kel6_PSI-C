package com.example.psi_finalproject

data class Product(
    var id:String="",
    var title:String="",
    var designer:String="",
    var price:String="",
    var pic_url:String="") {

    fun Product() {

    }

    fun Product(id: String, title: String, designer: String, price: String, pic_url: String) {
        this.id = id
        this.title = title
        this.designer = designer
        this.price = price
        this.pic_url = pic_url
    }

}
