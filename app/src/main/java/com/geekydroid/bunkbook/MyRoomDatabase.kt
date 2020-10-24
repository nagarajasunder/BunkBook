package com.geekydroid.bunkbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Subject::class, Slot::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun User_Dao(): UserDao?
    abstract fun Subject_Dao(): SubjectDao?
    abstract fun Slot_Dao(): SlotDao?

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null
        fun getInstance(context: Context): MyRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MyRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MyRoomDatabase::class.java,
                            "bunkbook.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}