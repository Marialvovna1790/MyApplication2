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
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random

class ActivityPrediction : AppCompatActivity(R.layout.activity_prediction) {

    val colors = intArrayOf(BLACK, CYAN, MAGENTA, BLUE)

    lateinit var colorSelected: String
    lateinit var dep: String
    lateinit var month: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.color).setOnClickListener { view: View ->
            MaterialDialog(this).show {
                title(text = "Выберите цвет")
                colorChooser(colors) { dialog, color ->
                    colorSelected = color.toString()
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
                        waitForPositiveButton = true, selection = { _, _, t -> (
                        view as TextView).text = t
                    dep = t.toString()
                })

                positiveButton(text= "OK")
            }
        }

        findViewById<TextView>(R.id.month).setOnClickListener { view: View ->
            MaterialDialog(this).show {
                title(text = "Выберите месяц замены")
                listItems(items = listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                        "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"),
                        waitForPositiveButton = true, selection = { _, _, t ->
                    (view as TextView).text = t
                    month = t.toString()
                })

                positiveButton(text= "OK")
            }
        }

        findViewById<TextView>(R.id.run).setOnClickListener {
            val dialog: android.app.AlertDialog? = SpotsDialog.Builder()
                    .setContext(this)
                    .setMessage("Расчет прогнозируемой даты...")
                    .setCancelable(false)
                    .build()
            thread {
                dialog!!.show()
                val client = Socket("127.0.0.1", 9999)
                client.outputStream.write("$colorSelected,$dep,$month".toByteArray())
                val days = Scanner(client.getInputStream()).nextLong()
                client.close()
                Handler(Looper.getMainLooper()).post {
                    dialog.dismiss()
                findViewById<TextView>(R.id.prediction).text = days.toString()
                }
            }
//                findViewById<TextView>(R.id.prediction).text = Random.nextInt(10, 40).toString()
        }
    }

    fun Calendar.formatDate(): String {
        return SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.time)
    }
}