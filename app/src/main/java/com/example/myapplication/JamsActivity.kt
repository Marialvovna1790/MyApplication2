package com.example.myapplication

import android.annotation.SuppressLint
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
import com.example.myapplication.Models.Model
import com.example.myapplication.Models.Printer
import view

class JamsActivity : BaseListActivity() {

    class JamsViewHolder(viewGroup: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.printer_layout, viewGroup, false)),
            Bindable<Printer> {
        val name = itemView.findViewById<TextView>(R.id.printer_name)
        val ip = itemView.findViewById<TextView>(R.id.printer_ip)
        val model = itemView.findViewById<TextView>(R.id.printer_model)

        override var item: Printer? = null

        init {
            itemView.setOnClickListener {
                val dialog = MaterialDialog(itemView.context)
                        .title(text = "Информация о замятиях")
                        .customView(R.layout.printer_info_layout, scrollable = true)
                val customView = dialog.getCustomView()
                bindModelInfo(customView, item)
                dialog.show()
            }
        }

        @SuppressLint("SetTextI18n")
        private fun bindModelInfo(customView: View, item: Printer?) {
            item ?: return
            with(customView) {
                view<TextView>(R.id.name).text = "Имя принтера: ${item.name}"
                view<TextView>(R.id.model).text = "Модель: ${item.model}"
                view<TextView>(R.id.ip).text = "IP: ${item.ip}"
                view<TextView>(R.id.jams_amnt).text = "Количество замятий: ${item.jams_amount}"
                view<TextView>(R.id.lists_amount).text = "Процент замятий: ${item.jams_percent}%"
                view<TextView>(R.id.lists_amount_colored).text = "Отпечатано листов: ${item.printed_amount}"

                hideViews(customView, R.id.black,
                        R.id.black_now,
                        R.id.yellow,
                        R.id.yellow_now,
                        R.id.blue,
                        R.id.blue_now,
                        R.id.cart,
                        R.id.current_state
                )
            }
        }

        fun hideViews(view: View, vararg ids: Int) {
            ids.forEach {
                view.view<View>(it).visibility = View.GONE
            }
        }


        override fun onBind(p: Printer) {
            name.text = p.name
            model.text = p.model
            ip.text = p.ip
        }
    }

    override fun createDataLoader() = object : DataLoader<Printer>("jams") {
        override var adapter: BaseAdapter<*, Printer> = JamsAdapter()

        override fun mapper(map: HashMap<String, *>) = Printer.from(map)
    }

    class JamsAdapter : BaseAdapter<JamsViewHolder, Printer>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JamsViewHolder {
            return JamsViewHolder(parent)
        }
    }
}