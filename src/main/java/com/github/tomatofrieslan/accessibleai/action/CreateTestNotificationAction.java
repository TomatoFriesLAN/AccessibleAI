package com.github.tomatofrieslan.accessibleai.action;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.keymap.impl.ui.KeymapPanel;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.platform.lang.lsWidget.OpenSettingsAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CreateTestNotificationAction extends AnAction {
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        FocusManager focusManager = FocusManager.getCurrentManager();

        Notification notification = new Notification("Accessible AI Notifications", "Test notification", focusManager.getFocusOwner().toString(), NotificationType.INFORMATION);
        notification.addAction(new OpenSettingsAction(KeymapPanel.class, "Keymap...", AllIcons.General.Settings));

        Notifications.Bus.notify(notification);

        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "getPopupTitle(e)", new DefaultActionGroup(), e.getDataContext(), JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING, true, null, -1,
                null, "what is this");

        popup.showCenteredInCurrentWindow(e.getProject());
    }
}
