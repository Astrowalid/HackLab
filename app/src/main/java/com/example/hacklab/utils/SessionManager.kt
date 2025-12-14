package com.example.hacklab.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.example.hacklab.services.TimeoutService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object SessionManager {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var appContext: Context? = null

    // Un flux pour notifier l'interface quand le temps est écoulé
    private val _timeoutFlow = MutableSharedFlow<Unit>()
    val timeoutFlow = _timeoutFlow.asSharedFlow()

    private val timeoutReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TimeoutService.ACTION_TIMEOUT) {
                scope.launch {
                    _timeoutFlow.emit(Unit)
                }
            }
        }
    }

    fun init(context: Context) {
        this.appContext = context.applicationContext
        
        val filter = IntentFilter(TimeoutService.ACTION_TIMEOUT)
        
        ContextCompat.registerReceiver(
            context.applicationContext,
            timeoutReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    /**
     * Démarre ou redémarre le minuteur via le Service.
     * Doit être appelé à chaque interaction utilisateur.
     */
    fun startUserSession() {
        val context = appContext ?: return
        val intent = Intent(context, TimeoutService::class.java)
        context.startService(intent)
    }

    fun stopTimer() {
        val context = appContext ?: return
        val intent = Intent(context, TimeoutService::class.java)
        context.stopService(intent)
    }
}