package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.myapplication.Models.Printer
import com.google.firebase.database.*
import view

class BaseListActivity : AppCompatActivity() {

    private lateinit var progress: ProgressBar
    private val listAdapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)
        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        progress = findViewById(R.id.progress_circular)
        doLoad()
    }

    private fun doLoad() {
        val db = FirebaseDatabase.getInstance()
        db.reference.child("Printers").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("debug", "added $p0")
                val type = object : GenericTypeIndicator<ArrayList<HashMap<String, *>>>() {}
                val value = p0.getValue(type)

                value?.mapTo(listAdapter.dataSet) {
                    Printer.from(it)
                }
                progress.visibility = View.GONE
                listAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun onDataReady() {

    }


    private class PrinterViewHolder(viewGroup: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.printer_layout, viewGroup, false)), Bindable<Printer> {
        val name = itemView.findViewById<TextView>(R.id.printer_name)
        val ip = itemView.findViewById<TextView>(R.id.printer_ip)
        val model = itemView.findViewById<TextView>(R.id.printer_model)

        override var item: Printer? = null

        init {
            itemView.setOnClickListener {
                val dialog = MaterialDialog(itemView.context)
                        .customView(R.layout.printer_info_layout, scrollable = true)
                val customView = dialog.getCustomView()
                bindPrinterInfo(customView, item)
                dialog.show()
            }
        }

        private fun bindPrinterInfo(customView: View, item: Printer?) {
            item ?: return
            with(customView) {
                view<TextView>(R.id.name).text = "Имя принтера: ${item.name}"
                view<TextView>(R.id.model).text = "Модель: ${item.model}"
                view<TextView>(R.id.ip).text = "IP: ${item.ip}"
                view<TextView>(R.id.lists_amount).text = "Общее кол-во листов: ${item.all_sheets}"
                view<TextView>(R.id.lists_amount_colored).text = "Общее кол-во листов: ${item.colored_sheets}"

                fun bindColors(black: Pair<ImageView, Long>,
                               red: Pair<ImageView, Long>,
                               blue: Pair<ImageView, Long>,
                               yellow: Pair<ImageView, Long>) {
                    black.first.background = CircleDrawable(Color.BLACK, black.second)
                    blue.first.background = CircleDrawable(Color.BLUE, blue.second)
                    red.first.background = CircleDrawable(Color.RED, red.second)
                    yellow.first.background = CircleDrawable(Color.YELLOW, yellow.second)
                }
                item.consumption ?: return
                item.consumptionCurrent ?: return
                bindColors(view<ImageView>(R.id.black) to item.consumption.black,
                        view<ImageView>(R.id.red) to item.consumption.red,
                        view<ImageView>(R.id.blue) to item.consumption.blue,
                        view<ImageView>(R.id.yellow) to item.consumption.yellow)

                bindColors(view<ImageView>(R.id.black_now) to item.consumptionCurrent.black,
                        view<ImageView>(R.id.red_now) to item.consumptionCurrent.red,
                        view<ImageView>(R.id.blue_now) to item.consumptionCurrent.blue,
                        view<ImageView>(R.id.yellow_now) to item.consumptionCurrent.yellow)
            }
        }


        override fun onBind(p: Printer) {
            name.text = p.name
            model.text = p.model
            ip.text = p.ip
        }
    }


    private class Adapter : RecyclerView.Adapter<PrinterViewHolder>() {

        val dataSet = mutableListOf<Printer>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrinterViewHolder {
            return PrinterViewHolder(parent)
        }

        override fun getItemCount() = dataSet.size

        override fun onBindViewHolder(holder: PrinterViewHolder, position: Int) {
            holder.bind(dataSet[position])
        }
    }
}