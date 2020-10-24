package com.geekydroid.bunkbook

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "Slot"
)
class Slot(
    @ColumnInfo(name = "sl_id")
    @PrimaryKey(autoGenerate = true)
    var sl_id: Int,
    @ColumnInfo(name = "sub_id")
    var s_id: Int,
    @ColumnInfo(name = "p_id")
    var p_id: Int,
    @ColumnInfo(name = "s_name")
    var s_name: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "status")
    var status: String
) : Parcelable