package smartpaste.handlers;


import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import smartpaste.SmartPaste;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.IFile;
import org.apache.commons.io.IOUtils;
/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SmartPasteHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SmartPasteHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {               
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		        final ITextEditor editor = (ITextEditor)part;
		        IDocumentProvider prov = editor.getDocumentProvider();
		        IDocument doc = prov.getDocument( editor.getEditorInput() );
		        ISelection sel = editor.getSelectionProvider().getSelection();
		        if ( sel instanceof TextSelection ) {
		            TextSelection textSel = (TextSelection)sel;
		            if(textSel.getText().length() == 0){
		            	int line = textSel.getStartLine();
		            	int len = doc.getLineLength(line);
		            	String line_sel = doc.get().substring(doc.getLineOffset(line), doc.getLineOffset(line)+len);
		            	String base_text = doc.get().substring(0, doc.getLineOffset(line)-1);
		            	String next = SmartPaste.getNext(base_text, line_sel);
		            	doc.replace(doc.getLineOffset(line+1), 0, next );
		            	editor.selectAndReveal(doc.getLineOffset(line+1)-1, next.length());
		            } else {
		            	String next = SmartPaste.getNext(doc.get().substring(0, textSel.getOffset()), textSel.getText());
		            	doc.replace(textSel.getOffset()+textSel.getLength(), 0, next );
		            	editor.selectAndReveal(textSel.getOffset()+textSel.getLength(), next.length());
		            }
		        }
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		/*TODO: Supress this menu*/
		/*MessageDialog.openInformation(
				window.getShell(),
				"Smartpaste x",
				"Smartpasting");*/
		return null;
		}
}
