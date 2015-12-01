package pages;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Composite;

public class MainPages {

	protected Shell shell;
	private ExplorerPage explorerPage;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainPages window = new MainPages();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Main Page");
		
		Label MainPagePictureLabel = new Label(shell, SWT.NONE);
		MainPagePictureLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		MainPagePictureLabel.setBounds(22, 26, 190, 160);
		MainPagePictureLabel.setText("New Label");
		
		Group groupApplications = new Group(shell, SWT.BORDER);
		groupApplications.setText("Applications");
		groupApplications.setBounds(230, 11, 210, 250);
		
		Button ExplorerButton = new Button(groupApplications, SWT.NONE);
		ExplorerButton.setFont(SWTResourceManager.getFont(".SF NS Text", 16, SWT.NORMAL));
		ExplorerButton.setBounds(26, 5, 153, 50);
		ExplorerButton.setText("Explorer");
		ExplorerButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				explorerPage = new ExplorerPage();
				explorerPage.open();
			}
		});
		
		Button FlowChartButton = new Button(groupApplications, SWT.NONE);
		FlowChartButton.setFont(SWTResourceManager.getFont(".SF NS Text", 16, SWT.NORMAL));
		FlowChartButton.setBounds(26, 60, 153, 50);
		FlowChartButton.setText("Flow Chart");
		
		Button ModelVisualizeButton = new Button(groupApplications, SWT.NONE);
		ModelVisualizeButton.setFont(SWTResourceManager.getFont(".SF NS Text", 16, SWT.NORMAL));
		ModelVisualizeButton.setBounds(26, 115, 153, 50);
		ModelVisualizeButton.setText("Model Visualize");
		
		Button AcknowledgmentButton = new Button(groupApplications, SWT.NONE);
		AcknowledgmentButton.setFont(SWTResourceManager.getFont(".SF NS Text", 16, SWT.NORMAL));
		AcknowledgmentButton.setBounds(26, 170, 153, 50);
		AcknowledgmentButton.setText("Acknowledgment");
		
		
		Composite compositeMainPageDetail = new Composite(shell, SWT.BORDER);
		compositeMainPageDetail.setBounds(22, 200,190, 57);

	}
}
