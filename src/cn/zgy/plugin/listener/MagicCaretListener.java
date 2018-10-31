package cn.zgy.plugin.listener;

import cn.zgy.plugin.util.CharPanel;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;

import java.awt.*;


public class MagicCaretListener implements CaretListener {

    @Override
    public void caretPositionChanged(CaretEvent e) {
        //此处经测试，只有增加字符的时候更新位置，删除时，坐标不会更新
        LogicalPosition logicalPosition = e.getNewPosition();
        Point position = e.getEditor().logicalPositionToXY(logicalPosition);
        CharPanel.getInstance(null).setPosition(position);
    }

    @Override
    public void caretAdded(CaretEvent e) {
    }

    @Override
    public void caretRemoved(CaretEvent e) {
    }
}
