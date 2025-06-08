package com.example.vocabry.data.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.vocabry.R
import java.util.concurrent.TimeUnit
/**
 * Trieda zodpovedná za naplánovanie pravidelnej dennej pripomienky (notifikácie).
 *
 * Používa WorkManager na opakované spúšťanie pripomienky každých 8 hodín.
 */
class NotificationScheduler {
    /**
     * Naplánuje opakovanú dennú pripomienku s oneskorením 8 hodín od aktuálneho času.
     *
     * Využíva WorkManager s `PeriodicWorkRequest`, ktorý sa vykoná každých 8 hodín.
     * Ak existuje už existuje práca s rovnakým názvom tak tento príkaz `ExistingPeriodicWorkPolicy.UPDATE` zabezpečí
     * že sa nahradí.
     *
     * @param context Kontext aplikácie použitý na získanie inštancie WorkManagera a prístup k string resource.
     */
    fun scheduleDailyReminder(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(8, TimeUnit.HOURS)
            .setInitialDelay(8, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            context.getString(R.string.daily_reminder),
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}