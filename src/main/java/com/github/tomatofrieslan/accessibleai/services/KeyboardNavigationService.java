package com.github.tomatofrieslan.accessibleai.services;

import com.intellij.ml.llm.core.chat.ui.AIAssistantContainerPanel;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatMessageView;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatPanel;
import com.intellij.ml.llm.core.chat.ui.chat.TextPartViewEditorPane;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ml.llm.AiPluginConstants;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class KeyboardNavigationService {
    public static ToolWindow getAiToolWindow(Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        return toolWindowManager.getToolWindow(AiPluginConstants.TOOL_WINDOW_ID);
    }

    public static AIAssistantContainerPanel getAIAssistantContainerPanel(Project project) {
        Component result = KeyboardNavigationService.getAiToolWindow(project).getComponent().getComponent(0);
        return result instanceof AIAssistantContainerPanel panel ? panel : null;
    }

    public static AIAssistantChatPanel getAIAssistantChatPanel(Project project) {
        AIAssistantContainerPanel containerPanel = getAIAssistantContainerPanel(project);
        if (containerPanel == null) {
            return null;
        }
        JComponent result = containerPanel.getContent();
        return result instanceof AIAssistantChatPanel panel ? panel : null;
    }

    public static AIAssistantChatMessageView getAIAssistantChatMessageView(Project project, int index) {
        return (AIAssistantChatMessageView) KeyboardNavigationService.getAIAssistantChatPanel(project).getMessages().get(index).getMessageSelector().getMessageView();
    }

    public static boolean FollowLinkAt(int position, TextPartViewEditorPane editorPane) {
        HTMLDocument htmlDocument = (HTMLDocument) editorPane.getDocument();
        Element element = htmlDocument.getCharacterElement(position);
        Object attribute = element.getAttributes().getAttribute(HTML.Tag.A);
        if (attribute instanceof AttributeSet attributeSet) {
            URL url;
            try {
                url = new URL((String) attributeSet.getAttribute(HTML.Attribute.HREF));
            } catch (MalformedURLException e) {
                url = null;
            }
            editorPane.fireHyperlinkUpdate(new HyperlinkEvent(editorPane, HyperlinkEvent.EventType.ACTIVATED, url, "is this working", element));
            return true;
        }
        return false;
    }

    public static URL getUrlAt(int position, TextPartViewEditorPane editorPane) {
        HTMLDocument htmlDocument = (HTMLDocument) editorPane.getDocument();
        Element element = htmlDocument.getCharacterElement(position);
        Object attribute = element.getAttributes().getAttribute(HTML.Tag.A);
        URL url = null;
        if (attribute instanceof AttributeSet attributeSet) {
            try {
                url = new URL((String) attributeSet.getAttribute(HTML.Attribute.HREF));
            } catch (MalformedURLException ignored) {}  // would set url to null here, but it already is
        }
        return url;
    }
}
