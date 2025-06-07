package com.example.vocabry.data.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
/**
 * Worker, ktorý sa vykonáva na pozadí a zodpovedá za zobrazenie pripomienkovej notifikácie používateľovi.
 *
 * Tento worker sa používa v kombinácii s WorkManagerom na plánovanie pravidelných pripomienok
 * na učenie slovíčok. Používa [NotificationHelper] na vytvorenie a zobrazenie notifikácie.
 *
 * @param context Kontext aplikácie, ktorý sa používa na vytvorenie NotificationHelper a získanie zdrojov.
 * @param workerParams Parametre od WorkManagera pre konfigurovanie tohto workeru.
 */
class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    /**
     * Vykonáva hlavnú prácu (zobrazenie notifikácie).
     *
     * Funkcia sa pokúsi vytvoriť a zobraziť pripomienku používateľovi.
     * V prípade, že je vyvolaná výnimka (napr. chýbajúce oprávnenie), vracia neúspešný výsledok.
     *
     * @return [Result.success] ak notifikácia bola úspešne zobrazená,
     *         [Result.failure] v prípade chyby (napr. chýbajúce povolenie).
     */
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