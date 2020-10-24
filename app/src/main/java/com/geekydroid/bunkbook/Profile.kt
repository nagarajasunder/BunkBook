package com.geekydroid.bunkbook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "Profile"

class Profile : AppCompatActivity() {

    private lateinit var progress: ProgressBar
    private lateinit var seekbar: SeekBar
    private lateinit var progress_text: TextView
    private lateinit var create: Button
    private lateinit var name: TextInputLayout
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setUI()
        create.setOnClickListener {
            //validates the input fields given by the user
            if (validate()) {
                //validation success creates new profile
                create_new_profile()
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                progress.setProgress(seekBar!!.progress)
                progress_text.setText("${seekBar.progress}%")
                count = seekBar.progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }


    private fun create_new_profile() {
        var name = name.editText!!.text.toString()

        var user = User(0, name, count, 0, 0)

        //changing the first start to false
        val prefs = getSharedPreferences("first_start", MODE_PRIVATE)
        var editor = prefs.edit()
        editor.putBoolean("first_profile", false)
        editor.apply()

            GlobalScope.launch(Dispatchers.IO) {
                MyRoomDatabase.getInstance(applicationContext)!!.User_Dao()!!
                    .create_profile(user)

                user =
                    MyRoomDatabase.getInstance(applicationContext)!!.User_Dao()!!.get_latest_profile()
                val prefs2 = getSharedPreferences("CURRENT_PROFILE", MODE_PRIVATE)
                editor = prefs2.edit()
                editor.putInt("CURRENT_ID", user.id)
                editor.apply()

            }
        Toast.makeText(this@Profile, "Profile created successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, Home::class.java))
        finish()
    }

    private fun validate(): Boolean {

        if (name.editText!!.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Please enter a name", Toast.LENGTH_SHORT).show()
            name.editText!!.requestFocus()
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setUI() {
        progress = findViewById(R.id.progress_circular)
        seekbar = findViewById(R.id.seekbar);
        progress_text = findViewById(R.id.progress_text)
        progress_text.setText("${seekbar.progress}%")
        count = seekbar.progress
        create = findViewById(R.id.create)
        name = findViewById(R.id.name)
    }
}

