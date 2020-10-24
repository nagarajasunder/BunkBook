package com.geekydroid.bunkbook

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "Subject", foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("p_id"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["p_id"])]
)
data class Subject(
    @ColumnInfo(name = "s_id")
    @PrimaryKey(autoGenerate = true)
    var s_id: Int,

    @ColumnInfo(name = "p_id")
    var p_id: Int,

    @ColumnInfo(name = "subject_name")
    var sub_name: String,

    @ColumnInfo(name = "created_at")
    var created: String,

    @ColumnInfo(name = "total_classes")
    var total_classes: Double,

    @ColumnInfo(name = "attended")
    var attended: Double,

    @ColumnInfo(name = "bunked")
    var bunked: Double,

    @ColumnInfo(name = "attendance")
    var attendance: Double,
) : Parcelable