package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        findViewById<View>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, BaseListActivity::class.java))
        }

        findViewById<View>(R.id.exit).setOnClickListener {
            MainActivity.setAuthorized(false, this)
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}