package com.geekydroid.bunkbook

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "Users",
)
class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "min_attendance")
    var min_attendance: Int,
    @ColumnInfo(name = "subjects")
    var subject: Int,
    @ColumnInfo(name = "overall")
    var overall_attendance: Int
) : Parcelable