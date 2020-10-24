package com.geekydroid.bunkbook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubjectDao {

    @Insert
    suspend fun add_new_subject(subject: Subject)

    @Query("SELECT * FROM Subject WHERE p_id LIKE :id")
    suspend fun get_all_subjects(id: Int): List<Subject>?

    @Query("SELECT * FROM Subject WHERE s_id LIKE :id")
    suspend fun get_specific_subject(id: Int): Subject?

    @Update
    suspend fun update_subject(subject: Subject)
}