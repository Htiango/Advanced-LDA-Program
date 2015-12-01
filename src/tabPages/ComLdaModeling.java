package tabPages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ComLdaModeling extends Composite{
	public ComLdaModeling(Shell shell,Composite parent, int style){
		super(parent, style);
		
		TabFolder tabFolderModel = new TabFolder(this, SWT.NONE);
		tabFolderModel.setBounds(5, 10, 670, 500);
		
		TabItem tbtmModel1 = new TabItem(tabFolderModel, SWT.NONE);
		tbtmModel1.setText("Model 1");
		Composite compositeModel1 = new ComModel1(shell, tabFolderModel, SWT.NONE);    
		tbtmModel1.setControl(compositeModel1);  
		
		TabItem tbtmModel2 = new TabItem(tabFolderModel, SWT.NONE);
		tbtmModel2.setText("Model 2");
		Composite compositeModel2 = new ComModel2(shell,tabFolderModel,SWT.NONE);
		tbtmModel2.setControl(compositeModel2);
	}
	
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
}
