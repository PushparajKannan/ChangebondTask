package com.example.changebondtask.view.model

import android.os.Parcelable
import com.example.changebondtask.database.model.UsersDbModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UsersListResponseModel(
    @SerializedName("data")
    var userList : List<Users> = emptyList()
)


@Parcelize
data class Users(
    @SerializedName("id")
    var id : Int? ,
    @SerializedName("name")
    var name : String ,
    @SerializedName("email")
    var email : String ,
    @SerializedName("gender")
    var gender : String ,
    var comment : String? ="" ,
    @SerializedName("status")
    var status : String
    ) : Parcelable


fun List<Users>.toUserDb() = map {
    UsersDbModel(it.id!!,it.name,it.email,it.gender,it.comment,it.status)
}.toList()

fun List<UsersDbModel>.toUserModel() = map {
    Users(it.id,it.name,it.email,it.gender,it.comment,it.status)
}.toList()

fun Users.toUserDbModel() = UsersDbModel(this.id,this.name,this.email,this.gender,this.comment,this.status)