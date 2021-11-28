package com.example.psi_finalproject

data class User(
    var username:String="",
    var email:String="",
    var pPicture_url:String=""
    ) {

    fun User() {

    }

    fun User(username: String,email: String) {
        this.username = username
        this.email = email
        this.pPicture_url = ""
    }

}
