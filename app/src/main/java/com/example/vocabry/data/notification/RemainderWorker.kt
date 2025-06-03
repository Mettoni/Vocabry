package com.example.vocabry.data.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val helper = NotificationHelper(applicationContext)
            helper.showReminderNotification(
                title = "Nezabudni trénovať!",
                message = "Dnes si ešte netrénoval slovíčka"
            )
            Result.success()
        } catch (e: SecurityException) {
            Result.failure()
        }
    }
}