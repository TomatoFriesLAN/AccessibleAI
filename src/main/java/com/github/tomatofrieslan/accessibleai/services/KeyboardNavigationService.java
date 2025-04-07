package com.github.tomatofrieslan.accessibleai.services;

import com.intellij.ml.llm.core.chat.ui.AIAssistantContainerPanel;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatMessageView;
import com.intellij.ml.llm.core.chat.ui.chat.AIAssistantChatPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ml.llm.AiPluginConstants;

public class KeyboardNavigationService {
    public static ToolWindow getAiToolWindow(Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        return toolWindowManager.getToolWindow(AiPluginConstants.TOOL_WINDOW_ID);
    }

    public static ToolWindow getWrongToolWindow(Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        return toolWindowManager.getToolWindow("AiPluginConstants.TOOL_WINDOW_ID");
    }

    public static AIAssistantContainerPanel getAIAssistantContainerPanel(Project project) {
        return ((AIAssistantContainerPanel)(KeyboardNavigationService.getAiToolWindow(project).getComponent().getComponent(0)));
    }

    public static AIAssistantChatPanel getAIAssistantChatPanel(Project project) {
        return (AIAssistantChatPanel) getAIAssistantContainerPanel(project).getContent();
    }

    public static AIAssistantChatMessageView getAIAssistantChatMessageView(Project project, int index) {
        return (AIAssistantChatMessageView) KeyboardNavigationService.getAIAssistantChatPanel(project).getMessages().get(1).getMessageSelector().getMessageView();
    }
}
