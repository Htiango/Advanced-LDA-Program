package tabPages;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
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

import preprocessing.SegWords;


public class ComPrediction extends Composite{
	
//	/**
//	 * the map index, the child node and the prob of the top 1,2,3 best ans 
//	 */
//	private Map<Integer, Map<String, Double>> mapBestAns;
	
	/**
	 * type in the question that need to get the best answer and the best user
	 */
	private StyledText textQuestion;
	
	/**
	 * detail about the 1st best answer 
	 */
	private StyledText textAns1;
	
	/**
	 * detail about the 2nd best answer 
	 */
	private StyledText textAns2;
	
	/**
	 * detail about the 3rd best answer 
	 */
	private StyledText textAns3;
	
	private Combo comboModelType;
	
	private Text textUser1;
	
	private Text textUser2;
	
	private Text textUser3;
	
	private Group groupGetAnswer;
	
	private Label labelAns1Prob;
	
	private Label labelAns2Prob;
	
	private Label labelAns3Prob;
	
	private Label labelUser1Prob;

	private Label labelUser2Prob;

	private Label labelUser3Prob;

	
	private final String[] MODELTYPE = {"Model 1", "Model 2"};
	
	/**
     * the child mode of the segged map
     */
    private static String[] CHILDREN = 
    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};

    /**
     * The child mode of the question content from the segged map
     */
    private static String CHILDREN2 = "问题内容";

//    /**
//     * the child node of the xml file 
//     */
//    private static String[] CHILDREN2 = 
//    	{"回复人1姓名", "回复人2姓名", "回复人3姓名", "回复人4姓名", "回复人5姓名"};
		
	public ComPrediction(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Label labelQuestion = new Label(this, SWT.BORDER  );
		labelQuestion.setText("输入预测问题:");
		labelQuestion.setBounds(12 , 0, 80, 15);
		
		textQuestion = new StyledText(this, SWT.BORDER| SWT.V_SCROLL |  SWT.WRAP);
		textQuestion.setBounds(12, 15, 655, 100);
		
		Label labelModelType = new Label(this, SWT.BORDER);
		labelModelType.setText("请选择 Model 类型：");
		labelModelType.setBounds(350, 125, 120, 20);
		
		comboModelType = new Combo(this, SWT.READ_ONLY);
		comboModelType.setBounds(470, 125, 80, 20);
		comboModelType.setItems(MODELTYPE);
		
		groupGetAnswer = new Group(this, SWT.BORDER);
		groupGetAnswer.setText("显示预测答案");
		groupGetAnswer.setBounds(10, 150, 420, 360);
				
		Group groupGetUser = new Group (this, SWT.BORDER);
		groupGetUser.setText("显示推荐专家");
		groupGetUser.setBounds(440, 150, 230, 250);
		
		Group groupAns1 = new Group(groupGetAnswer, SWT.NONE);
		groupAns1.setText("推荐答案1");
		groupAns1.setBounds(5 , 5, 405, 105);

		Group groupAns2 = new Group(groupGetAnswer, SWT.NONE);
		groupAns2.setText("推荐答案2");
		groupAns2.setBounds(5 , 110, 405, 105);
		
		Group groupAns3 = new Group(groupGetAnswer, SWT.NONE);
		groupAns3.setText("推荐答案3");
		groupAns3.setBounds(5 , 215, 405, 105);

		textAns1 = new StyledText(groupAns1, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns1.setBounds(0, 0 , 310, 85);
		
		Label labelAns1 = new Label(groupAns1, SWT.BORDER);
		labelAns1.setText("答案匹配率");
		labelAns1.setBounds(320, 10, 70, 20);
		
		labelAns1Prob = new Label(groupAns1, SWT.WRAP);
		labelAns1Prob.setBounds(310, 40, 90, 40);
		
		textAns2 = new StyledText(groupAns2, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns2.setBounds(0, 0 , 310, 85);
		
		Label labelAns2 = new Label(groupAns2, SWT.BORDER);
		labelAns2.setText("答案匹配率");
		labelAns2.setBounds(320, 10, 70, 20);

		labelAns2Prob = new Label(groupAns2, SWT.WRAP);
		labelAns2Prob.setBounds(310, 40, 90, 40);
		
		textAns3 = new StyledText(groupAns3, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns3.setBounds(0, 0 , 310, 85);
		
		Label labelAns3 = new Label(groupAns3, SWT.BORDER);
		labelAns3.setText("答案匹配率");
		labelAns3.setBounds(320, 10, 70, 20);
		
		labelAns3Prob = new Label(groupAns3, SWT.WRAP);
		labelAns3Prob.setBounds(310, 40, 90, 40);
		
		Group groupUser1 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser1.setBounds(5,5, 215, 70);
		groupUser1.setText("推荐专家1");

		Group groupUser2 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser2.setBounds(5,80, 215, 70);
		groupUser2.setText("推荐专家2");		
		
		Group groupUser3 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser3.setBounds(5,155, 215, 70);
		groupUser3.setText("推荐专家3");

		textUser1 = new Text(groupUser1, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser1.setBounds(0, 0, 120, 50);
		
		Label labelUser1 = new Label(groupUser1, SWT.BORDER);
		labelUser1.setText("专家匹配率");
		labelUser1.setBounds(125, 5, 80, 20);
		
		labelUser1Prob = new Label(groupUser1, SWT.NONE);
		labelUser1Prob.setBounds(120, 30, 90, 20);
	
		textUser2 = new Text(groupUser2, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser2.setBounds(0, 0, 120, 50);
		
		Label labelUser2 = new Label(groupUser2, SWT.BORDER);
		labelUser2.setText("专家匹配率");
		labelUser2.setBounds(125, 5, 80, 20);
		
		labelUser2Prob = new Label(groupUser2, SWT.NONE);
		labelUser2Prob.setBounds(120, 30, 90, 20);
		
		textUser3 = new Text(groupUser3, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser3.setBounds(0, 0, 120, 50);
		
		Label labelUser3 = new Label(groupUser3, SWT.BORDER);
		labelUser3.setText("专家匹配率");
		labelUser3.setBounds(125, 5, 80, 20);
		
		labelUser3Prob = new Label(groupUser3, SWT.NONE);
		labelUser3Prob.setBounds(120, 30, 90, 20);
		
		Button buttonComfirm = new Button(this, SWT.BORDER);
		buttonComfirm.setText("确认");
		buttonComfirm.setBounds(580, 120, 90, 32);
		buttonComfirm.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				getResult();
			}
		});
	}
	
	/**
	 * click the "comfirm" button and choose which the type of model
	 */
	private void getResult(){
		String comboString = comboModelType.getText();
		System.out.println(comboString);
		int type = ComPreprocess.indexType;
		if (comboString.equals(MODELTYPE[0])){
			// do as the model 1
			getResultModel1(type);
		}
		else if(comboString.equals(MODELTYPE[1])){
			// do as the model 2
			getResultModel2(type);
		}
		else{
			MessageBox messagebox=new MessageBox(getShell(),SWT.YES|SWT.ICON_ERROR);
			messagebox.setText("Error");
			messagebox.setMessage("请先选择 Model 类型!");
			messagebox.open();					
		}
	}
	
	/**
	 * get the top 3 best answer for Model 1
	 */
	private void getResultModel1(int type){
		String questionContent = textQuestion.getText();
		List<String> segQuestionWords;
		segQuestionWords = SegWords.segQuestionWords(questionContent, type);
		System.out.println(segQuestionWords);
		System.out.println("【phi矩阵】" + ComModel1.phiAnswer);
		System.out.println("【theta长度】"+ComModel1.thetaAnswer.length);
		System.out.println("【theta矩阵】"+ComModel1.thetaAnswer);
		
//		int best1AnsInteger = 0, best2AnsInteger = 0, best3AnsInteger = 0;
		int best1AnsIndex = 0, best2AnsIndex = 0 , best3AnsIndex = 0;
		double best1AnsProb = 0.0, best2AnsProb = 0.0, best3AnsProb = 0.0;
		int wordID;
		double scoreProduct, scoreSum;
		int m = 0;
		int k;
		int w;
		String word;
		double sum = 0.0;
		int docIndex = 1;
		
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.segDocMapMap.entrySet()){
			String segSentense = entry.getValue().get(CHILDREN2);
			
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
				sum += scoreProduct;
				
				if(scoreProduct > best1AnsProb){
					
					best3AnsProb = best2AnsProb;
					best3AnsIndex = best2AnsIndex;
					
					best2AnsProb = best1AnsProb;
					best2AnsIndex = best1AnsIndex;
					
					best1AnsProb = scoreProduct;
					best1AnsIndex = docIndex;
				}
				
				else if(scoreProduct > best2AnsProb){
					
					best3AnsProb = best2AnsProb;
					best3AnsIndex = best2AnsIndex;
					
					best2AnsProb = scoreProduct;
					best2AnsIndex = docIndex;
				}
				
				else if(scoreProduct > best3AnsProb){
					best3AnsProb = scoreProduct;
					best3AnsIndex = docIndex;
				}					
				
			}
			m += 1;
//			for( i = 0; i < CHILDREN.length; i++){
//				String segSentense = entry.getValue().get(CHILDREN[i]);
//				
//				if (segSentense.length() != 0){
//					
//					
//					scoreProduct = 0.0;
//					
//					for (w =0; w < segQuestionWords.size(); w++){
//						word = segQuestionWords.get(w);
//						if (!ComModel1.vocabularyAnswer.ifWordExist(word)){
//							continue;
//						}
//						wordID = ComModel1.vocabularyAnswer.getId(word);
//						scoreSum = 0.0;
//						for (k = 0; k < ComModel1.topicNumAnswer; k++){
//							scoreSum += ComModel1.phiAnswer[k][wordID] * ComModel1.thetaAnswer[m][k];
//						}
//						if (scoreProduct == 0.0 || scoreSum == 0.0){
//							scoreProduct = scoreSum;
//						}
//						else{
//							scoreProduct *= scoreSum;
//						}
//					}	
//					sum += scoreProduct;
//					
//					if(scoreProduct > best1AnsProb){
//						
//						best3AnsProb = best2AnsProb;
//						best3AnsInteger = best2AnsInteger;
//						best3AnsI = best2AnsI;
//						
//						best2AnsProb = best1AnsProb;
//						best2AnsInteger = best1AnsInteger;
//						best2AnsI = best1AnsI;
//						
//						best1AnsProb = scoreProduct;
//						best1AnsInteger = entry.getKey();
//						best1AnsI = i;
//					}
//					
//					else if(scoreProduct > best2AnsProb){
//						
//						best3AnsProb = best2AnsProb;
//						best3AnsInteger = best2AnsInteger;
//						best3AnsI = best2AnsI;
//						
//						best2AnsProb = scoreProduct;
//						best2AnsInteger = entry.getKey();
//						best2AnsI = i;
//					}
//					
//					else if(scoreProduct > best3AnsProb){
//						best3AnsProb = scoreProduct;
//						best3AnsInteger = entry.getKey();
//						best3AnsI = i;
//					}					
//					
//				}
//				m += 1;
//			}
		}
		System.out.println("总文档数(包括为空的)：" + m);
		System.out.println("总概率：" + sum);
		showTopicColor();
		changeWordColor(textQuestion, SegWords.listToString(segQuestionWords));
		printAnswer(1, ComPreprocess.docMapMap.get(best1AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best1AnsIndex).get(CHILDREN[0]), best1AnsProb / sum, true);
		printAnswer(2, ComPreprocess.docMapMap.get(best2AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best2AnsIndex).get(CHILDREN[0]), best2AnsProb / sum, true);
		printAnswer(3, ComPreprocess.docMapMap.get(best3AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best3AnsIndex).get(CHILDREN[0]), best3AnsProb / sum, true);
	}
	
	private void showTopicColor(){
		int startPixel = 5;
		int topicNumber = ComModel1.topicNumAnswer;
		int labelWidth = 390 / topicNumber;
		Label[] labelTopicColor = new Label[topicNumber];
		for(int i = 0; i < topicNumber; i++){
			labelTopicColor[i] = new Label(groupGetAnswer, SWT.NONE);
			labelTopicColor[i].setBounds(startPixel, 320, labelWidth, 20);
			labelTopicColor[i].setBackground(ComModel1.COLORTOPIC[topicNumber/5-1][i]);
			labelTopicColor[i].setText(i + "");
			startPixel += labelWidth;
		}
	}
	
	/**
	 * print the recommended expert
	 * @param index
	 * @param expert
	 * @param prob
	 */
	private void printExpert(int index, String expert, double prob){
		if (index == 1){
			textUser1.setText(expert);
			labelUser1Prob.setText(prob + "");
		}
		else if (index == 2){
			textUser2.setText(expert);
			labelUser2Prob.setText(prob + "");

		}	
		else{
			textUser3.setText(expert);
			labelUser3Prob.setText(prob + "");
		}
		System.out.println(expert);
		System.out.println(prob);
		
	}
	
	/**
	 * print the recommended answer, and show different color based on the topics in model1.
	 * @param index
	 * @param content
	 * @param segContent
	 * @param prob
	 * @param ifShowColor
	 */
	private void printAnswer(int index, String content,  String segContent, double prob, boolean ifShowColor){
//		String content = ComPreprocess.docMapMap.get(bestAnsInteger).get(CHILDREN[bestAnsI]);
		if (index == 1){
			textAns1.setText(content);
			labelAns1Prob.setText(prob + "");
			if(ifShowColor == true){
				changeWordColor(textAns1, segContent);
			}
		}
		else if (index == 2){
			textAns2.setText(content);
			labelAns2Prob.setText(prob + "");
			if(ifShowColor == true){
				changeWordColor(textAns2, segContent);
			}
		}
		else{
			textAns3.setText(content);
			labelAns3Prob.setText(prob + "");
			if(ifShowColor == true){
				changeWordColor(textAns3, segContent);
			}
		}
		System.out.println(prob);
	}
	
	/**
	 * get all the segged words from the previous Map
	 * @param docIndex
	 * @return 
	 */
	private ArrayList<String> getWords(String segSentense){
		ArrayList<String> words = new ArrayList<String>();
//		String childText;
//		String segSentense;
//		segSentense = ComPreprocess.segDocMapMap.get(ansIndex).get(childText);
		if (segSentense.length() != 0){
			String[] wordsSeg = segSentense.split("\\s");
			for (int j = 0; j < wordsSeg.length; j ++){
				words.add(wordsSeg[j]);
			}
		}
		return words;
	}
	
	/**
	 * the method to change the words color to be the same as its topic
	 */
	private void changeWordColor(StyledText text, String segContent){
		String sentensePrint = text.getText();
		ArrayList<String> words = new ArrayList<String>();
		words = getWords(segContent);
		
		int topicIndex;
		
		
		String word;
		Color color;
		for (int i = 0; i < words.size(); i ++){
			word = words.get(i);
			if(!ComModel1.vocabularyQuestion.ifWordExist(word))
				continue;
			topicIndex = getTopicIndex(word);
			int startIndex = 0;
	        startIndex = sentensePrint.indexOf(word, startIndex);
	        color = ComModel1.COLORTOPIC[ComModel1.topicNumAnswer / 5 - 1][topicIndex];
	        
	        StyleRange style = new StyleRange();
	        
	        while(startIndex > -1){
	        	
	        	
	            style.start = startIndex;
	            style.length = word.length();
	                      
	            style.foreground = color;
	            text.setStyleRange(style);
	            startIndex = sentensePrint.indexOf(word, startIndex + 1);
	        }
		}
	}
	
	/**
	 * get the most possible topic of a word
	 * @param word
	 * @return topic index
	 */
	private int getTopicIndex(String word){
		int topicIndex = 0;	
		if(!ComModel1.vocabularyQuestion.ifWordExist(word))
			return -1;
		int id = ComModel1.vocabularyQuestion.getId(word);
		double possibilityMax = ComModel1.phiAnswer[topicIndex][id];
		double possibility;
		for (int i = 1 ; i < ComModel1.topicNumAnswer; i ++){
			possibility = ComModel1.phiAnswer[i][id];
			if (possibility > possibilityMax){
				topicIndex = i;
				possibilityMax = possibility;
			}
		}
		return topicIndex;
	}
	
	/**
	 * get the top 3 best answers and top 3 best user2 for Model2
	 */
	private void getResultModel2(int type){
		String questionContent = textQuestion.getText();
		List<String> segQuestionWords;
		segQuestionWords = SegWords.segQuestionWords(questionContent, type);
		getAnsModel2(segQuestionWords);
		getExpertModel2(segQuestionWords);
	}
	
	/**
	 * get and print the top 3 best answers for model2
	 * @param segQuestionWords
	 */
	private void getAnsModel2(List<String> segQuestionWords){
//		int best1AnsInteger = 0, best2AnsInteger = 0, best3AnsInteger = 0;
		int best1AnsIndex = 0, best2AnsIndex = 0 , best3AnsIndex = 0;
		double best1AnsProb = 0.0, best2AnsProb = 0.0, best3AnsProb = 0.0;
		int wordID;
		double scoreProduct, scoreSum, scorePhiThetaProduct, scorePhiSum;
		int m = 0;
		int k;
		int x;
		int i;
		int w;
		String word;
		double sum = 0.0;
		int docIndex = 1;
		for(Map.Entry<Integer, Map<String, String>> entry : ComPreprocess.segDocMapMap.entrySet()){
			
			String segSentense = entry.getValue().get(CHILDREN2);
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
						
//						if (scorePhiThetaProduct == 0.0 || scorePhiSum == 0.0){
//							scorePhiThetaProduct = scorePhiSum;
//						}
//						else{
//							scorePhiThetaProduct *= scorePhiSum;
//						}
						
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
				
				if(scoreProduct > best1AnsProb){
					
					best3AnsProb = best2AnsProb;
					best3AnsIndex = best2AnsIndex;
					
					best2AnsProb = best1AnsProb;
					best2AnsIndex = best1AnsIndex;
					
					best1AnsProb = scoreProduct;
					best1AnsIndex = docIndex;
				}
				
				else if(scoreProduct > best2AnsProb){
					
					best3AnsProb = best2AnsProb;
					best3AnsIndex = best2AnsIndex;
					
					best2AnsProb = scoreProduct;
					best2AnsIndex = docIndex;
				}
				
				else if(scoreProduct > best3AnsProb){
					best3AnsProb = scoreProduct;
					best3AnsIndex = docIndex;
				}
				
			}
			m += 1;
			
//			for( i = 0; i < CHILDREN.length; i++){
				
//				String segSentense = entry.getValue().get(CHILDREN[i]);
//				
//				if (segSentense.length() != 0){
//					scoreProduct = 0.0;
//					
//					for (w =0; w < segQuestionWords.size(); w++){
//						word = segQuestionWords.get(w);
//						if (!ComModel2.vocabularyAnswer.ifWordExist(word)){
//							continue;
//						}
//						wordID = ComModel2.vocabularyAnswer.getId(word);
//						
//						scoreSum = 0.0;
//						
//						for (k = 0; k < ComModel2.topicNumAnswer; k++){
//							
//							scorePhiThetaProduct = 0.0;
//							
//							scorePhiSum = 0.0;
//							for (x = 0; x < ComModel2.expertiseNumAnswer; x++){
//								scorePhiSum += ComModel2.phiAnswer[k][x][wordID];
//							}
//							scorePhiThetaProduct = scorePhiSum * ComModel2.thetaAnswer[m][k];
//							
////							if (scorePhiThetaProduct == 0.0 || scorePhiSum == 0.0){
////								scorePhiThetaProduct = scorePhiSum;
////							}
////							else{
////								scorePhiThetaProduct *= scorePhiSum;
////							}
//							
//							scoreSum +=  scorePhiThetaProduct;							
//						}
//						if (scoreProduct == 0.0 || scoreSum == 0.0){
//							scoreProduct = scoreSum;
//						}
//						else{
//							scoreProduct *= scoreSum;
//						}
//					}
//					sum += scoreProduct;
//					
//					if(scoreProduct > best1AnsProb){
//												
//						best3AnsProb = best2AnsProb;
//						best3AnsInteger = best2AnsInteger;
//						best3AnsI = best2AnsI;
//						
//						best2AnsProb = best1AnsProb;
//						best2AnsInteger = best1AnsInteger;
//						best2AnsI = best1AnsI;
//						
//						best1AnsProb = scoreProduct;
//						best1AnsInteger = entry.getKey();
//						best1AnsI = i;
//					}
//					
//					else if(scoreProduct > best2AnsProb){
//						
//						best3AnsProb = best2AnsProb;
//						best3AnsInteger = best2AnsInteger;
//						best3AnsI = best2AnsI;
//						
//						best2AnsProb = scoreProduct;
//						best2AnsInteger = entry.getKey();
//						best2AnsI = i;
//					}
//					
//					else if(scoreProduct > best3AnsProb){
//						best3AnsProb = scoreProduct;
//						best3AnsInteger = entry.getKey();
//						best3AnsI = i;
//					}					
//					
//				}
//				m += 1;
//			}
		}
		
		System.out.println("总文档数（包括为空的）：" + m);
		System.out.println("【推荐答案】总概率：" + sum);
		System.out.println(ComModel2.thetaAnswer.length);
//		showTopicColor();
//		changeWordColor(textQuestion, SegWords.listToString(segQuestionWords));
		printAnswer(1, ComPreprocess.docMapMap.get(best1AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best1AnsIndex).get(CHILDREN[0]), best1AnsProb / sum, false);
		printAnswer(2, ComPreprocess.docMapMap.get(best2AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best2AnsIndex).get(CHILDREN[0]), best2AnsProb / sum, false);
		printAnswer(3, ComPreprocess.docMapMap.get(best3AnsIndex).get(CHILDREN[0]), 
				ComPreprocess.segDocMapMap.get(best3AnsIndex).get(CHILDREN[0]), best3AnsProb / sum, false);
	}
	
	
	/**
	 * get and print the top 3 best experts for model2
	 * @param segQuestionWords
	 */
	private void getExpertModel2(List<String> segQuestionWords){

		String best1ExpertName = "", best2ExpertName = "", best3ExpertName = "";		
		double best1ExpertProb = 0.0, best2ExpertProb = 0.0, best3ExpertProb = 0.0;
		
		int wordID;
		double scoreProduct, scoreSum, scorePhiPsiProduct, scorePhiSum;
//		int m = 0;
		int k;
		int x;
		int i;
		int w;
		String word;
		double sum = 0.0;
//		int mapIndex = 0;
		
//		Map<Integer, Map<String, String>> docMapMap =  ComPreprocess.docMapMap;
//		Map<Integer, Map<String, String>> segDocMapMap =  ComPreprocess.segDocMapMap;
//
		
//		for(Map.Entry<Integer, Map<Integer, Integer>> entry : ComModel2.userAnswer.getUser2DocAnsMapMap(docMapMap).entrySet()){
//					
//		}
		
		for(i = 0 ; i < ComModel2.userAnswer.size(); i++){
			System.out.println("第" + (i+1) + "位专家："+ComModel2.userAnswer.getUser(i));
			scoreProduct = 0.0;
			
			for (w =0; w < segQuestionWords.size(); w++){
				
				word = segQuestionWords.get(w);
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
					
					scorePhiPsiProduct = scorePhiSum * ComModel2.psiAnswer[i][x];
					
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
			
			if(scoreProduct > best1ExpertProb){
				best3ExpertProb = best2ExpertProb;
				best3ExpertName = best2ExpertName;
				
				best2ExpertProb = best1ExpertProb;
				best2ExpertName = best1ExpertName;
					
				best1ExpertProb = scoreProduct;
				best1ExpertName = ComModel2.userAnswer.getUser(i);
				
				System.out.println("best1， 第" + (i+1) + "位专家："+ComModel2.userAnswer.getUser(i));
			}
			
			else if(scoreProduct > best2ExpertProb){
				
				best3ExpertProb = best2ExpertProb;
				best3ExpertName = best2ExpertName;
				
				best2ExpertProb = scoreProduct;
				best2ExpertName = ComModel2.userAnswer.getUser(i);
				
				System.out.println("best2， 第" + (i+1) + "位专家："+ComModel2.userAnswer.getUser(i));
			}
			
			else if(scoreProduct > best3ExpertProb){
				best3ExpertProb = scoreProduct;
				best3ExpertName = ComModel2.userAnswer.getUser(i);
				
				System.out.println("best3， 第" + (i+1) + "位专家："+ComModel2.userAnswer.getUser(i));
			}	
		}
		
		System.out.println("【推荐用户】总概率：" + sum);
		printExpert(1, best1ExpertName, best1ExpertProb / sum);
		printExpert(2, best2ExpertName, best2ExpertProb / sum);
		printExpert(3, best3ExpertName, best3ExpertProb / sum);
		
	}
	
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
	
}
