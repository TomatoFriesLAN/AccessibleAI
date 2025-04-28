package com.github.tomatofrieslan.accessibleai.action;

import com.github.tomatofrieslan.accessibleai.services.KeyboardNavigationService;
import com.intellij.ml.llm.core.chat.ui.chat.TextPartViewEditorPane;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class FollowHoveredLinkAction extends AnAction {
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project currentProject = anActionEvent.getProject();
        /*
        Messages.showMessageDialog(
                currentProject,
                "we did it",
                "we did it",
                Messages.getInformationIcon());


        JEditorPane messagePart = ((TextPartViewEditorPane) KeyboardNavigationService.getAIAssistantChatMessageView(currentProject, 1).getMessage().getAllParts().get(0).getComponent());
        HTMLDocument document = ((HTMLDocument) messagePart.getDocument());
        HTMLDocument.Iterator iterator = document.getIterator(HTML.Tag.A);
        int startOffset = iterator.getStartOffset();
        //messagePart.hasFocus();
        //Element element = document.getCharacterElement(messagePart.getCaretPosition());

         */

        FocusManager focusManager = FocusManager.getCurrentManager();
        Component component = focusManager.getFocusOwner();
        boolean followed = false;
        if (component instanceof TextPartViewEditorPane editorPane) {
            int caretPosition = editorPane.getCaretPosition();
            followed = KeyboardNavigationService.FollowLinkAt(caretPosition, editorPane);
        }
        if (!followed) {
            Notification notification = new Notification("Accessible AI Notifications", "There is no link to follow here, or you are not focused on a chat message.", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
        }
        /*
        URL url;
        try {
            url = new URL((String) iterator.getAttributes().getAttribute(HTML.Attribute.HREF));
        } catch (MalformedURLException e) {
            url = null;
        }
        messagePart.fireHyperlinkUpdate(new HyperlinkEvent(messagePart, HyperlinkEvent.EventType.ACTIVATED, url, "is this working", document.getCharacterElement(startOffset)));
        */
    }
}
