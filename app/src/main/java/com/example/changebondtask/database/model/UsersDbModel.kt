package com.example.changebondtask.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersDbModel (
    @PrimaryKey()
    var id : Int? = null ,
    var name : String ,
    var email : String ,
    var gender : String ,
    var comment : String? ="",
    var status : String)
