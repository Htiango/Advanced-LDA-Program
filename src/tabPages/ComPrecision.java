package tabPages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ComPrecision extends Composite{
	
	/**
     * a text show the number of unique user
     */
    private Text userNumText;    
    
    /**
     * a text show the num of docs in the file
     */
    private Text docNumText;
    
    /**
     * a text shows the num of answers in the files
     */
    private Text ansNumText;
    
    /**
     * a text shows the num of answers to the current question
     */
    private Text AnsNumText;
    
    /**
     * represent the index of the doc
     */
    private int docIndex = 1;
    
    /**
     * a label that shows the index of the present doc 
     */
    private Label labelPresentDoc;
    
    /**
     * input the doc index that you want to go to
     */
    private Text textTurn2Doc;
    
    /**
     * show the content of the question
     */
    private Text textQuestionDetail;
    
    private Combo comboModelType;
    
    /**
     * the child node of the xml file 
     */
    private static String[] CHILDREN = 
    	{"问题标题","问题内容","提问者用户名","提问者性别","提问者年龄","提问时间",
    	"回复人1姓名", "回复人1职称", "回复人1分析",  "回复人1回复时间",
    	"回复人2姓名", "回复人2职称", "回复人2分析",  "回复人2回复时间",
    	"回复人3姓名", "回复人3职称", "回复人3分析",  "回复人3回复时间",
    	"回复人4姓名", "回复人4职称", "回复人4分析",  "回复人4回复时间",
    	"回复人5姓名", "回复人5职称", "回复人5分析",  "回复人5回复时间"};
    
    /**
     * the child mode of the segged map
     */
    private static String[] CHILDREN2 = 
    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
    
	private final String[] MODELTYPE = {"Model 1", "Model 2"};
    
    
	
	public ComPrecision(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Group groupDocQuestion = new Group(this, SWT.BORDER);
		groupDocQuestion.setText("训练文档的问题");
		groupDocQuestion.setBounds(10, 10, 660, 150);
		
		Group groupPresentQuestion = new Group(groupDocQuestion, SWT.BORDER);
		groupPresentQuestion.setText("问题详情");
		groupPresentQuestion.setBounds(5, 5, 330, 125);
		
		labelPresentDoc = new Label(groupPresentQuestion, SWT.BORDER);
		labelPresentDoc.setText("当前第 x 条");
//        labelPresentDoc.setText("当前第" + docIndex +" 条");
        labelPresentDoc.setBounds(5, 8, 80, 15);
        
        Button buttonFrontDoc = new Button(groupPresentQuestion, SWT.BORDER);
        buttonFrontDoc.setText("Front");
        buttonFrontDoc.setBounds(75, 8, 60, 20);
        buttonFrontDoc.setBackground(new Color(null,245,245,245));
        buttonFrontDoc.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				goFrontPage();
			}
		});
        
        Button buttonNextDoc = new Button(groupPresentQuestion, SWT.BORDER);
        buttonNextDoc.setText("Next");
        buttonNextDoc.setBounds(130, 8, 60, 20);
        buttonNextDoc.setBackground(new Color(null,245,245,245));
        buttonNextDoc.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				goNextPage();
			}
		});
        
        Label labelTurn2Doc = new Label(groupPresentQuestion, SWT.BORDER);
        labelTurn2Doc.setText("转到第");
        labelTurn2Doc.setBounds(190, 8, 40, 15);
        
        textTurn2Doc = new Text(groupPresentQuestion, SWT.BORDER);
        textTurn2Doc.setBounds(230, 8, 30, 20);
        
        Button buttonGoDoc = new Button(groupPresentQuestion, SWT.BORDER);
        buttonGoDoc.setBounds(260, 8, 45, 20);
        buttonGoDoc.setText("Go");
        buttonGoDoc.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		goPage();
        	}
        });
        
        textQuestionDetail = new Text(groupPresentQuestion, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
        textQuestionDetail.setBounds(5, 30, 315, 75);

		
		Group groupDocDetail = new Group(groupDocQuestion, SWT.BORDER);
		groupDocDetail.setText("文档信息");
		groupDocDetail.setBounds(350, 5, 300, 90);
		
		userNumText = new Text(groupDocDetail, SWT.BORDER | SWT.READ_ONLY);
        userNumText.setBounds(10, 10, 130, 22);
        userNumText.setBackground(new Color(null,245,245,245));
        userNumText.setText("不同答者数：");
        
        docNumText = new Text(groupDocDetail, SWT.BORDER | SWT.READ_ONLY);
        docNumText.setBounds(150, 10, 130, 22);
        docNumText.setBackground(new Color(null,245,245,245));
        docNumText.setText("问题数：");
        
        ansNumText = new Text(groupDocDetail, SWT.BORDER | SWT.READ_ONLY);
        ansNumText.setBounds(10, 40, 130, 22);
        ansNumText.setBackground(new Color(null,245,245,245));
        ansNumText.setText("回答数：");
        
        AnsNumText= new Text(groupDocDetail, SWT.BORDER | SWT.READ_ONLY);
        AnsNumText.setBounds(150, 40, 130, 22);
        AnsNumText.setBackground(new Color(null,245,245,245));
        AnsNumText.setText("当前问题回答数：");
		
        Label labelModelType = new Label(groupDocQuestion, SWT.BORDER);
		labelModelType.setText("请选择 Model 类型：");
		labelModelType.setBounds(350, 100, 120, 20);
		
		comboModelType = new Combo(groupDocQuestion, SWT.READ_ONLY);
		comboModelType.setBounds(470, 100, 80, 20);
		comboModelType.setItems(MODELTYPE);

		Button buttonComfirmModel = new Button(groupDocQuestion, SWT.BORDER);
		buttonComfirmModel.setText("确认");
		buttonComfirmModel.setBounds(560, 95, 90, 32);
	}
	
	
	private void updateDocIndex(){
		labelPresentDoc.setText("当前第 " + docIndex + " 条");
	}
	
	/**
	 * click the button and go to the front page and show in the text
	 */
	private void goFrontPage(){
		if(docIndex - 1 < 1){
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("已是第一页，无法向前!");
			messagebox.open();
		}
		else {
			docIndex -= 1;
			printDoc(docIndex);
			updateDocIndex();
		}		
	}
	
	/**
	 * click the button and go to the next page and show in the text
	 */
	private void goNextPage(){
		int docLength = ComPreprocess.docMapMap.size();
		if(docIndex + 1 > docLength){
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("已是最后一页，无法向后!");
			messagebox.open();
		}
		else{
			docIndex += 1;
			printDoc(docIndex);
			updateDocIndex();
		}
	}
	
	/**
	 * click the 'go' button and go to the certain page and show in the text
	 */
	private void goPage(){
		int docLength = ComPreprocess.docMapMap.size();
		int goIndex = Integer.parseInt(textTurn2Doc.getText());
		if (goIndex < 1 || goIndex > docLength){
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("无法转到此页!");
			messagebox.open();
		}
		else{
			docIndex = goIndex;
			printDoc(docIndex);
			updateDocIndex();
		}
	}
	
	private void printDoc(int docIndex){
		textQuestionDetail.setText("");
		String childText = "问题内容";
		String childContent;
		String inputText;
		childContent = ComPreprocess.docMapMap.get(docIndex).get(childText);
		if (childContent.length() != 0){
			inputText = "【" + childText +"】：" + childContent; 
			textQuestionDetail.append(inputText);
			textQuestionDetail.append("\n\n");
		}
		
		userNumText.setText("不同答者数：" + ComPreprocess.numExperts);
    	docNumText.setText("问题数：" + ComPreprocess.numQuestion);
    	ansNumText.setText("回答数：" + ComPreprocess.numAnswer);
    	AnsNumText.setText("当前问题回答数：" + getAnswerNum(docIndex));		
	}

	private int getAnswerNum(int questionIndex){
		String childText;
		String childContent;
		int numAnswer = 0;
		for (int i = 0; i < CHILDREN2.length; i++){
			childText = CHILDREN2[i];
			childContent = ComPreprocess.docMapMap.get(docIndex).get(childText);
			if (childContent.length() != 0){
				numAnswer += 1;
			}
		}				
		return numAnswer;
	}
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
	
}