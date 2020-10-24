package com.geekydroid.bunkbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "Home"

private var user: User? = null

class Home : AppCompatActivity() {

    //views
    public lateinit var name: TextView
    private lateinit var subjects: TextView
    private lateinit var progressBar: CircularProgressBar
    private lateinit var percent: TextView
    private lateinit var add: Button
    private lateinit var view: Button
    private lateinit var attendance: TextView
    private lateinit var recylerview: RecyclerView

    //vars
    private var id: Int = 0
    private lateinit var user1: User
    private lateinit var list: List<Subject>
    private lateinit var adapter: Subject_Adapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUI()

        //getting the current profile
        get_current_profile()

        //adds new subject
        add.setOnClickListener {
            open_dialog()
        }
    }


    private fun get_subjects() {
        runBlocking {
            list = MyRoomDatabase.getInstance(this@Home)!!.Subject_Dao()!!
                .get_all_subjects(user!!.id)!!
            adapter = Subject_Adapter(list)
            recylerview.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun get_current_profile() {
        GlobalScope.launch(Dispatchers.IO) {
            val prefs = getSharedPreferences("CURRENT_PROFILE", MODE_PRIVATE)
            id = prefs.getInt("CURRENT_ID", 0)
            user = MyRoomDatabase.getInstance(applicationContext)!!.User_Dao()!!
                .get_specific_profile(id)
            Log.d(TAG, "get_current_profile: $user")
            withContext(Dispatchers.Main)
            {
                name.text = user!!.name
                progressBar.apply {
                    progress = 0f
                    progressMax = 100f
                    setProgressWithAnimation(user!!.overall_attendance.toFloat())
                }
                percent.text = "${user!!.overall_attendance}%"
                subjects.text = "${resources.getString(R.string.homT3)} ${user!!.subject}"
                attendance.text = "${resources.getString(R.string.homT4)} ${user!!.min_attendance}%"

                get_subjects()
            }
        }

    }

    private fun open_dialog() {
        var view =
            LayoutInflater.from(applicationContext).inflate(R.layout.add_subject_dialog, null)
        var name = view.findViewById<EditText>(R.id.name)
        var total_class = view.findViewById<EditText>(R.id.total)

        var dialog = AlertDialog.Builder(this)
            .setView(view)
            .setPositiveButton("Add", null)
            .setNegativeButton("Cancel", null)
            .show()

        var button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            var sub_name = name.text.toString()
            var tot = 0
            if (!total_class.text.trim().isEmpty()) {
                tot = Integer.parseInt(total_class.text.toString())
            }
            if (sub_name.trim().isEmpty()) {
                Toast.makeText(this@Home, "Please enter a subject name", Toast.LENGTH_SHORT).show()
                name.requestFocus()
            } else {
                //adds news subject
                add_new_subject(dialog, sub_name, tot)
            }
        }

        dialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun add_new_subject(dialog: AlertDialog?, sub_name: String, tot: Int) {

        val format = SimpleDateFormat("dd/MM/yyyy")
        val sub =
            Subject(0, user!!.id, sub_name, format.format(Date()), tot.toDouble(), 0.0, 0.0, 0.0)

        GlobalScope.launch(Dispatchers.IO) {
            MyRoomDatabase.getInstance(applicationContext)!!.Subject_Dao()!!.add_new_subject(sub)
            withContext(Dispatchers.Main)
            {
                dialog!!.dismiss()
                Toast.makeText(
                    applicationContext,
                    "New subject added successfully",
                    Toast.LENGTH_SHORT
                ).show()

                get_subjects()
            }
        }
    }


    private fun setUI() {
        name = findViewById(R.id.name)
        subjects = findViewById(R.id.subjects)
        progressBar = findViewById(R.id.progress_bar)
        percent = findViewById(R.id.percent)
        attendance = findViewById(R.id.attendance)
        add = findViewById(R.id.add)
        view = findViewById(R.id.view)

        list = ArrayList()
        recylerview = findViewById(R.id.recycler_view)
        recylerview.setHasFixedSize(true)
        recylerview.layoutManager = LinearLayoutManager(this)
    }
}