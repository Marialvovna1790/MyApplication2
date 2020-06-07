package com.example.myapplication.Models

@Suppress("UNCHECKED_CAST")
data class Printer(val name: String,
                   val ip: String,
                   val model: String,
                   val all_sheets: Long,
                   val colored_sheets: Long,
                   val consumption: Consumption?,
                   val consumptionCurrent: Consumption?) {

    companion object {
        fun from(map: Map<String, *>): Printer {
            return Printer(
                    name = map["name"] as? String ?: "",
                    ip = map["ip"] as? String ?: "",
                    model = map["model"] as? String ?: "",
                    all_sheets = map["all_sheets"] as Long? ?: 0,
                    colored_sheets = map["lists_color_amount"] as Long? ?: 0,
                    consumption = parseConsumption(map["current_cartridge_status"] as? Map<String, *>),
                    consumptionCurrent = parseConsumption(map["consumption"] as? Map<String, *>)
            )
        }
    }
}

fun parseConsumption(map: Map<String, *>?): Consumption? {
    map ?: return null
    return Consumption(
            black = map["black"] as Long? ?: 0,
            yellow = map["yellow"] as Long? ?: 0,
            blue = map["blue"] as Long? ?: 0,
            red = map["red"] as? Long ?: 0
    )
}

data class Consumption(val black: Long, val red: Long, val yellow: Long, val blue: Long)