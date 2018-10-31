package cn.zgy.plugin;

import cn.zgy.plugin.util.CharPanel;
import cn.zgy.plugin.util.GlobalVar;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class EnableAction extends AnAction {
    private GlobalVar.State state = GlobalVar.getInstance().state;
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        if(editor == null || project == null){
            return;
        }
        JComponent component = editor.getContentComponent();
        if(component == null){
            return;
        }
        state.IS_ENABLE = !state.IS_ENABLE;
        updateState(anActionEvent.getPresentation());
        //只要点击Enable项，就把缓存中所有的文本清理
        CharPanel.getInstance(component).clearAllStr();

        GlobalVar.registerDocumentListener(project, editor, state.IS_ENABLE);
    }

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null || project == null) {
            e.getPresentation().setEnabled(false);
        } else {
            JComponent component = editor.getContentComponent();
            if (component == null) {
                e.getPresentation().setEnabled(false);
            } else {
                e.getPresentation().setEnabled(true);
            }
        }
        updateState(e.getPresentation());
    }

    private void updateState(Presentation presentation) {

        if (state.IS_ENABLE) {
            presentation.setText("Enable");
            presentation.setIcon(AllIcons.General.InspectionsOK);
        } else {
            presentation.setText("Disable");
            presentation.setIcon(AllIcons.Actions.Cancel);
        }
    }
}
