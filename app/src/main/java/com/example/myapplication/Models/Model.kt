package com.example.myapplication.Models

import com.example.myapplication.Models.Consumption.Companion.parseConsumption

@Suppress("UNCHECKED_CAST")
data class Model(val name: String,
                 val all_sheets: Long,
                 val amount: Long,
                 val consumption: Consumption?) {

    companion object {
        fun from(map: Map<String, *>): Model {
            return Model(
                    name = map["printer_model"] as? String ?: "",
                    amount = map["number_of_printers"] as? Long ?: 0,
                    all_sheets = map["all_sheets"] as Long? ?: 0,
                    consumption = parseConsumption(map["consumption"] as? Map<String, *>)
            )
        }
    }
}


