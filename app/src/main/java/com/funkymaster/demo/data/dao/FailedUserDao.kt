package com.funkymaster.demo.data.dao




import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.funkymaster.demo.data.entity.FailedLogin


@Dao
interface FailedUserDao {

    @Insert
    fun insertFailedUser(failedUser: FailedLogin)

    @Query("SELECT * FROM user")
    fun getFailedUsers(): List<FailedLogin>

}