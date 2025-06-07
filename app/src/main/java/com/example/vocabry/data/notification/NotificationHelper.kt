package com.example.vocabry.data.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vocabry.R
/**
 * Pomocná trieda na zobrazovanie notifikácií používateľovi.
 *
 * @param context Kontext aplikácie použitý na vytvorenie notifikácie.
 */
class NotificationHelper(private val context: Context) {
    private val channelId = "daily_reminder_channel"
    /**
     * Zobrazí notifikáciu s dennou pripomienkou.
     *
     * Vytvorí notifikačný kanál (ak ešte neexistuje) a následne zobrazí
     * notifikáciu s daným názvom a správou.
     *
     * @param title Nadpis notifikácie.
     * @param message Text správy v notifikácii.
     *
     * @RequiresPermission Označuje, že je potrebné povolenie [Manifest.permission.POST_NOTIFICATIONS].
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showReminderNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Denné pripomienky",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notification)
    }
}