package com.geekydroid.bunkbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Subject_view"

class Subject_view : AppCompatActivity() {

    //views
    private lateinit var progress: CircularProgressBar
    private lateinit var sub_name: TextView
    private lateinit var started: TextView
    private lateinit var tot_classes: TextView
    private lateinit var attended_ct: TextView
    private lateinit var bunked_ct: TextView
    private lateinit var attendance: TextView
    private lateinit var target: TextView
    private lateinit var present: Button
    private lateinit var bunked: Button
    private lateinit var percent:TextView

    //vars
    private lateinit var subject: Subject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_view)

        setUI()

        subject = intent.getParcelableExtra<Subject>("subject") as Subject

        set_values(subject)

        present.setOnClickListener {
            subject.attended += 1
            add_attendance("PRESENT")
        }

        bunked.setOnClickListener {
            subject.bunked += 1
            add_attendance("BUNKED")
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun add_attendance(status: String) {

        GlobalScope.launch {

            subject.total_classes += 1
            subject.attendance = ((subject.attended / subject.total_classes) * 100).toDouble()
            Log.d(TAG, "add_attendance: attended ${subject.attended}")
            Log.d(TAG, "add_attendance: bunked ${subject.bunked}")
            Log.d(TAG, "add_attendance: total ${subject.total_classes}")
            Log.d(TAG, "add_attendance: ${subject.attendance}")

            MyRoomDatabase.getInstance(applicationContext)!!.Subject_Dao()!!.update_subject(subject)
            var slot = Slot(
                0,
                subject.s_id,
                subject.p_id,
                subject.sub_name,
                SimpleDateFormat("dd/MM/yyyy").format(Date()),
                status
            )
            MyRoomDatabase.getInstance(applicationContext)!!.Slot_Dao()!!.add_attendance(slot)
            withContext(Dispatchers.Main)
            {
                set_values(subject)
            }

        }
        Toast.makeText(this, "Attendance added successfully", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun set_values(subject: Subject) {
        sub_name.text = subject.sub_name
        started.text = "${resources.getString(R.string.subviewT3)} ${subject.created}"
        tot_classes.text = "${resources.getString(R.string.subviewT6)} ${subject.total_classes}"
        attended_ct.text =
            "${resources.getString(R.string.subviewT4)} ${subject.attended}/${subject.total_classes}"
        bunked_ct.text =
            "${resources.getString(R.string.subviewT5)} ${subject.bunked}/${subject.total_classes}"

        percent.text = "${subject.attendance.toInt()}%"

        progress.apply {
            progress = 0f
            progressMax = 100f
            setProgressWithAnimation(subject.attendance.toInt().toFloat(),1500)
        }
    }

    private fun setUI() {
        progress = findViewById(R.id.progress)
        sub_name = findViewById(R.id.sub_name)
        started = findViewById(R.id.started)
        tot_classes = findViewById(R.id.tot_classes)
        attended_ct = findViewById(R.id.attended_count)
        bunked_ct = findViewById(R.id.bunked_count)
        attendance = findViewById(R.id.attendance)
        target = findViewById(R.id.target)
        percent = findViewById(R.id.percent)

        present = findViewById(R.id.present)
        bunked = findViewById(R.id.bunked)
    }
}