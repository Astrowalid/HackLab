package com.example.hacklab.services

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder

class TimeoutService : Service() {

    private var timer: CountDownTimer? = null

    companion object {
        const val ACTION_TIMEOUT = "com.example.hacklab.SESSION_TIMEOUT"
        // Durée du timeout en millisecondes (ex: 30 secondes pour le test)
        private const val TIMEOUT_DURATION = 30 * 1000L
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTimer()
        return START_NOT_STICKY
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(TIMEOUT_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Optionnel : Log ou notification de décompte
            }

            override fun onFinish() {
                // Envoyer un broadcast à l'application
                val intent = Intent(ACTION_TIMEOUT)
                intent.setPackage(packageName) // Sécurité : restreindre à notre app
                sendBroadcast(intent)
                stopSelf()
            }
        }.start()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}