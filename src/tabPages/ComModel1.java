package tabPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ldaModeling1.Corpus;
import ldaModeling1.LdaGibbsSampler;
import ldaModeling1.LdaUtil;
import ldaModeling1.Vocabulary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


@SuppressWarnings("deprecation")
public class ComModel1 extends Composite{
	
	
	public static double[][] thetaAnswer;
	
	public static double[][] phiAnswer;
	
	public static Vocabulary vocabularyAnswer;
	
	public static int topicNumAnswer;
	
	private Corpus corpus;
	
	private LdaGibbsSampler ldaGibbsSampler;
	
	/**
	 * choose the number of the topic 
	 */
	private Combo comboTopicNum;
	
	/**
     * topic的一个数组<br>
     * 每个都是一个Map, Map中的每个元素都是top possible的词语和对应的概率
     */
    private Map<String, Double>[] topicMap;
    
    /**
     * the top words and their possible from each topic;
     */
    private Map<Integer, Map<String,Double>> wordsValueMapMap = new HashMap<Integer, Map<String,Double>>();   
    
    /**
     * tableTree类，用来显示lda的结果
     */
	private TableTree tableTreeLdaResult;
    
    /**
     * tableTree中的table
     */
    private Table tableLdaResult;
    
    /**
     * the column width of table
     */
    private final int[] COLUMNWIDTH = {70,80,150};
    
    /**
     * the topic number got from the combo
     */
    private int topicNumber;
    
    private double alpha;
    
    private double beta;
    
    /**
     * input the value alpha
     */
    private Text textAlpha;
    
    /**
     * input the value beta
     */
    private Text textBeta;
    
    /**
     * tableTree 中的父类
     */
    private TableTreeItem parentLdaResult;
    
    /**
     * tableTree 中的子类
     */
    private TableTreeItem childLdaResult;
    
    /**
     * phi multinomial mixture of topic words (K x V)
     * it's the possibility of each topic and words
     */
    private double[][] phi;
    
    /**
     * a label that shows the index of the present doc 
     */
    private Label labelPresentDoc;
    
    /**
     * input the doc index that you want to go to
     */
    private Text textTurn2Doc;
    
    /**
     * show each doc with its words in different color relative with the topic color we choose
     */
    private StyledText textDocDetail;
    
    private Group groupDocTopic;
    
    /**
     * represent the index of the doc
     */
    private int docIndex = 1;
    
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
    
    private static Color[] COLORTOPIC5= {SWTResourceManager.getColor(255, 0, 0), 
                                        SWTResourceManager.getColor(255, 255, 0),
                                        SWTResourceManager.getColor(0, 255, 0),
                                        SWTResourceManager.getColor(0, 255, 255),
                                        SWTResourceManager.getColor(0, 0, 255)};
    
    private static Color[] COLORTOPIC10 = {SWTResourceManager.getColor(255, 0, 0),
    									  SWTResourceManager.getColor(255, 128, 0),
								          SWTResourceManager.getColor(255, 255, 0),
								          SWTResourceManager.getColor(128, 255, 0),
								          SWTResourceManager.getColor(0, 255, 128),
								          SWTResourceManager.getColor(0, 255, 255),
								          SWTResourceManager.getColor(0, 128, 255),
								          SWTResourceManager.getColor(0, 0, 255),
								          SWTResourceManager.getColor(127, 0, 255),
								          SWTResourceManager.getColor(255, 0, 255)};
    
    private static Color[] COLORTOPIC15 = {SWTResourceManager.getColor(102, 0, 0),
    									  SWTResourceManager.getColor(255, 0, 0),          
										  SWTResourceManager.getColor(255, 128, 0),
								          SWTResourceManager.getColor(255, 255, 0),
								          SWTResourceManager.getColor(128, 255, 0),
								          SWTResourceManager.getColor(0, 255, 0),
								          SWTResourceManager.getColor(0, 102, 51),
								          SWTResourceManager.getColor(0, 255, 128),
								          SWTResourceManager.getColor(0, 255, 255),
								          SWTResourceManager.getColor(0, 128, 255),
								          SWTResourceManager.getColor(0, 0, 255),
								          SWTResourceManager.getColor(127, 0, 255),
								          SWTResourceManager.getColor(255, 0, 255),
								          SWTResourceManager.getColor(255, 0, 127),
								          SWTResourceManager.getColor(128, 128, 128)};

    private static Color[] COLORTOPIC20 = {SWTResourceManager.getColor(102, 0, 0),
										  SWTResourceManager.getColor(255, 0, 0),  
										  SWTResourceManager.getColor(102, 51, 0), 
										  SWTResourceManager.getColor(255, 128, 0),
								          SWTResourceManager.getColor(255, 255, 0),
								          SWTResourceManager.getColor(128, 255, 0),
								          SWTResourceManager.getColor(0, 255, 0),
								          SWTResourceManager.getColor(0, 102, 51),
								          SWTResourceManager.getColor(0, 255, 128),
								          SWTResourceManager.getColor(0, 102, 102), 
								          SWTResourceManager.getColor(0, 255, 255),
								          SWTResourceManager.getColor(0, 128, 255),
								          SWTResourceManager.getColor(0, 0, 102), 
								          SWTResourceManager.getColor(0, 0, 255),
								          SWTResourceManager.getColor(127, 0, 255),
								          SWTResourceManager.getColor(255, 204, 255), 
								          SWTResourceManager.getColor(255, 0, 255),
								          SWTResourceManager.getColor(102, 0, 102), 
								          SWTResourceManager.getColor(255, 0, 127),
								          SWTResourceManager.getColor(128, 128, 128)};
    
    
    public static Color[][] COLORTOPIC = {COLORTOPIC5, COLORTOPIC10, COLORTOPIC15, COLORTOPIC20};
    
    
	
	public ComModel1(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Group groupLDAPreprocess = new Group(this, SWT.BORDER);
		groupLDAPreprocess.setText("LDA模型预处理");
		groupLDAPreprocess.setBounds(5, 0, 320, 430);
				
		Group groupLDAResult =  new Group(this, SWT.BORDER);
		groupLDAResult.setText("LDA结果");
		groupLDAResult.setBounds(340, 0, 320, 430);
		
		Label labelLDAPicture = new Label(groupLDAPreprocess, SWT.BORDER);
		labelLDAPicture.setBounds(8, 10, 300, 200);
		labelLDAPicture.setImage(new Image(Display.getDefault(), "model 1.png"));

		
		Group groupLDAParameter = new Group(groupLDAPreprocess, SWT.BORDER);
		groupLDAParameter.setText("LDA参数选择");
		groupLDAParameter.setBounds(8, 220, 300, 190);
		
		Label labelDirPara = new Label(groupLDAParameter, SWT.NONE);
		labelDirPara.setText("Dirichlet参数");
		labelDirPara.setBounds(10, 20, 75, 15);
		
		Label labelAlpha = new Label(groupLDAParameter, SWT.NONE);
		labelAlpha.setText("α =");
		labelAlpha.setBounds(120, 20, 20, 15);
		
		textAlpha = new Text(groupLDAParameter, SWT.BORDER);
		textAlpha.setText("50");
		textAlpha.setBounds(145, 18, 40, 20);
		
		Label labelBeta = new Label(groupLDAParameter, SWT.NONE);
		labelBeta.setText("β =");
		labelBeta.setBounds(200, 20, 20, 15);
		
		textBeta = new Text(groupLDAParameter, SWT.BORDER);
		textBeta.setText("0.5");
		textBeta.setBounds(225, 18, 40, 20);
		
		Label labelTopicNum = new Label(groupLDAParameter, SWT.NONE);
		labelTopicNum.setText("确认主题数");
		labelTopicNum.setBounds(10, 80, 75, 15);
		
		comboTopicNum = new Combo(groupLDAParameter, SWT.READ_ONLY);
		comboTopicNum.setBounds(120, 80, 150, 18);
		final String[] TOPICNUM = {"5", "10", "15", "20"};
		comboTopicNum.setItems(TOPICNUM);
		
		Button buttonDoLda = new Button(groupLDAParameter, SWT.BORDER);
		buttonDoLda.setText("确认");
		buttonDoLda.setBounds(170, 120, 100, 40);
		buttonDoLda.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				doLda();
				showLdaDoc();
			}
		});
		
		
		tableTreeLdaResult = new TableTree(groupLDAResult, SWT.BORDER | SWT.FULL_SELECTION);
        tableTreeLdaResult.setBounds(5, 10, 300, 200);
        tableLdaResult = tableTreeLdaResult.getTable();     
        tableLdaResult.setHeaderVisible(true);
        tableLdaResult.setLinesVisible(true);       
        String[] tableTreeLdaResultTitle = {"Topic", "Words", "Probability"};
        for (int i = 0; i < tableTreeLdaResultTitle.length ; i ++){
            new TableColumn(tableLdaResult, SWT.LEFT).setText(tableTreeLdaResultTitle[i]);
        }
        TableColumn[] columns = tableLdaResult.getColumns();
        int n = columns.length;
        for (int k = 0; k < n; k++) {
            columns[k].pack();  
            columns[k].setWidth(COLUMNWIDTH[k]);                
        }	
        
        
        groupDocTopic = new Group(groupLDAResult, SWT.BORDER);
        groupDocTopic.setText("文档主题分布");
        groupDocTopic.setBounds(5, 220, 305, 190);
        
        labelPresentDoc = new Label(groupDocTopic, SWT.BORDER);
        labelPresentDoc.setText("当前第 x 条");
        labelPresentDoc.setBounds(5, 8, 80, 15);
        
        Button buttonFrontDoc = new Button(groupDocTopic, SWT.BORDER);
        buttonFrontDoc.setText("Front");
        buttonFrontDoc.setBounds(75, 8, 60, 20);
        buttonFrontDoc.setBackground(new Color(null,245,245,245));
        buttonFrontDoc.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				goFrontPage();
			}
		});
        
        Button buttonNextDoc = new Button(groupDocTopic, SWT.BORDER);
        buttonNextDoc.setText("Next");
        buttonNextDoc.setBounds(130, 8, 60, 20);
        buttonNextDoc.setBackground(new Color(null,245,245,245));
        buttonNextDoc.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				goNextPage();
			}
		});
        
        Label labelTurn2Doc = new Label(groupDocTopic, SWT.BORDER);
        labelTurn2Doc.setText("转到第");
        labelTurn2Doc.setBounds(190, 8, 40, 15);
        
        textTurn2Doc = new Text(groupDocTopic, SWT.BORDER);
        textTurn2Doc.setBounds(230, 8, 30, 20);
        
        Button buttonGoDoc = new Button(groupDocTopic, SWT.BORDER);
        buttonGoDoc.setBounds(260, 8, 45, 20);
        buttonGoDoc.setText("Go");
        buttonGoDoc.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		goPage();
        	}
        });
        
        textDocDetail = new StyledText(groupDocTopic, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
        textDocDetail.setBounds(5, 30, 292, 120);
        
	}
	
	/**
	 * update the doc index
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
	
	/**
	 * use gibbs sampling to get the lda result
	 */
	private void doLda(){
		topicNumber =Integer.parseInt(comboTopicNum.getText());
		alpha = Double.parseDouble(textAlpha.getText());
		beta = Double.parseDouble(textBeta.getText());
		
		corpus = Corpus.loading(ComPreprocess.segDocMapMap, ComPreprocess.uniqueWordsNumClone);
		ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
		ldaGibbsSampler.gibbs(topicNumber, alpha, beta);
		phi = ldaGibbsSampler.getPhi();
        topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), topicNumber);                
        LdaUtil.explain(topicMap, wordsValueMapMap);       
        
        thetaAnswer = ldaGibbsSampler.getTheta();
        phiAnswer = ldaGibbsSampler.getPhi();
        vocabularyAnswer = corpus.getVocabulary();
        topicNumAnswer = topicNumber;
        
        removeTableTree();
        printTableLdaResult(wordsValueMapMap);		
	}
	
	/**
	 * print the lda result into a tableTree 
	 * @param ldaResult
	 */
	private void printTableLdaResult(Map<Integer, Map<String, Double>> ldaResult){
		
		int i;
		
		for (Map.Entry<Integer, Map<String, Double>> entry : ldaResult.entrySet()){
			
			i = entry.getKey().intValue();
			
            parentLdaResult = new TableTreeItem(tableTreeLdaResult, SWT.BORDER);
            parentLdaResult.setText(0, "topic " + i);
            parentLdaResult.setText(1, "top possible words");
            parentLdaResult.setText(2, "Words' Probability");            
            
            int j = 0;
            
            for (Map.Entry<String, Double> entry2 : entry.getValue().entrySet()){
                childLdaResult = new TableTreeItem(parentLdaResult, SWT.BORDER);
                childLdaResult.setText(0, "   Top " + (j+1) + " word");
                childLdaResult.setText(1, entry2.getKey());
                childLdaResult.setText(2, entry2.getValue()+"");
                j++;
            }
            parentLdaResult.setExpanded(false);
        }
        
        TableColumn[] columns = tableLdaResult.getColumns();
        int n = columns.length;
        for (int k = 0; k < n; k++) {
            columns[k].pack();  
            columns[k].setWidth(COLUMNWIDTH[k]);                
        }
	}
	
	/**
	 * print the words from doc in styledText in different color 
	 */
	private void showLdaDoc(){
		showTopicColor();
		printDoc(docIndex);
		
	}
	
	/**
	 * the method to change the words color to be the same as its topic
	 */
	private void changeWordColor(){
		String sentensePrint = textDocDetail.getText();
		ArrayList<String> words = new ArrayList<String>();
		words = getWords(docIndex);
		
		int topicIndex;
		
		
		String word;
		Color color;
		for (int i = 0; i < words.size(); i ++){
			word = words.get(i);
			topicIndex = getTopicIndex(word);
			int startIndex = 0;
	        startIndex = sentensePrint.indexOf(word, startIndex);
	        color = COLORTOPIC[topicNumber / 5 - 1][topicIndex];
	        
	        StyleRange style = new StyleRange();
	        
	        while(startIndex > -1){
	        	
	        	
	            style.start = startIndex;
	            style.length = word.length();
	                      
	            style.foreground = color;
	            textDocDetail.setStyleRange(style);
	            startIndex = sentensePrint.indexOf(word, startIndex + 1);
	        }
		}
	}
	
	/**
	 * get the most possible topic of a word
	 * @param word
	 * @return
	 */
	private int getTopicIndex(String word){
		int topicIndex = 0;		
		int id = corpus.getVocabulary().getId(word);
		double possibilityMax = phi[topicIndex][id];
		double possibility;
		for (int i = 1 ; i < topicNumber; i ++){
			possibility = phi[i][id];
			if (possibility > possibilityMax){
				topicIndex = i;
				possibilityMax = possibility;
			}
		}
		return topicIndex;
	}
	
	/**
	 * get all the segged words from the previous Map
	 * @param docIndex
	 * @return
	 */
	private ArrayList<String> getWords(int docIndex){
		ArrayList<String> words = new ArrayList<String>();
		String childText;
		String segSentense;
		for (int i = 0; i < CHILDREN2.length; i++){
			childText = CHILDREN2[i];
			segSentense = ComPreprocess.segDocMapMap.get(docIndex).get(childText);
			if (segSentense.length() != 0){
				String[] wordsSeg = segSentense.split("\\s");
				for (int j = 0; j < wordsSeg.length; j ++){
					words.add(wordsSeg[j]);
				}
			}
		}
		return words;
	}
	
	/**
	 * print the doc into the styledText
	 */
	private void printDoc(int docIndex){
		textDocDetail.setText("");
		String childText;
		String childContent;
		String inputText;
		for (int i = 0; i < CHILDREN.length; i++){
			childText = CHILDREN[i];
			childContent = ComPreprocess.docMapMap.get(docIndex).get(childText);
			if (childContent.length() != 0){
				inputText = "【" + childText +"】：" + childContent; 
				textDocDetail.append(inputText);
				textDocDetail.append("\n\n");
			}
		}
		changeWordColor();
	}
	
	/**
	 * show the color of each topic
	 */
	private void showTopicColor(){
		int startPixel = 2;
		int labelWidth = 300 / topicNumber;
		Label[] labelTopicColor = new Label[topicNumber];
		for(int i = 0; i < topicNumber; i++){
			labelTopicColor[i] = new Label(groupDocTopic, SWT.NONE);
			labelTopicColor[i].setBounds(startPixel, 150, labelWidth, 20);
			labelTopicColor[i].setBackground(COLORTOPIC[topicNumber/5-1][i]);
			labelTopicColor[i].setText(i + "");
			startPixel += labelWidth;
		}
	}
	
	/**
	 * remove the content in the table tree
	 */
	private void removeTableTree(){
		tableLdaResult.removeAll();
	}
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
}
