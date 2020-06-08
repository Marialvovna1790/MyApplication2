package com.example.myapplication.Models

import com.example.myapplication.Models.Consumption.Companion.parseConsumption

@Suppress("UNCHECKED_CAST")
data class Printer(val name: String,
                   val ip: String,
                   val model: String,
                   val all_sheets: Long,
                   val colored_sheets: Long,
                   val jams_amount: Long,
                   val jams_percent: String,
                   val printed_amount: Long,
                   val consumption: Consumption?,
                   val consumptionCurrent: Consumption?) {

    companion object {
        fun from(map: Map<String, *>): Printer {
            return Printer(
                    name = map["name"] as? String ?: "",
                    ip = map["ip"] as? String ?: "",
                    model = map["printer_model"] as? String ?: "",
                    all_sheets = map["all_sheets"] as Long? ?: 0,
                    colored_sheets = map["lists_color_amount"] as Long? ?: 0,
                    jams_amount = map["jams_amnt"] as Long? ?: 0,
                    printed_amount = map["printed_amnt"] as Long? ?: 0,
                    jams_percent = map["percent"] as String? ?: "",
                    consumption = parseConsumption(map["current_cartridge_status"] as? Map<String, *>),
                    consumptionCurrent = parseConsumption(map["consumption"] as? Map<String, *>)
            )
        }
    }
}

