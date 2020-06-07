package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bindColors
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.myapplication.Models.Printer
import view

class PrintersActivity : BaseListActivity() {

    class PrinterViewHolder(viewGroup: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.printer_layout, viewGroup, false)),
            Bindable<Printer> {
        val name = itemView.findViewById<TextView>(R.id.printer_name)
        val ip = itemView.findViewById<TextView>(R.id.printer_ip)
        val model = itemView.findViewById<TextView>(R.id.printer_model)

        override var item: Printer? = null

        init {
            itemView.setOnClickListener {
                val dialog = MaterialDialog(itemView.context)
                        .title(text = "Информация о принтере")
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

    override fun createDataLoader() = object : DataLoader<Printer>("Printers") {
        override var adapter: BaseAdapter<*, Printer> = PrintersAdapter()

        override fun mapper(map: HashMap<String, *>) = Printer.from(map)
    }

    class PrintersAdapter : BaseAdapter<PrinterViewHolder, Printer>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrinterViewHolder {
            return PrinterViewHolder(parent)
        }
    }
}