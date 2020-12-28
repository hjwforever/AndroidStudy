package com.example.touchtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.touchtest.dao.UserDao
import com.example.touchtest.entity.User

/**
 * 数据库类，用于创建数据库及对所有数据库表进行操作
 * version表示当前的数据库版本号
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //所有数据库表操作类都放在这里
    abstract val userDao: UserDao

    companion object {
        private const val DATABASE_NAME = "sql"

        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) =
            instance ?: buildDatabase(context).also { instance = it }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
