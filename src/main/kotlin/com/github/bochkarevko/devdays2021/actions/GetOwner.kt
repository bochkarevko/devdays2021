package com.github.bochkarevko.devdays2021.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GetOwner : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val path = e.getFilePath()
        if (path != null) {
            Notifications.Bus.notify(
                Notification(
                    Notifications.SYSTEM_MESSAGES_GROUP_ID,
                    "File Owner",
                    "NOT YET IMPLEMENTED",
                    NotificationType.INFORMATION,
                    null
                )
            )
        }
    }
}
