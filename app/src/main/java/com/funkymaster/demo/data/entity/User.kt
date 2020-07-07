package com.funkymaster.demo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    var email:String,

    var role:String,

    var status:String,

    var shop_id:String,

    var role_id: Int

)