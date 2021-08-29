package com.example.guideme

class BusinessUser{
    var firstName : String? = null
    var lastName : String? = null
    var shopName: String? = null
    var email : String? = null
    var openTime : String? = null
     var closeTime : String? = null
     var address : String? = null
     var servicesOffered : String? = null
     var mobileNumber : String? = null
     var pincode : String? = null
     var city : String? = null
     var state : String? = null
     var country : String? = null
     var password : String? = null

    constructor(
        firstName : String?,
        lastName : String?,
        shopName: String?,
        email : String?,
        openTime : String?,
         closeTime : String?,
         address : String?,
        servicesOffered : String?,
         mobileNumber : String?,
         pincode : String?,
         city : String?,
         state : String?,
         country : String?,
         password : String?

    )
    {
        this.firstName= firstName
        this.lastName= lastName
        this.shopName= shopName
        this.email= email
        this.openTime=openTime
        this.closeTime= closeTime

        this.address= address
        this.servicesOffered = servicesOffered
        this.mobileNumber=  mobileNumber
        this.pincode= pincode
        this.city= city
        this.state= state
        this.country= country

        this.password= password
    }


}

