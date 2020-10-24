package com.geekydroid.bunkbook

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SlotDao {

    @Insert
    suspend fun add_attendance(slot: Slot)
}