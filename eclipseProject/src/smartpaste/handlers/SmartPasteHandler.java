package smartpaste.handlers;




import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import smartpaste.SmartPaste;


import org.eclipse.ui.PlatformUI;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;

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
		            	System.out.println("\nactive selection empty");
		            	System.out.println("\nactive line:"+line);
		            	String line_sel = doc.get().substring(doc.getLineOffset(line), doc.getLineOffset(line)+len);
		            	String base_text;
		            	if (line != 0){
		            		base_text = doc.get().substring(0, doc.getLineOffset(line)-1)+"\n";
		            	} else {
		            		base_text = "";
		            	}
		            	String next = SmartPaste.getNext(base_text, line_sel);
		            	doc.replace(doc.getLineOffset(line+1), 0, next );
		            	editor.selectAndReveal(doc.getLineOffset(line+1)-1, next.length());
		            } else {
		            	System.out.println("\nactive selection not empty");
		            	String next = SmartPaste.getNext(doc.get().substring(0, textSel.getOffset()), textSel.getText());
		            	doc.replace(textSel.getOffset()+textSel.getLength(), 0, next );
		            	editor.selectAndReveal(textSel.getOffset()+textSel.getLength(), next.length());
		            }
		        }
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		return null;
		}
}
