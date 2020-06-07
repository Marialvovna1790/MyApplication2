package com.example.myapplication

interface Bindable<T> {

    var item: T?

    fun bind(item: T) {
        this.item = item
        onBind(item)
    }

    fun onBind(item: T)
}