package pages;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;

import tabPages.ComLdaModeling;
import tabPages.ComPrecision;
import tabPages.ComPrediction;
import tabPages.ComPreprocess;

public class ExplorerPage {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */


	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(750, 600);
		shell.setText("Explorer Tab Page");		

		TabFolder tabFolderExplorer = new TabFolder(shell, SWT.NONE);
		tabFolderExplorer.setBounds(25, 15, 700, 560);
				
		TabItem tbtmPreprocess = new TabItem(tabFolderExplorer, SWT.NONE);
		tbtmPreprocess.setText("  Preprocess  ");
		Composite compositePreprocess = new ComPreprocess(shell, tabFolderExplorer, SWT.NONE);    
		tbtmPreprocess.setControl(compositePreprocess);  
		
		TabItem tbtmLdaModeling = new TabItem(tabFolderExplorer, SWT.NONE);
		tbtmLdaModeling.setText("LDA Modeling");
		Composite compositeLdaModeling = new ComLdaModeling(shell,tabFolderExplorer,SWT.NONE);
		tbtmLdaModeling.setControl(compositeLdaModeling);
		
		TabItem tbtmPrediction = new TabItem(tabFolderExplorer, SWT.NONE);
		tbtmPrediction.setText("   Prediction  ");
		Composite compositePrediction = new ComPrediction(shell,tabFolderExplorer, SWT.NONE);
		tbtmPrediction.setControl(compositePrediction);
		
		TabItem tbtmPrecision = new TabItem(tabFolderExplorer, SWT.NONE);
		tbtmPrecision.setText("    Precision    ");
		Composite compositePrecision = new ComPrecision(shell,tabFolderExplorer, SWT.NONE);
		tbtmPrecision.setControl(compositePrecision);

	}
}
