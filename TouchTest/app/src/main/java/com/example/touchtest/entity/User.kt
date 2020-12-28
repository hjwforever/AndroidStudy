package com.example.touchtest.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  用户表，包含自动生成值的主键id字段，用户名字段及密码字段
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey (autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "user_password") val userPassword: String?
)
