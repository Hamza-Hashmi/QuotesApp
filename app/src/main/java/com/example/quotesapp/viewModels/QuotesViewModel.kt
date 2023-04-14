package com.example.quotesapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.quotesapp.models.Quotes
import com.google.gson.Gson

class QuotesViewModel(val context: Context) : ViewModel() {

     private var quoteList:Array<Quotes> = emptyArray<Quotes>()
     private var index = 0

    init {
        quoteList = loadQuotesFromAssets()
    }
    private fun loadQuotesFromAssets() : Array<Quotes>{

        // load json from assets
        val inputStrem = context.assets.open("quotes.json")
        val size:Int = inputStrem.available()
        val buffer = ByteArray(size)
        inputStrem.read(buffer)
        inputStrem.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json,Array<Quotes>::class.java)

    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[++index]

    fun previousQuote() = quoteList[--index]
}