package com.example.vocabry.data.notification

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotificationScheduler {
    fun scheduleDailyReminder(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(8, TimeUnit.HOURS)
            .setInitialDelay(8, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}