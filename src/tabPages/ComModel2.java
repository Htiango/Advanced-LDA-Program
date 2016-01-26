package tabPages;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ldaModeling2.Corpus;
import ldaModeling2.LdaGibbsSampler;
import ldaModeling2.LdaUtil;

@SuppressWarnings("deprecation")
public class ComModel2 extends Composite{
	
	/**
	 * choose the number of the topic 
	 */
	private Combo comboTopicNum;
	
	/**
	 * choose the number of the expertise 
	 */
	private Combo comboExpertiseNum;	
	
	/**
     * input the value alpha
     */
    private Text textAlpha;
    
    /**
     * input the value beta
     */
    private Text textBeta;
    
    /**
     * input the value gamma
     */
    private Text textGamma;
    
    /**
     * phi multinomial mixture of topic, expertise words (K x E x V)
     * it's the possibility of each topic and words
     */
    private double[][][] phi;
    
	/**
     * topic, expertise的一个2-D数组 (K x E)<br>
     * 每个都是一个Map, Map中的每个元素都是top possible的词语和对应的概率
     */
    private Map<String, Double>[][] topicExpertiseMap;
    
    /**
     * the topic number got from the combo
     */
    private int topicNumber;
    
    /**
     * the expertise number got from the combo
     */
    private int expertiseNumber;
    
    private double alpha;
    
    private double beta;
    
    private double gamma; 
    
    /**
     * Vocabulary, User corpus   -- 词语、用户库
     */
    private Corpus corpus;
	
	private LdaGibbsSampler ldaGibbsSampler;
	
	private final int LIMIT = 10;
	
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
    private final int[] COLUMNWIDTH = {90,70,140};
    
    /**
     * tableTree 中的父类
     */
    private TableTreeItem parentLdaResult;
    
    /**
     * tableTree 中的子类
     */
    private TableTreeItem childLdaResult1;
    
    /**
     * tableTree 中的子类
     */
    private TableTreeItem childLdaResult2;
	
	
	public ComModel2(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Group groupLDAPreprocess = new Group(this, SWT.BORDER);
		groupLDAPreprocess.setText("LDA模型预处理");
		groupLDAPreprocess.setBounds(5, 0, 320, 430);
				
		Group groupLDAResult =  new Group(this, SWT.BORDER);
		groupLDAResult.setText("LDA结果");
		groupLDAResult.setBounds(340, 0, 320, 430);
		
		Label labelLDAPicture = new Label(groupLDAPreprocess, SWT.BORDER);
		labelLDAPicture.setBounds(8, 10, 300, 200);
		labelLDAPicture.setImage(new Image(Display.getDefault(), "model 2.png"));
		
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
		
		Label labelGamma = new Label(groupLDAParameter, SWT.NONE);
		labelGamma.setText("γ =");
		labelGamma.setBounds(120, 50, 20, 15);
		
		textGamma = new Text(groupLDAParameter, SWT.BORDER);
		textGamma.setText("50");
		textGamma.setBounds(145, 48, 40, 20);
		
		Label labelTopicNum = new Label(groupLDAParameter, SWT.NONE);
		labelTopicNum.setText("确认主题数");
		labelTopicNum.setBounds(10, 80, 75, 15);
		
		comboTopicNum = new Combo(groupLDAParameter, SWT.READ_ONLY);
		comboTopicNum.setBounds(120, 80, 150, 18);
		final String[] TOPICNUM = {"5", "10", "15", "20"};
		comboTopicNum.setItems(TOPICNUM);
		
		Label labelExpertiseNum = new Label(groupLDAParameter, SWT.NONE);
		labelExpertiseNum.setText("确认专长数");
		labelExpertiseNum.setBounds(10, 110, 75, 15);
		
		comboExpertiseNum = new Combo(groupLDAParameter, SWT.READ_ONLY);
		comboExpertiseNum.setBounds(120, 110, 150, 18);
		final String[] TOPICNUM2 = {"3", "5", "8", "10"};
		comboExpertiseNum.setItems(TOPICNUM2);
		
		Button buttonDoLda = new Button(groupLDAParameter, SWT.BORDER);
		buttonDoLda.setText("确认");
		buttonDoLda.setBounds(170, 135, 100, 35);
		buttonDoLda.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				doLda();
				printTableLdaResult(topicExpertiseMap);
			}
		});
		
		tableTreeLdaResult = new TableTree(groupLDAResult, SWT.BORDER | SWT.FULL_SELECTION);
        tableTreeLdaResult.setBounds(5, 10, 300, 390);
        tableLdaResult = tableTreeLdaResult.getTable();     
        tableLdaResult.setHeaderVisible(true);
        tableLdaResult.setLinesVisible(true);       
        String[] tableTreeLdaResultTitle = {"Topic & Expertise", "Words", "Probability"};
        for (int i = 0; i < tableTreeLdaResultTitle.length ; i ++){
            new TableColumn(tableLdaResult, SWT.LEFT).setText(tableTreeLdaResultTitle[i]);
        }
        TableColumn[] columns = tableLdaResult.getColumns();
        int n = columns.length;
        for (int k = 0; k < n; k++) {
            columns[k].pack();  
            columns[k].setWidth(COLUMNWIDTH[k]);                
        }	
		
	}
	
	private void doLda(){
		topicNumber =Integer.parseInt(comboTopicNum.getText());
		expertiseNumber = Integer.parseInt(comboExpertiseNum.getText());
		alpha = Double.parseDouble(textAlpha.getText());
		beta = Double.parseDouble(textBeta.getText());
		gamma = Double.parseDouble(textGamma.getText());
		
		corpus = Corpus.loading(ComPreprocess.segDocMapMap, ComPreprocess.docMapMap,
				ComPreprocess.uniqueWordsNumClone, ComPreprocess.uniqueUserNumClone);
		ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getDoc2UserMap(), 
				corpus.getVocabularySize(), corpus.getUserSize());
		ldaGibbsSampler.gibbs(topicNumber, expertiseNumber, alpha, beta, gamma);
		phi = ldaGibbsSampler.getPhi();
		topicExpertiseMap = LdaUtil.translate(phi, corpus.getVocabulary(), LIMIT);
		LdaUtil.explain(topicExpertiseMap);
		
	}
	
	private void printTableLdaResult(Map<String, Double>[][] ldaResult){
		for (int k = 0; k < topicNumber; k++){
			parentLdaResult = new TableTreeItem(tableTreeLdaResult, SWT.BORDER);
            parentLdaResult.setText(0, "topic " + k);
            parentLdaResult.setText(1, "top words");
            parentLdaResult.setText(2, "Words' Probability");
            for (int e = 0; e < expertiseNumber; e++){
            	childLdaResult1 = new TableTreeItem(parentLdaResult, SWT.BORDER);
                childLdaResult1.setText(0, "  t " + k + ", e "+ e);
                childLdaResult1.setText(1, "top words");
                childLdaResult1.setText(2, "Words' Probability");
                int topIndex = 0;
                for (Map.Entry<String, Double> entry : ldaResult[k][e].entrySet()){
                	childLdaResult2 = new TableTreeItem(childLdaResult1, SWT.BORDER);
                    childLdaResult2.setText(0, "   Top " + (topIndex+1) + " word");
                    childLdaResult2.setText(1, entry.getKey());
                    childLdaResult2.setText(2, entry.getValue()+"");
                    topIndex++;
                }
                childLdaResult1.setExpanded(false);
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
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
}
