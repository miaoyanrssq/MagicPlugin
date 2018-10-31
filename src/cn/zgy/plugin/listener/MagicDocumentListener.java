package cn.zgy.plugin.listener;


import cn.zgy.plugin.util.CharPanel;
import cn.zgy.plugin.util.GlobalVar;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class MagicDocumentListener implements DocumentListener {

    private Project mProject;
    private Editor mEditor;
    private MagicCaretListener caretListener;

    public MagicDocumentListener(Project project){
        mProject = project;
        //光标移动监听
        caretListener = new MagicCaretListener();
    }

    @Override
    public void beforeDocumentChange(DocumentEvent event) {
        if(mEditor == null){
            mEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
            if(mEditor == null){
                return;
            }
        }

        CaretModel caretModel = mEditor.getCaretModel();
        caretModel.addCaretListener(caretListener);

        GlobalVar.updateGlobalVar(mEditor);

        JComponent editorComponent = mEditor.getContentComponent();
        CharPanel charPanel = CharPanel.getInstance(editorComponent);
        //添加字符串
        String newStr = event.getNewFragment().toString().trim();
        if(newStr.length() > 0){
            charPanel.addStrToList(newStr, true);
        }
        //删除字符串，由于MagicCaretListener监听的问题，在此不做删除字符的添加动作
//        String deleteStr = event.getOldFragment().toString().trim();
//        if(deleteStr.length() > 0){
//            charPanel.addStrToList(deleteStr, false);
//        }
    }

    @Override
    public void documentChanged(DocumentEvent event) {

    }
}
