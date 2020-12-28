package com.example.touchtest.dao

import androidx.room.*
import com.example.touchtest.entity.User


/**
 * 用户数据库表操作类
 */
@Dao
interface UserDao {
    // 查找所有用户
    @Query("SELECT * FROM users")
    fun findAll(): List<User>

    // 通过uid查找用户
    @Query("SELECT * FROM users WHERE uid IN (:userIds) LIMIT 1")
    fun findByUId(userIds: IntArray): List<User>

    // 模糊查找
    @Query("SELECT * FROM users WHERE user_name LIKE :username")
    fun findByUsername(username: String): User

    // 新增用户
    @Insert
    fun add(vararg users: User)

    // 更新用户
    @Update
    fun update(vararg user: User)

    // 删除用户
    @Delete
    fun delete(user: User)

    //删除所有用户
    @Query("DELETE FROM users")
    fun deleteAll()
}