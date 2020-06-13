package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.list.listItems
import dmax.dialog.SpotsDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class ActivityPrediction : AppCompatActivity(R.layout.activity_prediction) {

    val colors = intArrayOf(BLACK, CYAN, MAGENTA, BLUE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.color).setOnClickListener { view: View ->
            MaterialDialog(this).show {
                title(text = "Выберите цвет")
                colorChooser(colors) { dialog, color ->
                    view.background = ColorDrawable(color)
                    if (color == Color.BLACK) {
                        (view as TextView).setTextColor(Color.WHITE)
                    } else {
                        (view as TextView).setTextColor(Color.BLACK)
                    }
                }
                positiveButton(text= "OK")
            }
        }

        findViewById<TextView>(R.id.department).setOnClickListener { view ->
            MaterialDialog(this).show {
                title(text = "Выберите отдел")
                listItems(items = listOf("Общий отдел", "Отдел безопасности" , "Отдел по работе с клиентами", "Технический отдел", "Гендиректор"),
                        waitForPositiveButton = true, selection = { _, _, t -> (view as TextView).text = t })

                positiveButton(text= "OK")
            }
        }

        findViewById<TextView>(R.id.month).setOnClickListener { view: View ->
            MaterialDialog(this).show {
                title(text = "Выберите месяц замены")
                listItems(items = listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                        "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"),
                        waitForPositiveButton = true, selection = { _, _, t -> (view as TextView).text = t })

                positiveButton(text= "OK")
            }
        }

        findViewById<TextView>(R.id.run).setOnClickListener {
            val dialog: android.app.AlertDialog? = SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Расчет прогнозируемой даты...")
                    .setCancelable(false)
                    .build()
            dialog!!.show()
            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                findViewById<TextView>(R.id.prediction).text = Random.nextInt(10, 40).toString()
            }, 3000)

        }
    }

    fun Calendar.formatDate(): String {
        return SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.time)
    }
}