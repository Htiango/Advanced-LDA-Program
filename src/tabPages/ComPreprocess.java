package tabPages;


//import java.util.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import preprocessing.SegWords;
import preprocessing.XMLReader;

//import Pages.ExplorerPage;


public class ComPreprocess extends Composite{
	
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
     * a text show the name of the file
     */
    private Text filenameText;    
    
    /**
     * a text show the num of docs in the file
     */
    private Text docNumText;
    
    /**
     * a text shows the num of answers in the files
     */
    private Text ansNumText;
    
    /**
     * a text shows the num of page in the table
     */
    private Text pageNumText;
    
    /**
     * show the pages of the table
     */
    private Text presentPageText;
        
    /**
     * input the page you want to go
     */
    private Text turnPageText;
    
    /**
     * the class that translate the xml file
     */
    private XMLReader xmlReader;
    
    /**
     * The number of column is 2
     */
    private static int COLUMNS_CNT = 2;
    
    /**
     * The number of row is 20
     */
    private static int ROWS_CNT = 20;  
    
    /**
     * Show the page in the table
     */
    private int page = 1;
    
    /**
     * show the title and ids of the xml file
     */
    private Table tableTitle;
    
    /**
     * whether a xml file is open
     */
    private boolean ifOpen = false;
    
    private boolean ifSeg = false;
    
    /**
     * a text that shows the details of a doc
     */
    private Text docDetailText;
    
    /**
     * a  text that shows the details of a segged doc
     */
    private Text segDocDetailText;
    
    /**
     * the class that seg the words and rid off the stopwords
     */
    private SegWords segWords;
    
    public static Map<Integer, Map<String,String>> segDocMapMap = 
			new HashMap<Integer, Map<String,String>>();
    
    public static Map<Integer, Map<String,String>> docMapMap = 
			new HashMap<Integer, Map<String,String>>();
    
    /**
     * a text that shows the words number
     */
    private Text wordsNumText;
    
    /**
     * a text that shows the number of unique words
     */
    private Text uniqueWordsNumText;
    
    /**
     * a text that shows the number of words that repeat more than 2 times
     */
    private Text repeat2NumText;
    
    /**
     * a text that shows the number of words that repeat more than 10 times
     */
    private Text repeat10NumText;
    
    /**
     * a text that shows the number of words that repeat more than 100 times
     */
    private Text repeat100NumText;
    
    /**
     * the number of all the words
     */
    private int wordsNum;
    
    /**
     * the number of all unique words
     */
    private int uniqueWordsNum;
    
    public static int uniqueWordsNumClone;
    
    /**
     * the number of words repeat more than 2
     */
    private int repeat2Num;
    
    /**
     * the number of words repeat more than 10
     */
    private int repeat10Num; 

    /**
     * the number of words repeat more than 10
     */
    private int repeat100Num; 
    
    
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
    	{"问题内容","回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};

    
  
	
	public ComPreprocess(Shell shell, Composite parent, int style) {  
        super(parent, style);  
        
        
        fileText = new Text(this, SWT.BORDER);
        fileText.setBounds(121, 40, 445, 22);
                        
        /**
         * a group shows the basic info of xml file
         */
        Group groupXmlInfo = new Group(this, SWT.BORDER);
        groupXmlInfo.setText("XML信息");
        groupXmlInfo.setBounds(10, 70, 345, 90);
        

        filenameText = new Text(groupXmlInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        filenameText.setBounds(10, 10, 150, 22);
        filenameText.setBackground(new Color(null,245,245,245));
        filenameText.setText("文件名：");
        
        docNumText = new Text(groupXmlInfo, SWT.BORDER | SWT.READ_ONLY);
        docNumText.setBounds(10, 40, 150, 22);
        docNumText.setBackground(new Color(null,245,245,245));
        docNumText.setText("文档数：");
        
        ansNumText = new Text(groupXmlInfo, SWT.BORDER | SWT.READ_ONLY);
        ansNumText.setBounds(185, 40, 150, 22);
        ansNumText.setBackground(new Color(null,245,245,245));
        ansNumText.setText("回答数：");
        
        pageNumText= new Text(groupXmlInfo, SWT.BORDER | SWT.READ_ONLY);
        pageNumText.setBounds(185, 10, 150, 22);
        pageNumText.setBackground(new Color(null,245,245,245));
        pageNumText.setText("总页数：");
        
        
        /**
         * a group shows all the docs' titles
         */
        Group groupDocTitle = new Group(this, SWT.BORDER);
        groupDocTitle.setText("文档标题");
        groupDocTitle.setBounds(10, 160, 345, 300);
        
        presentPageText = new Text(groupDocTitle, SWT.BORDER | SWT.READ_ONLY);
        presentPageText.setBounds(5, 5, 75, 22);
        presentPageText.setText("当前页：");
        presentPageText.setBackground(new Color(null,245,245,245));
        
        /**
         * turn to the front page if possible
         */
        Button buttonFront = new Button(groupDocTitle, SWT.BORDER);
        buttonFront.setBounds(85, 5, 60, 24);
        buttonFront.setText("Front");
        buttonFront.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				if (ifOpen == true){
					if (page != 1){
						page -= 1;
						fillTableGroup();
					}
					else {
						MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
						messagebox.setText("Error");
						messagebox.setMessage("已是第一页，没有上一页");
						messagebox.open();
					}
				}				
			}
		});
        
        /**
         * turn to the next page if possible
         */
        Button buttonNext = new Button(groupDocTitle,SWT.BORDER );
        buttonNext.setBounds(145, 5, 60, 24);
        buttonNext.setText("Next");
        buttonNext.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				if (ifOpen == true){
					if (page * 20 < xmlReader.docNum){
						page += 1;
						fillTableGroup();
					}
					else {
						MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
						messagebox.setText("Error");
						messagebox.setMessage("已是最后一页，没有下一页");
						messagebox.open();
					}
				}				
			}
		});
        
        /**
         * instruction about turning pages
         */
        Label labelTurnPage = new Label(groupDocTitle,SWT.NONE);
        labelTurnPage.setBounds(210, 8, 50, 22);
        labelTurnPage.setText("转到第");
        
        /**
         * turn to the next page if possible
         */
        turnPageText = new Text(groupDocTitle, SWT.BORDER);
        turnPageText.setBounds(250, 5, 25, 22);
        
        /**
         * comfirm to turning to the next page
         */
        Button buttonComfirmTurn = new Button(groupDocTitle, SWT.BORDER);
        buttonComfirmTurn.setBounds(282, 6, 60, 22);
        buttonComfirmTurn.setText("确认");
        buttonComfirmTurn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				if (ifOpen == true){
					int pageGo = Integer.parseInt(turnPageText.getText());
					if ((pageGo-1) * 20 < xmlReader.docNum && pageGo > 0){
						page = pageGo;
						fillTableGroup();
					}
					else {
						MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
						messagebox.setText("Error");
						messagebox.setMessage("不存在该页");
						messagebox.open();
					}
				}				
			}
		});
        
        
        
        /**
         * initialize the table
         */
        tableTitle = new Table(groupDocTitle, SWT.BORDER|SWT.FULL_SELECTION);
        tableTitle.setBounds(5, 30, 335, 240);
        tableTitle.setHeaderVisible(true);
        tableTitle.setLinesVisible(true);
        String[] tableTitleString = {"ID", "Title"};
        int[] tableColumnWidth = {50,285};
		for (int i = 0; i < COLUMNS_CNT ; i ++){
			TableColumn columnTemp = new TableColumn(tableTitle, SWT.CENTER);
			columnTemp.setText(tableTitleString[i]);			
			columnTemp.setResizable(false);
			tableTitle.getColumn(i).pack();
			columnTemp.setWidth(tableColumnWidth[i]);
		}
		
        
        
        /**
         * a group shows the basic info after segged
         */
        Group groupSegInfo = new Group(this, SWT.BORDER);
        groupSegInfo.setText("分词后的信息");
        groupSegInfo.setBounds(365, 70, 310, 90);
        
        wordsNumText = new Text(groupSegInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        wordsNumText.setBounds(10, 10, 135, 22);
        wordsNumText.setBackground(new Color(null,245,245,245));
        wordsNumText.setText("总词数：");
        
        uniqueWordsNumText = new Text(groupSegInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        uniqueWordsNumText.setBounds(165, 10, 135, 22);
        uniqueWordsNumText.setBackground(new Color(null,245,245,245));
        uniqueWordsNumText.setText("不同词数：");
        
        repeat2NumText = new Text(groupSegInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        repeat2NumText.setBounds(10, 40, 90, 22);
        repeat2NumText.setBackground(new Color(null,245,245,245));
        repeat2NumText.setText(">2:");
        
        repeat10NumText = new Text(groupSegInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        repeat10NumText.setBounds(110, 40, 90, 22);
        repeat10NumText.setBackground(new Color(null,245,245,245));
        repeat10NumText.setText(">10:");

        repeat100NumText = new Text(groupSegInfo, SWT.BORDER | SWT.READ_ONLY |SWT.H_SCROLL);
        repeat100NumText.setBounds(210, 40, 90, 22);
        repeat100NumText.setBackground(new Color(null,245,245,245));
        repeat100NumText.setText(">100:");
        
        /**
         * a group shows the detail of the selected doc
         */
        Group groupDocDetail = new Group(this, SWT.BORDER);
        groupDocDetail.setText("文档详细信息");
        groupDocDetail.setBounds(365, 160, 310, 160);
        
        docDetailText = new Text(groupDocDetail,SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
        docDetailText.setBounds(2,0,302,140);
        docDetailText.setBackground(new Color(null,230,230,230));
        
        Button buttonSegWords = new Button(this, SWT.BORDER);
        buttonSegWords.setText("进行分词");
        buttonSegWords.setBackground(new Color(null,230,230,230));
        buttonSegWords.setBounds(75,465, 200, 40);
        buttonSegWords.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e){
        		if (ifSeg == false){
        			ifSeg = true;
        			doSegging();
        		}
        	}
        });
        
        
        /**
         * a group shows the detail of the selected doc after segged
         */        
        Group groupSegDoc = new Group(this, SWT.BORDER);
        groupSegDoc.setText("分词后的文档");
        groupSegDoc.setBounds(365, 320, 310, 140);
        
        
        segDocDetailText = new Text(groupSegDoc,SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
        segDocDetailText.setBounds(2, 0, 302, 120);
        segDocDetailText.setBackground(new Color(null,230,230,230));
        
        
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
	
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
	
	
	/**
	 * open the selected xml file and show in other groups
	 */
	private void openFile() {
		xmlReader = new XMLReader();
		xmlReader.readXml(filePath[0]);	
		
		docMapMap = xmlReader.docMapMap;
				
		// fill the doc titles in the table
		fillDocInfo();
		fillTableGroup();
		fillDocDetail();
		ifOpen = true;
		ifSeg = false;
	}
	
	/**
	 * seg the words
	 */
	private void doSegging(){
		segWords = new SegWords();
		segWords.segWords(xmlReader.docMapMap, xmlReader.docNum);
		segDocMapMap = segWords.segDocMapMap;
		fillSegInfo();
		fillSegDetail();
	}
	
	/**
	 * fill in the groupSegInfo
	 */
	private void fillSegInfo(){
		getSegInfo();
		wordsNumText.setText("总词数:" + wordsNum);
		uniqueWordsNumText.setText("不同词数:" + uniqueWordsNum);
		repeat2NumText.setText(">2:" + repeat2Num);
		repeat10NumText.setText(">10:" + repeat10Num);
		repeat100NumText.setText(">100:" + repeat100Num);
	}
	
	/**
	 * fill in the groupSegDetail -- segDocDetailText
	 */
	private void fillSegDetail(){
		tableTitle.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				int selectIndex = tableTitle.getSelectionIndex();
				int id = selectIndex + 1 + (page-1) * ROWS_CNT;
				String childText;
				String childContent;
				String inputText;
				if (id <= xmlReader.docNum){
					segDocDetailText.setText("");
					for (int i = 0; i < CHILDREN2.length; i++){
						childText = CHILDREN2[i];
						childContent = segWords.segDocMapMap.get(id).get(childText);
						if (childContent.length() != 0){
							inputText = "【" + childText +"】：" + childContent; 
							segDocDetailText.append(inputText);
							segDocDetailText.append("\n\n");
						}
					}
					
				}								
				
			}
		});
	}
	
	/**
	 * fill in the groupDocInfo
	 */
	private void fillDocInfo(){
		filenameText.setText("文件名：" +  filename);
		docNumText.setText("文档数：" + xmlReader.docNum);
		ansNumText.setText("回答数：" + xmlReader.ansNum);
		pageNumText.setText("总页数：" + ((xmlReader.docNum - 1) / ROWS_CNT + 1));
	}
	
	/**
	 * fill in the table and refresh the other 
	 */
	private void fillTableGroup(){
		removeTableGroup();
		
		presentPageText.setText("当前页:" + page);
		
		for (int i = 0; i < ROWS_CNT; i ++){
			TableItem item = new TableItem(tableTitle,SWT.NULL);
			int id = i + 1 + ROWS_CNT * (page - 1);
			if (id <= xmlReader.docNum){
				item.setText(0, id + "");
				Integer id2 = new Integer(id);
				String title = xmlReader.docMapMap.get(id2).get("问题标题");
				item.setText(1, title);
			}			
		}
		
		// add addSelectionListener
		
	}
	
	/**
	 * fill in the groupDocDetail
	 */
	private void fillDocDetail(){
		tableTitle.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				int selectIndex = tableTitle.getSelectionIndex();
				int id = selectIndex + 1 + (page-1) * ROWS_CNT;
				String childText;
				String childContent;
				String inputText;
				if (id <= xmlReader.docNum){
					docDetailText.setText("");
					for (int i = 0; i < CHILDREN.length; i++){
						childText = CHILDREN[i];
						childContent = xmlReader.docMapMap.get(id).get(childText);
						if (childContent.length() != 0){
							inputText = "【" + childText +"】：" + childContent; 
							docDetailText.append(inputText);
							docDetailText.append("\n\n");
						}
					}
					
				}								
				
			}
		});
	}
	
	/**
	 * get the number of the words' info after segged
	 */
	private void getSegInfo(){
		TreeSet<String> wordsSet = new TreeSet<String>();
		wordsNum = 0;
		uniqueWordsNum = 0;
		repeat2Num = 0;
		repeat10Num = 0;
		repeat100Num = 0;
		String[] temp;
		
		ArrayList<String> equalSet = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<String>();
		
		for(Map.Entry<Integer, Map<String,String>> entry : segWords.segDocMapMap.entrySet()){
			for (int i = 0; i < CHILDREN2.length; i++){
				temp = entry.getValue().get(CHILDREN2[i]).split("\\s");
				wordsNum += temp.length;
				for(int k = 0; k < temp.length; k ++){
					list.add(temp[k]);
                    if(wordsSet.add(temp[k])==true){
                    	equalSet.add(temp[k]);
                    }                         
				}
			}
		}
		int[] damnRepeat = new int[equalSet.size()];
        for(int i = 0; i<equalSet.size();i++){
        	damnRepeat[i] = 0;
        }
        
        for(int i = 0; i < list.size(); i++){
            damnRepeat[equalSet.indexOf(list.get(i))] += 1;                 
        }
        
        for(int i = 0;i<equalSet.size();i++){
        	if (damnRepeat[i] >= 2){
        		repeat2Num += 1;
        	}
        	if (damnRepeat[i] >= 10){
                repeat10Num += 1;
        	}
        	if (damnRepeat[i] >= 100){
        		repeat100Num += 1;
        	}
        }
        uniqueWordsNum = wordsSet.size();
        uniqueWordsNumClone = uniqueWordsNum;
	}
	
	/**
	 * remove the content in the table and the other 
	 */
	private void removeTableGroup(){
		tableTitle.removeAll();		
	}
}
