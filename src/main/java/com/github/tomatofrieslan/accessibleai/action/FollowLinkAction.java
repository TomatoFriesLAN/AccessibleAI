package com.github.tomatofrieslan.accessibleai.action;

import com.github.tomatofrieslan.accessibleai.services.KeyboardNavigationService;
import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.QuickSwitchSchemeAction;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatMessageViewModel;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatPanel;
import com.intellij.ml.llm.core.chat.ui.chat.MessagePartView;
import com.intellij.ml.llm.core.chat.ui.chat.TextPartViewEditorPane;
import com.intellij.ml.llm.core.chat.ui.chat.selection.AIMessageSelectViewProcessorHolder;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.List;

import com.intellij.openapi.diagnostic.Logger;

public class FollowLinkAction extends QuickSwitchSchemeAction {
    private static final Logger LOGGER = Logger.getInstance(FollowLinkAction.class);

    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    protected void fillActions(Project project, @NotNull DefaultActionGroup group, @NotNull DataContext dataContext) {
        AIAssistantChatPanel chatPanel = KeyboardNavigationService.getAIAssistantChatPanel(project);
        if (chatPanel == null) {
            Notification notification = new Notification("Accessible AI Notifications", "No current chat session.", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);

            return;
        }

        List<AIMessageSelectViewProcessorHolder> messages = chatPanel.getMessages();

        int messageLinks = 0;
        int totalLinks = 0;
        boolean shouldAddSeparator = false;

        for (AIMessageSelectViewProcessorHolder message : messages) {
            if (messageLinks > 0) {
                shouldAddSeparator = true;
                messageLinks = 0;
            }

            List<MessagePartView> parts = ((AIAssistantChatMessageViewModel) message).getAllParts();

            for (MessagePartView part : parts) {
                Component messagePart = part.getComponent();

                if (messagePart instanceof TextPartViewEditorPane textPart) {
                    HTMLDocument document = ((HTMLDocument) textPart.getDocument());
                    HTMLDocument.Iterator iterator = document.getIterator(HTML.Tag.A);

                    while (iterator.isValid()) {
                        int position = iterator.getStartOffset();
                        int length = iterator.getEndOffset() - position;

                        try {
                            if (shouldAddSeparator) {
                                group.addSeparator();
                                shouldAddSeparator = false;
                            }
                            String text = textPart.getText(position, length);
                            group.add(new DumbAwareAction(text, "", KeyboardNavigationService.getUrlAt(position, textPart) != null ? AllIcons.General.Web : AllIcons.FileTypes.Text) {
                                @Override
                                public void actionPerformed(@NotNull AnActionEvent e) {
                                    KeyboardNavigationService.FollowLinkAt(position, textPart);
                                }
                            });
                            messageLinks++;
                            totalLinks++;
                            LOGGER.warn(text);
                        } catch (BadLocationException e) {
                            LOGGER.warn("BadLocationException while retrieving hyperlink text", e);
                        }

                        iterator.next();
                    }
                }
            }
        }

        if (totalLinks == 0) {
            Notification notification = new Notification("Accessible AI Notifications", "No links in current chat session.", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
        }
    }
}
