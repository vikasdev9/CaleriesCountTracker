package com.nutrimind.ai.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nutrimind.ai.utils.NotificationHelper

class WaterReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        NotificationHelper.showNotification(
            applicationContext,
            "Hydration Reminder",
            "Time to drink some water and stay healthy!"
        )
        return Result.success()
    }
}
