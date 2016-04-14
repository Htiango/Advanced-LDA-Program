package tabPages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import preprocessing.SegWords;
import preprocessing.XMLReader;

public class ComEvaluation extends Composite{

	/**
	 * show the filePath
	 */
	private String[] filePath;
	
	/**
	 * show the filename
	 */
	private String filename;	
	
	/**
     * when click the button ‘open file’, the filepath will be shown in the text
     */
	private Text fileText;
	
    /**
     * show the content of the question
     */
    private Text textQuestionDetail;
    
    /**
     * show the predicted answer
     */
    private Text textPredictAnswer;
    
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
    
    private Button buttonJudge1;
	
    private Button buttonJudge2;
    
	/**
     * the class that translate the xml file
     */
    private XMLReader xmlReader;

    /**
     * the class that seg the words and rid off the stopwords
     */
    private SegWords segWords;
    
    /**
     * the map storing the info about the segged testing set
     */
    private Map<Integer, Map<String,String>> segDocMapMapTest = 
			new HashMap<Integer, Map<String,String>>();
    
    /**
     * Integer represent the docIndex of the test set, Boolean means whether it's relevant<br>
     * true means the prediction is correct.<br>
     * false means it is wrong.<br>
     */
    private Map<Integer, Boolean> annotationMapTest = 
    		new HashMap<Integer, Boolean>();
    
    /**
     * the map storing the info about the testing set
     */
    private Map<Integer, Map<String,String>> docMapMapTest = 
			new HashMap<Integer, Map<String,String>>();

    /**
     * 0:"不使用词典", <br>1: "使用词典", <br>2:"只保留词典"
     */
    private final String[] MODELTYPE = {"LDA Model", "UQA Model"};
    
    /**
     * "问题内容"
     */
    private static String CHILDREN = "问题内容";
    
	/**
     * the child mode of the segged map
     */
    private static String[] CHILDREN2 = 
    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};

    
    private Combo comboModelType;
	
	public ComEvaluation(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		fileText = new Text(this, SWT.BORDER);
        fileText.setBounds(121, 40, 445, 22);
        
        Group groupDocQuestion = new Group(this, SWT.BORDER);
		groupDocQuestion.setText("训练集信息");
		groupDocQuestion.setBounds(10, 80, 660, 180);
		
		Group groupPresentQuestion = new Group(groupDocQuestion, SWT.BORDER);
		groupPresentQuestion.setText("问题详情");
		groupPresentQuestion.setBounds(5, 5, 330, 127);
		
		Group groupPrediction = new Group(groupDocQuestion, SWT.BORDER);
		groupPrediction.setText("匹配答案");
		groupPrediction.setBounds(335, 5, 315, 127);
		
		Label labelModelType = new Label(groupDocQuestion, SWT.BORDER);
		labelModelType.setText("请选择 Model 类型：");
		labelModelType.setBounds(10, 132, 110, 30);
		
		comboModelType = new Combo(groupDocQuestion, SWT.BORDER);
		comboModelType.setBounds(125, 130, 100, 30);
		comboModelType.setItems(MODELTYPE);
		
		Button buttonGetPrediction = new Button(groupDocQuestion, SWT.BORDER);
		buttonGetPrediction.setText("进行匹配");
		buttonGetPrediction.setBounds(230, 127, 90, 32);
		buttonGetPrediction.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				getPrediction();
			}
		});
		
		textPredictAnswer = new Text(groupPrediction, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textPredictAnswer.setBounds(5, 5 , 300, 98);
		textPredictAnswer.setBackground(new Color(null,225,225,225));
		
		Label labelRadioButton = new Label(groupDocQuestion, SWT.BORDER);
		labelRadioButton.setText("进行标注:");
		labelRadioButton.setBounds(345,132, 60 ,30);
		
		buttonJudge1 = new Button(groupDocQuestion, SWT.RADIO);
		buttonJudge1.setText("相关");
		buttonJudge1.setBounds(400, 125, 50, 30);

		buttonJudge2 = new Button(groupDocQuestion, SWT.RADIO);
		buttonJudge2.setText("不相关");
		buttonJudge2.setBounds(450, 125, 60, 30);
		
		Button buttonAnnotation = new Button(groupDocQuestion, SWT.BORDER);
		buttonAnnotation.setText("提交标注");
		buttonAnnotation.setBounds(510, 127, 80, 30);
		buttonAnnotation.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				addAnnotation();	
			}
		});
		
		Button buttonRevoke = new Button(groupDocQuestion, SWT.BORDER);
		buttonRevoke.setText("撤销");
		buttonRevoke.setBounds(585, 127, 60, 30);
		buttonRevoke.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				revokeAnnotation();	
			}
		});
		
		textQuestionDetail = new Text(groupPresentQuestion, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
        textQuestionDetail.setBackground(new Color(null,225,225,225));
        textQuestionDetail.setBounds(5, 30, 315, 75);
		
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
        
        
        Button buttonOpenFile = new Button(this, SWT.BORDER);
        buttonOpenFile.setText("Open File");
        buttonOpenFile.setBounds(20, 40, 85, 22);
        buttonOpenFile.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e){  
        		
          		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("请选择需要打开的文件");
				dialog.setFilterExtensions(new String[]{"*.xml"});
				dialog.setFilterNames(new String[] {"xml文件(*.xml)"});
				filePath = new String[1];
				filePath[0]=dialog.open();
				filename=dialog.getFileName();		
				if (filename != null){
		        	fileText.setText(filePath[0]);
		        }
			}
        });
        
        Button buttonComfirm = new Button(this, SWT.BORDER);
        buttonComfirm.setText("Comfirm");
        buttonComfirm.setBounds(580, 40, 85, 22);
        buttonComfirm.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e){  
        		if (filename != null){
		        	openFile();
		        }
			}			
        });
	}

	/**
	 * open the selected xml file and show in other groups
	 */
	private void openFile() {
		xmlReader = new XMLReader();
		xmlReader.readXml(filePath[0]);	
		
		docMapMapTest = xmlReader.docMapMap;
		
		doSegging();
		
		printDoc(docIndex);
	}
	
	/**
	 * seg the words
	 */
	private void doSegging(){
		segWords = new SegWords();
		int type = ComPreprocess.indexType;
//		System.out.println(type);
		segWords.segWords(xmlReader.docMapMap, xmlReader.docNum,type);
		segDocMapMapTest = segWords.segDocMapMap;

	}
	
	/**
	 * get the prediction answer or users of the input testing question
	 */
	private void getPrediction(){
		List<String> segQuestionWords;
		segQuestionWords = Arrays.asList(segDocMapMapTest.get(docIndex).get(CHILDREN).split("\\s"));
		String comboString = comboModelType.getText();
//		System.out.println(comboString);
		if (comboString.equals(MODELTYPE[0])){
			// do as the model 1
			getResultModel1(segQuestionWords);
		}
		else if(comboString.equals(MODELTYPE[1])){
			// do as the model 2
			getResultModel2(segQuestionWords);
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("请先选择 Model 类型!");
			messagebox.open();					
		}
	}
	
	private void getResultModel1(List<String> segQuestionWords){
		int bestAnsIndex = 0;
		double bestAnsProb = 0.0;
		int wordID;
		double scoreProduct, scoreSum;
		int m = 0;
		int k;
		int w;
		String word;
		int docIndex = 1;
		
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.segDocMapMap.entrySet()){
			String segSentense = entry.getValue().get(CHILDREN);
			
			docIndex = entry.getKey();
			
			if (segSentense.length() != 0){
								
				scoreProduct = 0.0;
				
				for (w =0; w < segQuestionWords.size(); w++){
					word = segQuestionWords.get(w);
					if (!ComModel1.vocabularyQuestion.ifWordExist(word)){
						continue;
					}
					wordID = ComModel1.vocabularyQuestion.getId(word);
					scoreSum = 0.0;
					for (k = 0; k < ComModel1.topicNumAnswer; k++){
						scoreSum += ComModel1.phiAnswer[k][wordID] * ComModel1.thetaAnswer[m][k];
					}
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}
				}	
//				sum += scoreProduct;
				
				if(scoreProduct > bestAnsProb){					
					bestAnsProb = scoreProduct;
					bestAnsIndex = docIndex;
				}
			}
			m += 1;
		}
		textPredictAnswer.setText(ComPreprocess.docMapMap.get(bestAnsIndex).get(CHILDREN2[0]));
	}
	
	private void getResultModel2(List<String> segQuestionWords){
		int bestAnsIndex = 0;
		double bestAnsProb = 0.0;
		int wordID;
		double scoreProduct, scoreSum, scorePhiThetaProduct, scorePhiSum;
		int m = 0;
		int k;
		int x;
		int w;
		String word;
		int docIndex = 1;
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.segDocMapMap.entrySet()){
			
			String segSentense = entry.getValue().get(CHILDREN);
			docIndex = entry.getKey();
			
			if (segSentense.length() != 0){
				scoreProduct = 0.0;
				
				for (w =0; w < segQuestionWords.size(); w++){
					word = segQuestionWords.get(w);
					if (!ComModel2.vocabularyAnswer.ifWordExist(word)){
						continue;
					}
					wordID = ComModel2.vocabularyAnswer.getId(word);
					
					scoreSum = 0.0;
					
					for (k = 0; k < ComModel2.topicNumAnswer; k++){
						
						scorePhiThetaProduct = 0.0;
						
						scorePhiSum = 0.0;
						for (x = 0; x < ComModel2.expertiseNumAnswer; x++){
							scorePhiSum += ComModel2.phiAnswer[k][x][wordID];
						}
						scorePhiThetaProduct = scorePhiSum * ComModel2.thetaAnswer[m][k];
						
						scoreSum +=  scorePhiThetaProduct;							
					}
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}
				}
							
				if(scoreProduct > bestAnsProb){
					
					bestAnsProb = scoreProduct;
					bestAnsIndex = docIndex;
				}				
			}
			m += 1;
		}
		textPredictAnswer.setText(ComPreprocess.docMapMap.get(bestAnsIndex).get(CHILDREN2[0]));
	}
	
	/**
	 * add annotation to the map
	 */
	private void addAnnotation(){
		if(annotationMapTest.containsKey(docIndex) == false){
			if(buttonJudge1.getSelection()){
				annotationMapTest.put(docIndex, true);
			}
			else if(buttonJudge2.getSelection()){
				annotationMapTest.put(docIndex, false);
			}
			else{
				MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
				messagebox.setText("Error");
				messagebox.setMessage("请进行选择，相关或是不相关!");
				messagebox.open();
			}
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("已经进行过标注，请进入下一个");
			messagebox.open();
		}
	}
	
	/**
	 * revoke the annotation from the map
	 */
	private void revokeAnnotation(){
		if(annotationMapTest.containsKey(docIndex)){
			annotationMapTest.remove(docIndex);
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("已撤销标注，请进行下一步！");
			messagebox.open();
		}
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
	
	/**
	 * update the doc index label
	 */
	private void updateDocIndex(){
		labelPresentDoc.setText("当前第 " + docIndex + " 条");
	}
	
	/**
	 * print the detail of the doc into the text 
	 * @param docIndex : refer to the index of the print doc
	 */
	private void printDoc(int docIndex){
		textQuestionDetail.setText("");
		String childText = "问题内容";
		String childContent;
		String inputText;
		childContent = ComPreprocess.docMapMap.get(docIndex).get(childText);
		if (childContent.length() != 0){
			inputText = "【" + childText +"】：" + childContent+"\n"; 
			textQuestionDetail.append(inputText);
			textQuestionDetail.append("\n\n");
		}
	}
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
}
