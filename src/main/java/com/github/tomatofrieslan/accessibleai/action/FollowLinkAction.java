package com.github.tomatofrieslan.accessibleai.action;

import com.github.tomatofrieslan.accessibleai.services.KeyboardNavigationService;
import com.intellij.ml.llm.core.chat.ui.chat.TextPartViewEditorPane;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

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

         */
        JEditorPane messagePart = ((TextPartViewEditorPane) KeyboardNavigationService.getAIAssistantChatMessageView(currentProject, 0).getMessage().getAllParts().get(0).getComponent());
        HTMLDocument document = ((HTMLDocument) messagePart.getDocument());
        HTMLDocument.Iterator iterator = document.getIterator(HTML.Tag.A);
        int startOffset = iterator.getStartOffset();
        //Element element = document.getCharacterElement(messagePart.getCaretPosition());
        messagePart.fireHyperlinkUpdate(new HyperlinkEvent(messagePart, HyperlinkEvent.EventType.ACTIVATED, null, "is this working", document.getCharacterElement(startOffset)));
    }
}
