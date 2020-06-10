package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import java.text.SimpleDateFormat
import java.util.*

class ActivityEnterDate: AppCompatActivity(R.layout.activity_enter_date) {

    private var start: Calendar? = null
    private var end: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.start).setOnClickListener { view: View ->
            MaterialDialog(this).show {
                datePicker(requireFutureDate = false) { dialog, datetime: Calendar ->
                    start = datetime
                    (view as TextView).text = datetime.formatDate()
                }
            }
        }

        findViewById<TextView>(R.id.end).setOnClickListener { view ->
            MaterialDialog(this).show {
                datePicker(requireFutureDate = false) { dialog, datetime: Calendar ->
                    end = datetime
                    (view as TextView).text = datetime.formatDate()
                }
            }
        }

        findViewById<TextView>(R.id.run).setOnClickListener {
            val clazz = intent.getSerializableExtra("clazz") as Class<*>
            startActivity(Intent(this, clazz))
        }
    }

    fun Calendar.formatDate(): String {
        return SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.time)
    }
}