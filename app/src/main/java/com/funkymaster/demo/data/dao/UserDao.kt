package com.funkymaster.demo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.funkymaster.demo.data.entity.User


@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

}