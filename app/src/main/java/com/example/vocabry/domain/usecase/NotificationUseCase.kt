package com.example.vocabry.domain.usecase

import android.content.Context
import com.example.vocabry.data.notification.NotificationScheduler

/**
 * UseCase trieda zodpovedná za naplánovanie dennej notifikácie.
 *
 * Trieda deleguje operáciu plánovania na inštanciu triedy [NotificationScheduler],
 * ktorá obsahuje konkrétnu implementáciu plánovania pomocou WorkManageru.
 *
 * @param scheduler Inštancia triedy [NotificationScheduler], ktorá zabezpečuje naplánovanie notifikácie.
 */
class NotificationUseCase (
    private val scheduler: NotificationScheduler
){
    /**
     * Spustí naplánovanie dennej pripomienky pre používateľa.
     *
     * @param context Kontext aplikácie potrebný pre inicializáciu WorkManageru.
     */
    fun invoke(context: Context) {
        scheduler.scheduleDailyReminder(context)
    }
}