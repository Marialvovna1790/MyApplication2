package com.example.myapplication.Models

data class Consumption(val black: Long, val red: Long, val yellow: Long, val blue: Long) {

    companion object {
        fun parseConsumption(map: Map<String, *>?): Consumption? {
            map ?: return null
            return Consumption(
                    black = map["black"] as Long? ?: 0,
                    yellow = map["yellow"] as Long? ?: 0,
                    blue = map["blue"] as Long? ?: 0,
                    red = map["red"] as? Long ?: 0
            )
        }
    }

}