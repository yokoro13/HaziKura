package com.example.dev.hazikura.fragment.management

import android.util.Log

/**
 * Created by yokoro
 */

class MyListItem {

    private var itemId: Int = 0
    var date: String
        private set
    var content: String
        private set
    lateinit var number: String
        private set
    var amount: String
        private set

    constructor(id: Int, date: String, content: String, amount: String) {
        this.itemId = id
        this.date = date
        this.content = content
        this.amount = amount
    }

    constructor(id: Int, date: String, content: String, number: String, amount: String) {
        this.itemId = id
        this.date = date
        this.content = content
        this.number = number
        this.amount = amount
    }

    fun getId(): Int {
        Log.d("取得ID：", itemId.toString())
        return itemId
    }

}
