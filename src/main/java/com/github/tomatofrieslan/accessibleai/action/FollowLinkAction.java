package com.github.tomatofrieslan.accessibleai.action;

import com.github.tomatofrieslan.accessibleai.services.KeyboardNavigationService;
import com.intellij.icons.AllIcons;
import com.intellij.ml.llm.core.chat.ui.chat.TextPartViewEditorPane;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.impl.ui.KeymapPanel;
import com.intellij.openapi.project.Project;
import com.intellij.platform.lang.lsWidget.OpenSettingsAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FollowLinkAction extends AnAction {
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
        if (component instanceof TextPartViewEditorPane editorPane) {
            int caretPosition = editorPane.getCaretPosition();
            HTMLDocument htmlDocument = (HTMLDocument) editorPane.getDocument();
            Element element = htmlDocument.getCharacterElement(caretPosition);
            Object attribute = element.getAttributes().getAttribute(HTML.Tag.A);
            if (attribute instanceof AttributeSet attributeSet) {
                URL url;
                try {
                    url = new URL((String) attributeSet.getAttribute(HTML.Attribute.HREF));
                } catch (MalformedURLException e) {
                    url = null;
                }
                editorPane.fireHyperlinkUpdate(new HyperlinkEvent(editorPane, HyperlinkEvent.EventType.ACTIVATED, url, "is this working", element));
            }
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
