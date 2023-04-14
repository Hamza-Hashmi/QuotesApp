package com.example.quotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.quotesapp.databinding.ActivityMainBinding
import com.example.quotesapp.models.Quotes
import com.example.quotesapp.models.TTSHelper
import com.example.quotesapp.viewModels.QuotesViewModel
import com.example.quotesapp.viewModels.QuotesViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:QuotesViewModel
    private lateinit var textToSpeak:TTSHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // intialization ViewModel
        viewModel = ViewModelProvider(this,QuotesViewModelFactory(application)).get(QuotesViewModel::class.java)

        // intialization Text To Speak
        textToSpeak = TTSHelper()
        textToSpeak.ttsInitialization(this@MainActivity)

        // Set up first quote
        setQuote(viewModel.getQuote())


        binding.apply {
            btnNext.setOnClickListener{
                nextQuote()
            }
            btnPrevious.setOnClickListener{
                previousQuote()
            }
            btnShare.setOnClickListener {
                onShare()
            }

            btnSpeak.setOnClickListener {
                if (!textToSpeak.isSpeaking()){
                    textToSpeak.speakText(viewModel.getQuote().text)
                }
            }
        }
    }

    private fun setQuote(quote: Quotes){
        binding.apply {
            quoteText.text = quote.text
            autoherName.text = quote.author
        }
    }

    private fun nextQuote(){
        setQuote(viewModel.nextQuote())
    }

    private fun previousQuote(){
        setQuote(viewModel.previousQuote())
    }

    private fun onShare(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plan"
        intent.putExtra(Intent.EXTRA_TEXT,viewModel.getQuote().text)
        startActivity(intent)
    }

    override fun onStop() {
        stopSpeaking()
        super.onStop()
    }

    private fun stopSpeaking() {
        if (textToSpeak.isSpeaking()){
            textToSpeak.stopSpeaker()
        }
    }

    override fun onPause() {
        stopSpeaking()
        super.onPause()
    }

    override fun onDestroy() {
        stopSpeaking()
        super.onDestroy()
    }
}