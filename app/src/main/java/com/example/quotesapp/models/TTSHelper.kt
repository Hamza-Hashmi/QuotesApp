package com.example.quotesapp.models

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class TTSHelper : TextToSpeech.OnInitListener {
    private var textForSpeak: String = ""

    companion object {

        var pitch = 1f
        var speed = 1.0f
        lateinit var tts: TextToSpeech
    }

    fun ttsInitialization(context: Context) {
        tts = TextToSpeech(context, this@TTSHelper)
    }

    fun speakText(textForSpeak: String/*, outputCode: String?*/) {
        stopSpeaker()
        val code = "en"
     /*   outputCode?.let {
            code = it
            if (it.contains("-")) {
                code = it.split("-")[0]
            }
        }*/

        this.textForSpeak = textForSpeak
        tts.language = Locale(code)

        tts.setPitch(
            pitch
        )
        tts.setSpeechRate(
            speed
        )
        tts.speak(textForSpeak, TextToSpeech.QUEUE_ADD, null, "MyUniqueUtteranceId")
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
            }

            override fun onDone(utteranceId: String?) {
                onDoneSpeaking?.invoke()
            }

            override fun onError(utteranceId: String?) {
                onErrorSpeaking?.invoke()
            }
        })
    }

    var onDoneSpeaking: (() -> (Unit))? = null
    var onErrorSpeaking: (() -> (Unit))? = null

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            tts.language = Locale(
                "en"
            )
        }
    }

    fun stopSpeaker(shutDown: Boolean = false) {
        if (tts.isSpeaking) {
            tts.stop()
        }
        if (shutDown) {
            tts.shutdown()
        }
    }

    fun isSpeaking(): Boolean {
        return tts.isSpeaking
    }
}