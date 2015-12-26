package tabPages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ComPrecision extends Composite{
	public ComPrecision(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Label labelTest  = new Label(this, SWT.BORDER);
		labelTest.setText("precision");
		labelTest.setBounds(10, 10, 100, 50);
		

	}
	
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
	
}