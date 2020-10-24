package com.geekydroid.bunkbook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var prefs = getSharedPreferences("first_start", MODE_PRIVATE)
        var first_start = prefs.getBoolean("first_profile", true)
        Handler().postDelayed({
            if (first_start) {
                startActivity(Intent(this@MainActivity, Profile::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, Home::class.java))
                finish()
            }
        }, 1000)
    }
}