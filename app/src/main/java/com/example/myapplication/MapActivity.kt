package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        findViewById<View>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, ActivityEnterDate::class.java).apply {
                putExtra(CLAZZ, PrintersActivity::class.java)
            })
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, ActivityEnterDate::class.java).apply {
                putExtra(CLAZZ, ModelsActivity::class.java)
            })
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, ActivityPrediction::class.java))
        }

        findViewById<View>(R.id.button6).setOnClickListener {
            startActivity(Intent(this, ActivityEnterDate::class.java).apply {
                putExtra(CLAZZ, JamsActivity::class.java)
            })
        }

        findViewById<View>(R.id.exit).setOnClickListener {
            MainActivity.setAuthorized(false, this)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    companion object {
        private const val CLAZZ = "clazz"
    }
}