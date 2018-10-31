package cn.zgy.plugin.listener;

import cn.zgy.plugin.util.GlobalVar;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class MagicTypedActionHandler implements TypedActionHandler {

    private TypedActionHandler mLastHandler;
    public MagicTypedActionHandler(TypedActionHandler lastHandler){
        mLastHandler = lastHandler;
    }

    @Override
    public void execute(@NotNull Editor editor, char c, @NotNull DataContext dataContext) {
        if(!GlobalVar.hasAddListener){
            Project project = PlatformDataKeys.PROJECT.getData(dataContext);
            GlobalVar.registerDocumentListener(project, editor, false);

        }
        if(mLastHandler != null){
            mLastHandler.execute(editor, c, dataContext);
        }
    }
}
