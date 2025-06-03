package com.example.vocabry.domain.usecase

import android.content.Context
import com.example.vocabry.data.notification.NotificationScheduler

class NotificationUseCase (
    private val scheduler: NotificationScheduler
){
    fun invoke(context: Context) {
        scheduler.scheduleDailyReminder(context)
    }
}