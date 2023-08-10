package com.hftamayo.kotlinretrievefromfbase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var firstName: String? = null, var lastName: String? = null, var age: Int? = null){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.

}
