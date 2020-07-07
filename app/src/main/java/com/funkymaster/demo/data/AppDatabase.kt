package com.funkymaster.demo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.funkymaster.demo.data.dao.FailedUserDao
import com.funkymaster.demo.data.dao.UserDao
import com.funkymaster.demo.data.entity.FailedLogin
import com.funkymaster.demo.data.entity.User
import com.funkymaster.demo.utilities.Converters

@Database(entities = [User::class, FailedLogin::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun useRDao(): UserDao
    abstract fun failedUserDaO(): FailedUserDao


    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}