package com.funkymaster.demo.data.entity



import androidx.room.*


@Entity(tableName = "failed_login")
data class FailedLogin(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var email: String,
    @ColumnInfo(name = "timestamp") val timestamp: String?

)


