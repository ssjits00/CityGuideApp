package com.example.guideme
 class User {
    var name: String? = null
    var address: String? = null
    var email: String? = null
    var phoneNo: String? = null
    var password: String? = null
     var confirmPassword: String? = null

    constructor(
        name: String?,
        address: String?,
        email: String?,
        phoneNo: String?,
        password: String?,
        confirmPassword: String
    ) {
        this.name = name
        this.address = address
        this.email = email
        this.phoneNo = phoneNo
        this.password = password
        this.confirmPassword = confirmPassword

    }
}