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

class ModelsActivity : BaseListActivity() {

    class ModelViewHolder(viewGroup: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.model_layout, viewGroup, false)),
            Bindable<Model> {
        val model = itemView.findViewById<TextView>(R.id.printer_model)

        override var item: Model? = null

        init {
            itemView.setOnClickListener {
                val dialog = MaterialDialog(itemView.context)
                        .title(text = "Информация о модели")
                        .customView(R.layout.model_info_layout, scrollable = true)
                val customView = dialog.getCustomView()
                bindModelInfo(customView, item)
                dialog.show()
            }
        }

        @SuppressLint("SetTextI18n")
        private fun bindModelInfo(customView: View, item: Model?) {
            item ?: return
            with(customView) {
                view<TextView>(R.id.name).text = "Модель: ${item.name}"
                view<TextView>(R.id.amount).text = "Общее кол-во принтеров: ${item.amount}"
                view<TextView>(R.id.lists_amount).text = "Кол-во листов: ${item.all_sheets}"

                item.consumption ?: return
                bindColors(view<ImageView>(R.id.black) to item.consumption.black,
                        view<ImageView>(R.id.red) to item.consumption.red,
                        view<ImageView>(R.id.blue) to item.consumption.blue,
                        view<ImageView>(R.id.yellow) to item.consumption.yellow)
            }
        }


        override fun onBind(p: Model) {
            model.text = p.name
        }
    }

    override fun createDataLoader() = object : DataLoader<Model>("Models") {
        override var adapter: BaseAdapter<*, Model> = ModelsAdapter()

        override fun mapper(map: HashMap<String, *>) = Model.from(map)
    }

    class ModelsAdapter : BaseAdapter<ModelViewHolder, Model>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
            return ModelViewHolder(parent)
        }
    }
}