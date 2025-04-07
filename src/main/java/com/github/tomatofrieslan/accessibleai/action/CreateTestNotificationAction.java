package com.github.tomatofrieslan.accessibleai.action;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.impl.ui.KeymapPanel;
import com.intellij.platform.lang.lsWidget.OpenSettingsAction;
import org.jetbrains.annotations.NotNull;

public class CreateTestNotificationAction extends AnAction {
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Notification notification = new Notification("Accessible AI Notifications", "Test notification", "This notification will be more useful in the future.", NotificationType.INFORMATION);
        notification.addAction(new OpenSettingsAction(KeymapPanel.class, "Keymap...", AllIcons.General.Settings));

        Notifications.Bus.notify(notification);
    }
}
