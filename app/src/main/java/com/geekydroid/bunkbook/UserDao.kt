package com.geekydroid.bunkbook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun create_profile(user: User)

    @Query("SELECT * FROM Users")
    suspend fun get_all_profiles(): List<User>

    @Query("SELECT * FROM Users LIMIT 1")
    suspend fun get_latest_profile():User

    @Query("SELECT * FROM Users WHERE id LIKE :id")
    suspend fun get_specific_profile(id:Int):User
}
