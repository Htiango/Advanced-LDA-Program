package tabPages;

import java.util.HashMap;
import java.util.Map;

import ldaModeling1.Corpus;
import ldaModeling1.LdaGibbsSampler;
import ldaModeling1.LdaUtil;

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


@SuppressWarnings("deprecation")
public class ComModel1 extends Composite{
	
	private Corpus corpus;
	
	private LdaGibbsSampler ldaGibbsSampler;
	
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
    
    private final int[] COLUMNWIDTH = {70,80,150};
    
    private int topicNumber;
    
    /**
     * tableTree 中的父类
     */
    private TableTreeItem parentLdaResult;
    
    /**
     * tableTree 中的子类
     */
    private TableTreeItem childLdaResult;
    
	
	public ComModel1(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Group groupLDAPreprocess = new Group(this, SWT.BORDER);
		groupLDAPreprocess.setText("LDA模型预处理");
		groupLDAPreprocess.setBounds(5, 0, 320, 430);
				
		Group groupLDAResult =  new Group(this, SWT.BORDER);
		groupLDAResult.setText("LDA结果");
		groupLDAResult.setBounds(340, 0, 320, 430);
		
		Label labelLDAPicture = new Label(groupLDAPreprocess, SWT.BORDER);
		labelLDAPicture.setBounds(8, 15, 300, 200);
		labelLDAPicture.setImage(new Image(Display.getDefault(), "model 1.png"));

		
		Group groupLDAParameter = new Group(groupLDAPreprocess, SWT.BORDER);
		groupLDAParameter.setText("LDA参数选择");
		groupLDAParameter.setBounds(8, 230, 300, 170);
		
		Label labelDirPara = new Label(groupLDAParameter, SWT.NONE);
		labelDirPara.setText("Dirichlet参数");
		labelDirPara.setBounds(10, 20, 75, 15);
		
		Label labelAlpha = new Label(groupLDAParameter, SWT.NONE);
		labelAlpha.setText("α =");
		labelAlpha.setBounds(120, 20, 20, 15);
		
		Text textAlpha = new Text(groupLDAParameter, SWT.BORDER);
		textAlpha.setText("50");
		textAlpha.setBounds(145, 18, 40, 20);
		
		Label labelBeta = new Label(groupLDAParameter, SWT.NONE);
		labelBeta.setText("β =");
		labelBeta.setBounds(200, 20, 20, 15);
		
		Text textBeta = new Text(groupLDAParameter, SWT.BORDER);
		textBeta.setText("0.5");
		textBeta.setBounds(225, 18, 40, 20);
		
		Label labelTopicNum = new Label(groupLDAParameter, SWT.NONE);
		labelTopicNum.setText("确认主题数");
		labelTopicNum.setBounds(10, 80, 75, 15);
		
		comboTopicNum = new Combo(groupLDAParameter, SWT.READ_ONLY);
		comboTopicNum.setBounds(120, 80, 150, 18);
		final String[] TOPICNUM = {"5", "10", "15", "20", "25"};
		comboTopicNum.setItems(TOPICNUM);
		
		Button buttonDoLda = new Button(this, SWT.BORDER);
		buttonDoLda.setText("确认");
		buttonDoLda.setBounds(200, 430, 100, 40);
		buttonDoLda.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				doLda();
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
	}
	
	
	private void doLda(){
		topicNumber =Integer.parseInt(comboTopicNum.getText());		
		
		corpus = Corpus.loading(ComPreprocess.segDocMapMap);
		ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
		ldaGibbsSampler.gibbs(topicNumber);
		double[][] phi = ldaGibbsSampler.getPhi();
        topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), topicNumber);                
        LdaUtil.explain(topicMap, wordsValueMapMap);       
        
        printTableLdaResult(wordsValueMapMap);
		
	}
	
	
	
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
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
}
