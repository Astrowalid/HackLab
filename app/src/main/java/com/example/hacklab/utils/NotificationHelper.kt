package com.example.hacklab.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hacklab.R

object NotificationHelper {

    private const val CHANNEL_ID = "HackLabChannel"
    private const val CHANNEL_NAME = "HackLab Notifications"
    private const val CHANNEL_DESCRIPTION = "Notifications pour le panier et le timeout"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context, title: String, message: String, notificationId: Int) {
        try {
            // Utilisation d'une icône système par défaut pour éviter les crashs si l'icône perso est manquante/invalide
            // Idéalement, utilisez une icône monochrome avec fond transparent (R.drawable.ic_notification par ex)
            val icon = android.R.drawable.ic_dialog_info 

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon) 
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            
            // Vérification de permission basique avant l'envoi
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || 
                androidx.core.content.ContextCompat.checkSelfPermission(
                    context, 
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(notificationId, builder.build())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}