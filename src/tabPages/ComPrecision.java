package tabPages;

import java.util.ArrayList;
import java.util.HashSet;
//import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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

//import preprocessing.SegWords;

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
    
    private Combo comboModelType2;
    
    private Text textPredictAns;
    
    private Text textPredictExpert;
    
    private Text textOriginalAns;
    
    private Text textOriginalExpert;
    
    private Text textAccuracyAns;
    
    private Text textAccuracyExpert;
  
    
    
//    /**
//     * the child node of the xml file 
//     */
//    private static String[] CHILDREN = 
//    	{"问题标题","问题内容","提问者用户名","提问者性别","提问者年龄","提问时间",
//    	"回复人1姓名", "回复人1职称", "回复人1分析",  "回复人1回复时间",
//    	"回复人2姓名", "回复人2职称", "回复人2分析",  "回复人2回复时间",
//    	"回复人3姓名", "回复人3职称", "回复人3分析",  "回复人3回复时间",
//    	"回复人4姓名", "回复人4职称", "回复人4分析",  "回复人4回复时间",
//    	"回复人5姓名", "回复人5职称", "回复人5分析",  "回复人5回复时间"};
    
    /**
     * the child mode of the map
     * "回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"
     */
    private static String[] CHILDREN2 = 
    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
    
    
    /**
     * the child mode of the map
     * "回复人1姓名", "回复人2姓名","回复人3姓名","回复人4姓名", "回复人5姓名"
     */
    private static String[] CHILDREN = 
    	{"回复人1姓名", "回复人2姓名","回复人3姓名","回复人4姓名", "回复人5姓名"};
    
    
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
        textQuestionDetail.setBackground(new Color(null,225,225,225));
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

		Group groupAnswer = new Group(this, SWT.BORDER);
		groupAnswer.setText("答案相关");
		groupAnswer.setBounds(10,160,660,230);
		
		Group groupPredict = new Group(groupAnswer, SWT.BORDER);
		groupPredict.setText("匹配内容");
		groupPredict.setBounds(5, 5, 320, 205);
		
		Group groupPredictAns = new Group(groupPredict, SWT.BORDER);
		groupPredictAns.setText("匹配答案");
		groupPredictAns.setBounds(5, 5, 308, 120);
		
		textPredictAns = new Text(groupPredictAns, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textPredictAns.setBounds(5, 3, 295, 95);
		textPredictAns.setBackground(new Color(null,230,230,230));
		
		Group groupPredictExpert = new Group(groupPredict, SWT.BORDER);
		groupPredictExpert.setText("匹配专家");
		groupPredictExpert.setBounds(5, 125, 308, 60);
		
		textPredictExpert = new Text(groupPredictExpert, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textPredictExpert.setBounds(5, 3, 295, 35);
		textPredictExpert.setBackground(new Color(null,230,230,230));
		
		Group groupOriginal = new Group(groupAnswer, SWT.BORDER);
		groupOriginal.setText("原有内容");
		groupOriginal.setBounds(330, 5, 320, 205);
		
		Group groupOriginalAns = new Group(groupOriginal, SWT.BORDER);
		groupOriginalAns.setText("采纳答案");
		groupOriginalAns.setBounds(5, 5, 308, 120);
		
		textOriginalAns = new Text(groupOriginalAns, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textOriginalAns.setBounds(5, 3, 295, 95);
		textOriginalAns.setBackground(new Color(null,230,230,230));
		
		Group groupOriginalExpert = new Group(groupOriginal, SWT.BORDER);
		groupOriginalExpert.setText("采纳专家");
		groupOriginalExpert.setBounds(5, 125, 308, 60);
		
		textOriginalExpert = new Text(groupOriginalExpert, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textOriginalExpert.setBounds(5, 3, 295, 35);
		textOriginalExpert.setBackground(new Color(null,230,230,230));
		
		Button buttonComfirmModel = new Button(groupDocQuestion, SWT.BORDER);
		buttonComfirmModel.setText("确认");
		buttonComfirmModel.setBounds(560, 95, 90, 32);
		buttonComfirmModel.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		getResult();
        	}
        });
		
		Label labelModelType2 = new Label(this, SWT.BORDER);
		labelModelType2.setText("请选择 Model 类型：");
		labelModelType2.setBounds(40, 390, 120, 20);
		
		comboModelType2 = new Combo(this, SWT.READ_ONLY);
		comboModelType2.setBounds(40, 410, 100, 20);
		comboModelType2.setItems(MODELTYPE);
		
		Group groupAccuracy = new Group(this, SWT.BORDER);
		groupAccuracy.setText("正确率");
		groupAccuracy.setBounds(170, 390, 500, 120);
		
		Group groupAccuracyAns = new Group(groupAccuracy, SWT.BORDER);
		groupAccuracyAns.setText("预测答案正确率：");
		groupAccuracyAns.setBounds(5, 0, 240, 100);
		
		Group groupAccuracyExpert = new Group(groupAccuracy, SWT.BORDER);
		groupAccuracyExpert.setText("预测专家正确率：");
		groupAccuracyExpert.setBounds(250, 0, 240, 100);
		
		textAccuracyAns = new Text(groupAccuracyAns, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAccuracyAns.setBounds(5, 3, 225, 75);
		textAccuracyAns.setBackground(new Color(null,230,230,230));
		
		textAccuracyExpert = new Text(groupAccuracyExpert, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAccuracyExpert.setBounds(5, 3, 225, 75);
		textAccuracyExpert.setBackground(new Color(null,230,230,230));
		
		Button buttonGetAdaptedAccuracy = new Button(this, SWT.BORDER);
		buttonGetAdaptedAccuracy.setText("获取采纳答案正确率");
		buttonGetAdaptedAccuracy.setBounds(25,440, 140, 35);
		buttonGetAdaptedAccuracy.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		getAdaptedAccuracy();
        	}
        });
		
		Button buttonGetAccuracy = new Button(this, SWT.BORDER);
		buttonGetAccuracy.setText("获取随机答案正确率");
		buttonGetAccuracy.setBounds(25,480, 140, 35);
		buttonGetAccuracy.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		getAccuracy();
        	}
        });
		
		
//		Button buttonTest = new Button(this, SWT.BORDER);
//		buttonTest.setText("Test");
//		buttonTest.setBounds(0,0, 50, 20);
//		buttonTest.addSelectionListener(new SelectionAdapter(){
//        	public void widgetSelected(SelectionEvent e){
//        		Test();
//        	}
//        });
		
	}
	
//	private void Test(){
//		double[][] test = ComModel1.thetaAnswer;
//		System.out.println("thetaAnswer size:" + test.length);		
//	}
	
	private void getAdaptedAccuracy(){
		textAccuracyAns.setText("");
		textAccuracyExpert.setText("");
		String comboString = comboModelType2.getText();
		int type = 0;
		if (comboString.equals(MODELTYPE[0])){
			// do as the model 1
			type = 1;
			getAdaptedAccuracyModel1();
			getAdaptedRandomAccuracy(type);
//			getDiffAccuracyModel1();
		}
		else if(comboString.equals(MODELTYPE[1])){
			// do as the model 2
			type = 2;
			getAdaptedAccuracyModel2();
			getAdaptedRandomAccuracy(type);
//			getDiffAccuracyModel2();
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("请先选择 Model 类型!");
			messagebox.open();					
		}
	}
	
	private void getAdaptedAccuracyModel1(){
		int mapSize = ComPreprocess.docMapMap.size();
		int docNum = 0;
		int countCorrect = 0 ;
		int predictIndex;
		for(int i = 1; i < mapSize + 1; i++){
			
			if(getAnswerNum(i) == 1){
				continue;
			}
			docNum += 1;
			
			predictIndex = getAdaptedResultModel1(i);
			if(predictIndex == 0){
				countCorrect += 1;
			}
		}
		double accuracy = countCorrect * 1.0 / docNum;
		System.out.println("答案多于1的文档数"+docNum);
		System.out.println("Model1【采纳】--推荐答案的正确率:" + accuracy);
		textAccuracyAns.setText("Model1【采纳】--推荐答案的正确率:" + accuracy + "\n"+ "\n");
	}
	
	private void getAdaptedAccuracyModel2(){
		int docNum = 0;
		int mapSize = ComPreprocess.docMapMap.size();
		int countAnsCorrect = 0 ;
		int countExpertCorrect = 0;
		int predictAnsIndex;
		int predictExpertIndex;
		ArrayList<Integer> goodIndex = new ArrayList<Integer>();
		ArrayList<Integer> secondGoodIndex = new ArrayList<Integer>();
		ArrayList<Integer> secondGoodAnsIndex = new ArrayList<Integer>();
		for(int i = 1; i < mapSize + 1; i++){
			
			if(getAnswerNum(i) == 1){
				continue;
			}
			docNum += 1;
			
			predictAnsIndex = getAdaptedResultModel2(i);
			predictExpertIndex = getAdaptedExpertModel2(i);
			if(predictAnsIndex == 0){
				countAnsCorrect += 1;
			}
			if(predictExpertIndex == 0){
				countExpertCorrect += 1;
			}
			if(predictAnsIndex == 0 && predictExpertIndex == 0){
				goodIndex.add(i);
			}
			if(predictAnsIndex == predictExpertIndex && predictExpertIndex != 0){
				secondGoodIndex.add(i);
				secondGoodAnsIndex.add(predictAnsIndex);
			}
		}
		System.out.println("答案多于1的文档数"+docNum);
		double ansAccuracy = countAnsCorrect * 1.0 / docNum;
		double expertAccuracy = countExpertCorrect * 1.0 / docNum;
		System.out.println("Model2【采纳】--推荐答案的正确率:" + ansAccuracy);
		System.out.println("Model2【采纳】--推荐专家的正确率:" + expertAccuracy);
		
//		for(int i = 0; i < secondGoodIndex.size(); i ++){
//			System.out.println(secondGoodIndex.get(i) + ", " +secondGoodAnsIndex.get(i));
//		}
		
		textAccuracyAns.setText("Model2【采纳】--推荐答案的正确率:" + ansAccuracy+ "\n"+ "\n");
		textAccuracyExpert.setText("Model2【采纳】--推荐专家的正确率:" + expertAccuracy+ "\n"+ "\n");
		
	}
	
	
	/**
	 * click the accuracy button and get the accuracy
	 */
	private void getAccuracy(){
		textAccuracyAns.setText("");
		textAccuracyExpert.setText("");
		String comboString = comboModelType2.getText();
		int type = 0;
		if (comboString.equals(MODELTYPE[0])){
			// do as the model 1
			type = 1;
			getAccuracyModel1();
			getRandomAccuracy(type);
//			getDiffAccuracyModel1();
		}
		else if(comboString.equals(MODELTYPE[1])){
			// do as the model 2
			type = 2;
			getAccuracyModel2();
			getRandomAccuracy(type);
//			getDiffAccuracyModel2();
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("请先选择 Model 类型!");
			messagebox.open();					
		}
	}
	
	private void getAccuracyModel1(){
		int mapSize = ComPreprocess.docMapMap.size();
		int docNum = mapSize;
		int countCorrect = 0 ;
		int[] predictIndex = new int[2];
//		int predictDocIndex, predictAnsIndex;
		for(int i = 1; i < mapSize + 1; i++){
			
			predictIndex = getResultModel1(i);
			
			if(predictIndex[0] == i){
				countCorrect += 1;
			}
		}
		double accuracy = countCorrect * 1.0 / docNum;
		System.out.println("Model1【随机】--推荐答案的正确率:" + accuracy);
		textAccuracyAns.setText("Model1【随机】--推荐答案的正确率:" + accuracy + "\n"+ "\n");
	}
	
	private void getAccuracyModel2(){
		int mapSize = ComPreprocess.docMapMap.size();
		int docNum = mapSize;
		int countAnsCorrect = 0 ;
		int countExpertCorrect = 0;
		int[] predictAnsIndex;
		int[] predictExpertIndex;
		for(int i = 1; i < mapSize + 1; i++){
			predictAnsIndex = getAnsModel2(i);
			predictExpertIndex = getExpertModel2(i);
			if(predictAnsIndex[0] == i){
				countAnsCorrect += 1;
			}
			if(predictExpertIndex[0] == i){
				countExpertCorrect += 1;
			}
		}
		
		double ansAccuracy = countAnsCorrect * 1.0 / docNum;
		double expertAccuracy = countExpertCorrect * 1.0 / docNum;
		System.out.println("Model2【随机】--推荐答案的正确率:" + ansAccuracy);
		System.out.println("Model2【随机】--推荐专家的正确率:" + expertAccuracy);
		
		textAccuracyAns.setText("Model1【随机】--推荐答案的正确率:" + ansAccuracy+ "\n"+ "\n");
		textAccuracyExpert.setText("Model2【随机】--推荐专家的正确率:" + expertAccuracy+ "\n"+ "\n");
		
	}
	
	
	private void getAdaptedRandomAccuracy(int type){
//		int mapSize = ComPreprocess.docMapMap.size();
		int docNum = 0; 
		int ansNum;
		double randomCorrectProb = 0.0;
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.docMapMap.entrySet()){
			
			ansNum = getAnsNum(entry.getKey());
			if(ansNum > 1){
				docNum += 1;
				randomCorrectProb += 1.0 / ansNum ;
			}
		}
		double randomCorrect = 1.0 * randomCorrectProb / docNum;
		System.out.println("答案的随机正确率:" + randomCorrect);
		textAccuracyAns.append("答案的随机正确率:" + randomCorrect+ "\n" + "\n");
		if(type != 1){
			textAccuracyExpert.append("答案的随机正确率:" + randomCorrect+ "\n"+ "\n");
		}
	}
	
	private void getRandomAccuracy(int type){
		int docNum = ComPreprocess.docMapMap.size();
//		int mapSize = ComPreprocess.docMapMap.size();
		int ansNum;
		double randomCorrectProb = 0.0;
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.docMapMap.entrySet()){
			ansNum = 0;
			for(int i = 0; i < CHILDREN2.length;i ++){
				if(entry.getValue().get(CHILDREN2[i]).length() != 0){
					ansNum += 1;
				}
			}
			
			randomCorrectProb += 1.0 * (ansNum) / (ansNum + 4);
			
		}
		double randomCorrect = randomCorrectProb / docNum;
//		System.out.println("答案多于1的问题数： " + docNum);
		System.out.println("答案的随机正确率:" + randomCorrect);
		textAccuracyAns.append("答案的随机正确率:" + randomCorrect+ "\n" + "\n");
		
		if(type != 1){
			textAccuracyExpert.append("答案的随机正确率:" + randomCorrect+ "\n"+ "\n");
		}
	}
	
	
	private int getAnsNum(int index){
		int count = 0; 
		for(int i = 0; i < CHILDREN2.length; i ++){
			if(ComPreprocess.docMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				count += 1;
			}
		}
		return count;
	}

	
	/**
	 * click the "comfirm" button and choose which the type of model
	 */
	private void getResult(){
		String comboString = comboModelType.getText();
//		System.out.println(comboString);
		if (comboString.equals(MODELTYPE[0])){
			// do as the model 1
//			int[] predictIndex = getResultModel1(docIndex);
//			textPredictAns.setText(ComPreprocess.docMapMap.get(predictIndex[0]).get(CHILDREN2[predictIndex[1]]));
//			textPredictAns.setText("问题【"+ predictIndex[0] + "】，答案【"+ predictIndex[1] + "】");
//			textOriginalAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[0]));
			int predictIndex = getAdaptedResultModel1(docIndex);
			textPredictAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[predictIndex]));
			textPredictAns.append("问题【"+ docIndex + "】，答案【"+ predictIndex + "】");
			textOriginalAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[0]));
			
		}
		else if(comboString.equals(MODELTYPE[1])){
			// do as the model 2
//			int[] predictAnsIndex = getAnsModel2(docIndex);
//			textPredictAns.setText(ComPreprocess.docMapMap.get(predictAnsIndex[0]).get(CHILDREN2[predictAnsIndex[1]]));
//			textPredictAns.setText("问题【"+ predictAnsIndex[0] + "】，答案【"+ predictAnsIndex[1] + "】");
//			textOriginalAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[0]));
//			
//			int[] predictExpertIndex = getExpertModel2(docIndex);
//			textPredictExpert.setText(ComPreprocess.docMapMap.get(predictExpertIndex[0]).get(CHILDREN[predictExpertIndex[1]]));
//			textPredictExpert.setText("问题【"+ predictExpertIndex[0] + "】，答案【"+ predictExpertIndex[1] + "】");
//			textOriginalExpert.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN[0]));	
			
			int predictAnsIndex = getAdaptedResultModel2(docIndex);
			textPredictAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[predictAnsIndex]));
			textPredictAns.append("\n问题【"+ docIndex + "】，答案【"+ predictAnsIndex + "】");
			textOriginalAns.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN2[0]));
			
			int predictExpertIndex = getAdaptedExpertModel2(docIndex);
			textPredictExpert.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN[predictExpertIndex]));
			textPredictExpert.append("\n问题【"+ docIndex + "】，答案【"+ predictExpertIndex + "】");
			textOriginalExpert.setText(ComPreprocess.docMapMap.get(docIndex).get(CHILDREN[0]));	
			
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("请先选择 Model 类型!");
			messagebox.open();					
		}
	}
	
	/**
	 * get the best answer for Model 1 among the answers the question provide
	 * @param index     the index of doc from the Map<Integer, <String,String>> <br>starting from 1
	 * @param lambda	parameter lower than 1 ---- {0.2,0.3,0.4,0.5,0.6,0.7,0.8}
	 * @param mu		parameter {10,100,500,1000,2000}
	 * @return the fit answer's index
	 */
	private int[] getResultModel1(int index){
//		String segSentense;
//		String childNode;
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum;
		double sum = 0.0;
		int k;
		double bestAnsProb = 0.0;
		int bestDocIndex = 0;
		int bestAnsIndex = 0;
		int docIndex;
		int ansIndex;
		int[] result = new int[2];
		
		ArrayList<Double> ansProbList = new ArrayList<Double>();
		Set<int[]> hs = new HashSet<int[]>();
		int[] indexSet = new int[2];
		Random random = new Random();
		int docNum = ComPreprocess.numQuestion;
		int randomDocIndex, randomAnsIndex;
		ArrayList<int[]> indexList = new ArrayList<int[]>();
		
		// random 9 random answer index 
		while (indexList.size() < 4) {
			randomDocIndex = random.nextInt(docNum) + 1;
			randomAnsIndex =  random.nextInt(5);
            if(ComPreprocess.segDocMapMap.get(randomDocIndex).get(CHILDREN2[randomAnsIndex]).length()!=0){
            	indexSet[0] = randomDocIndex;
            	indexSet[1] = randomAnsIndex;
            	hs.add(indexSet);
            	indexList.add(indexSet.clone());
            }
        }
		
		for(int i = 0; i< CHILDREN2.length; i++){
			if(ComPreprocess.segDocMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				indexSet[0] = index;
				indexSet[1] = i;
				indexList.add(indexSet.clone());
			}
		}
		
		for (int i = 0; i < indexList.size(); i++){
			String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
			words = questionContent.split(" ");
			
			scoreProduct = 0.0;
			
			
			docIndex = indexList.get(i)[0];
			ansIndex = indexList.get(i)[1];			
		
			for(String word : words){
				if (!ComModel1.vocabularyAnswer.ifWordExist(word)){
					continue;
				}
				wordID = ComModel1.vocabularyAnswer.getId(word);
				scoreSum = 0.0;
				for (k = 0; k < ComModel1.topicNumAnswer; k++){
					scoreSum += ComModel1.phiAnswer[k][wordID] * 
							ComModel1.thetaAnswer[(docIndex-1)*5 + ansIndex][k];
				}				
				
				if (scoreProduct == 0.0 || scoreSum == 0.0){
					scoreProduct = scoreSum;
				}
				else{
					scoreProduct *= scoreSum;
				}			
				
			}
			
			sum += scoreProduct;
							
			ansProbList.add(scoreProduct);
			
			if(scoreProduct > bestAnsProb){
				bestAnsProb = scoreProduct;
				bestDocIndex = indexList.get(i)[0];
				bestAnsIndex = indexList.get(i)[1];
			}
			
		}
			
		
		int temp;
		for(int j = 0; j < ansProbList.size();j ++){
			temp = j + 1;
			System.out.println("文档【"+ index + "】回答【"+ temp +"】概率：" + ansProbList.get(j) * 1.0 / sum);
		}
		System.out.println("最佳：" + "文档"+ bestDocIndex + ",回答"+ bestAnsIndex + ",概率："+ bestAnsProb * 1.0 / sum + "\n");
		
		result[0] = bestDocIndex;
		result[1] = bestAnsIndex;
		
		return result;
		
	}
	
	
	/**
	 * get the best answer for Model 2 among the answers the question provide
	 * @param index
	 * @param lambda
	 * @param mu
	 * @return
	 */
	private int[] getAnsModel2(int index){
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum, scorePhiThetaProduct, scorePhiSum;
		double sum = 0.0;
		int k, x;
		double bestAnsProb = 0.0;
		int bestDocIndex = 0;
		int bestAnsIndex = 0;
		int docIndex;
		int ansIndex;
		int[] result = new int[2];
		
		ArrayList<Double> ansProbList = new ArrayList<Double>();
		Set<int[]> hs = new HashSet<int[]>();
		int[] indexSet = new int[2];
		Random random = new Random();
		int docNum = ComPreprocess.numQuestion;
		int randomDocIndex, randomAnsIndex;
		ArrayList<int[]> indexList = new ArrayList<int[]>();
		
		// random 9 random answer index 
		while (indexList.size() < 4) {
			randomDocIndex = random.nextInt(docNum) + 1;
			randomAnsIndex =  random.nextInt(5);
            if(ComPreprocess.segDocMapMap.get(randomDocIndex).get(CHILDREN2[randomAnsIndex]).length()!=0){
            	indexSet[0] = randomDocIndex;
            	indexSet[1] = randomAnsIndex;
            	hs.add(indexSet);
            	indexList.add(indexSet.clone());
            }
        }
		
		for(int i = 0; i< CHILDREN2.length; i++){
			if(ComPreprocess.segDocMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				indexSet[0] = index;
				indexSet[1] = i;
				indexList.add(indexSet.clone());
			}
		}
		
		String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
		words = questionContent.split(" ");
		
		for (int i = 0; i < indexList.size(); i++){
			
			scoreProduct = 0.0;
						
			docIndex = indexList.get(i)[0];
			ansIndex = indexList.get(i)[1];
			
			for(String word : words){
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
					scorePhiThetaProduct = scorePhiSum * 
							ComModel2.thetaAnswer[(docIndex - 1) * 5 + ansIndex][k];
					
					scoreSum +=  scorePhiThetaProduct;	
				}
				
				if (scoreProduct == 0.0 || scoreSum == 0.0){
					scoreProduct = scoreSum;
				}
				else{
					scoreProduct *= scoreSum;
				}	
			}
			sum += scoreProduct;
			
			ansProbList.add(scoreProduct);
			
			if(scoreProduct > bestAnsProb){
				bestAnsProb = scoreProduct;
				bestDocIndex = indexList.get(i)[0];
				bestAnsIndex = indexList.get(i)[1];
			}			
		}
		
		int temp;
		for(int j = 0; j < ansProbList.size();j ++){
			temp = j + 1;
			System.out.println("文档【"+ index + "】回答【"+ temp +"】概率：" + ansProbList.get(j) * 1.0 / sum);
		}
		System.out.println("最佳：" + "文档"+ bestDocIndex + ",回答"+ bestAnsIndex + ",概率："+ bestAnsProb * 1.0 / sum + "\n");
		
		result[0] = bestDocIndex;
		result[1] = bestAnsIndex;
		
		return result;
	}
	
	private int[] getExpertModel2(int index){
//		String segSentense;
//		String childNode, childNode2;
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum, scorePhiPsiProduct, scorePhiSum;
		double sum = 0.0;
		int k, x;
		double bestExpertProb = 0.0;
		int bestDocIndex = 0;
		int bestAnsIndex = 0;
		int docIndex;
		int ansIndex;
		int[] result = new int[2];
		
		ArrayList<Double> ansProbList = new ArrayList<Double>();
		Set<int[]> hs = new HashSet<int[]>();
		int[] indexSet = new int[2];
		Random random = new Random();
		int docNum = ComPreprocess.numQuestion;
		int randomDocIndex, randomAnsIndex;
		ArrayList<int[]> indexList = new ArrayList<int[]>();
		
		String userName;
		int u; 
		
		while (indexList.size() < 4) {
			randomDocIndex = random.nextInt(docNum) + 1;
			randomAnsIndex =  random.nextInt(5);
            if(ComPreprocess.segDocMapMap.get(randomDocIndex).get(CHILDREN2[randomAnsIndex]).length()!=0){
            	indexSet[0] = randomDocIndex;
            	indexSet[1] = randomAnsIndex;
            	hs.add(indexSet);
            	indexList.add(indexSet.clone());
            }
        }
		
		for(int i = 0; i< CHILDREN2.length; i++){
			if(ComPreprocess.segDocMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				indexSet[0] = index;
				indexSet[1] = i;
				indexList.add(indexSet.clone());
			}
		}
		
		String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
		words = questionContent.split(" ");

		for (int i = 0; i < indexList.size(); i++){
			
			
			scoreProduct = 0.0;
						
			docIndex = indexList.get(i)[0];
			ansIndex = indexList.get(i)[1];
			
			userName = ComPreprocess.docMapMap.get(docIndex).get(CHILDREN[ansIndex]);
			u = ComModel2.userAnswer.getId(userName);
			
			for(String word : words){
				if (!ComModel2.vocabularyAnswer.ifWordExist(word)){
					continue;
				}
				wordID = ComModel2.vocabularyAnswer.getId(word);
				scoreSum = 0.0;
				
				for (x = 0; x < ComModel2.expertiseNumAnswer; x++){
					
					scorePhiPsiProduct = 0.0;						
					scorePhiSum = 0.0;
					
					for(k = 0; k < ComModel2.topicNumAnswer; k ++){
						scorePhiSum += ComModel2.phiAnswer[k][x][wordID];
					}
					
					scorePhiPsiProduct = scorePhiSum * ComModel2.psiAnswer[u][x];
					scoreSum +=  scorePhiPsiProduct;
				}
				
				if (scoreProduct == 0.0 || scoreSum == 0.0){
					scoreProduct = scoreSum;
				}
				else{
					scoreProduct *= scoreSum;
				}	
				
			}
			sum += scoreProduct;
			
			ansProbList.add(scoreProduct);
			
			if(scoreProduct >= bestExpertProb){
				bestExpertProb = scoreProduct;
				bestDocIndex = indexList.get(i)[0];
				bestAnsIndex = indexList.get(i)[1];
			}			
		}

		int temp;
		for(int j = 0; j < ansProbList.size();j ++){
			temp = j + 1;
			System.out.println("【专家】文档【"+ index + "】回答【"+ temp +"】概率：" + ansProbList.get(j) * 1.0 / sum);
		}
		System.out.println("【专家】最佳：" + "文档"+ bestDocIndex + ",回答"+ bestAnsIndex + ",概率："+ bestExpertProb * 1.0 / sum + "\n");
		
		result[0] = bestDocIndex;
		result[1] = bestAnsIndex;
		
		return result;
	
	}
	
	private int getAdaptedResultModel1(int index){
//		String segSentense;
//		String childNode;
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum;
		double sum = 0.0;
		int k;
		double bestAnsProb = 0.0;
		int bestAnsIndex = 0;
	
		String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
		words = questionContent.split(" ");
		
		for(int i = 0 ; i < CHILDREN2.length; i ++){
			scoreProduct = 0.0;
			if (ComPreprocess.docMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				for(String word : words){
					if (!ComModel1.vocabularyAnswer.ifWordExist(word)){
						continue;
					}
					wordID = ComModel1.vocabularyAnswer.getId(word);
					scoreSum = 0.0;
					for (k = 0; k < ComModel1.topicNumAnswer; k++){
						scoreSum += ComModel1.phiAnswer[k][wordID] * 
								ComModel1.thetaAnswer[(index-1)*5 + i][k];
					}				
					
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}			
					
				}
				
				sum += scoreProduct;
				
				if(scoreProduct > bestAnsProb){
					bestAnsProb = scoreProduct;
					
					bestAnsIndex = i;
				}

			}
		}
		
		System.out.println("最佳回答："+ bestAnsIndex + ",概率："+ bestAnsProb * 1.0 / sum + "\n");
		
		return bestAnsIndex;
		
	}
		
	private int getAdaptedResultModel2(int index){
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum, scorePhiThetaProduct, scorePhiSum;
		double sum = 0.0;
		int k, x;
		double bestAnsProb = 0.0;
		int bestAnsIndex = 0;
		
		String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
		words = questionContent.split(" ");
		
		for(int i = 0; i< CHILDREN2.length; i++){
			
			if(ComPreprocess.docMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				scoreProduct = 0.0;
				
				for(String word : words){
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
						scorePhiThetaProduct = scorePhiSum * 
								ComModel2.thetaAnswer[(index - 1) * 5 + i][k];
						
						scoreSum +=  scorePhiThetaProduct;	
					}
					
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}	
					
				}
				sum += scoreProduct;
				
				if(scoreProduct > bestAnsProb){
					bestAnsProb = scoreProduct;
					bestAnsIndex = i;
				}			
			}
		}
	
		System.out.println("最佳回答："+ bestAnsIndex + ",概率："+ bestAnsProb * 1.0 / sum + "\n");
		
		
		return bestAnsIndex;
	}
	
	private int getAdaptedExpertModel2(int index){
		String[] words;
		int wordID;	
		double scoreProduct, scoreSum, scorePhiPsiProduct, scorePhiSum;
		double sum = 0.0;
		int k, x;
		double bestExpertProb = 0.0;
		int bestAnsIndex = 0;
		
		String userName; 
		int u;
		
		String questionContent =ComPreprocess.segDocMapMap.get(index).get("问题内容");
		words = questionContent.split(" ");
		
		for(int i = 0; i< CHILDREN2.length; i++){
			
			if(ComPreprocess.docMapMap.get(index).get(CHILDREN2[i]).length() != 0){
				scoreProduct = 0.0;
				userName = ComPreprocess.docMapMap.get(index).get(CHILDREN[i]);
				u = ComModel2.userAnswer.getId(userName);
				
				for(String word : words){
					if (!ComModel2.vocabularyAnswer.ifWordExist(word)){
						continue;
					}
					wordID = ComModel2.vocabularyAnswer.getId(word);
					scoreSum = 0.0;
					
					for (x = 0; x < ComModel2.expertiseNumAnswer; x++){
						
						scorePhiPsiProduct = 0.0;						
						scorePhiSum = 0.0;
						
						for(k = 0; k < ComModel2.topicNumAnswer; k ++){
							scorePhiSum += ComModel2.phiAnswer[k][x][wordID];
						}
						
						scorePhiPsiProduct = scorePhiSum * ComModel2.psiAnswer[u][x];
						scoreSum +=  scorePhiPsiProduct;
					}
					
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}
				}
				
				sum += scoreProduct;
				
				
				if(scoreProduct >= bestExpertProb){
					bestExpertProb = scoreProduct;
					bestAnsIndex = i;
				}			
				
			}
		}
		
		
		System.out.println("【专家】最佳回答："+ bestAnsIndex + ",概率："+ bestExpertProb * 1.0 / sum + "\n");
		
		return bestAnsIndex;
	}
	
	/**
	 * update the doc index label
	 */
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
			inputText = "【" + childText +"】：" + childContent+"\n"; 
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
			childContent = ComPreprocess.docMapMap.get(questionIndex).get(childText);
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
