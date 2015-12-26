package tabPages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ComPrediction extends Composite{
	
	/**
	 * type in the question that need to get the best answer and the best user
	 */
	private Text textQuestion;
	
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
	
	private Text textUser1;
	
	private Text textUser2;
	
	private Text textUser3;
	
	private Group groupAns1;
	
	private Group groupAns2;
	
	private Group groupAns3;
	
	private Group groupUser1;
	
	private Group groupUser2;
	
	private Group groupUser3;
	
	public ComPrediction(Shell shell, Composite parent, int style){
		super(parent, style);  
		
		Label labelQuestion = new Label(this, SWT.BORDER  );
		labelQuestion.setText("输入预测问题:");
		labelQuestion.setBounds(12 , 0, 80, 15);
		
		textQuestion = new Text(this, SWT.BORDER| SWT.V_SCROLL |  SWT.WRAP);
		textQuestion.setBounds(12, 15, 655, 100);
		
		Button buttonComfirm = new Button(this, SWT.BORDER);
		buttonComfirm.setText("确认");
		buttonComfirm.setBounds(580, 120, 90, 32);
		
		Group groupGetAnswer = new Group(this, SWT.BORDER);
		groupGetAnswer.setText("显示预测答案");
		groupGetAnswer.setBounds(10, 150, 420, 360);
				
		Group groupGetUser = new Group (this, SWT.BORDER);
		groupGetUser.setText("显示推荐专家");
		groupGetUser.setBounds(440, 150, 230, 250);
		
		groupAns1 = new Group(groupGetAnswer, SWT.NONE);
		groupAns1.setText("推荐答案1");
		groupAns1.setBounds(5 , 5, 405, 110);

		groupAns2 = new Group(groupGetAnswer, SWT.NONE);
		groupAns2.setText("推荐答案2");
		groupAns2.setBounds(5 , 115, 405, 110);
		
		groupAns3 = new Group(groupGetAnswer, SWT.NONE);
		groupAns3.setText("推荐答案3");
		groupAns3.setBounds(5 , 225, 405, 110);

		textAns1 = new StyledText(groupAns1, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns1.setBounds(0, 0 , 310, 75);
		
		Label labelAns1 = new Label(groupAns1, SWT.BORDER);
		labelAns1.setText("答案匹配率");
		labelAns1.setBounds(320, 10, 70, 20);
		
		textAns2 = new StyledText(groupAns2, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns2.setBounds(0, 0 , 310, 75);
		
		Label labelAns2 = new Label(groupAns2, SWT.BORDER);
		labelAns2.setText("答案匹配率");
		labelAns2.setBounds(320, 10, 70, 20);
		
		textAns3 = new StyledText(groupAns3, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textAns3.setBounds(0, 0 , 310, 75);
		
		Label labelAns3 = new Label(groupAns3, SWT.BORDER);
		labelAns3.setText("答案匹配率");
		labelAns3.setBounds(320, 10, 70, 20);
		
		groupUser1 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser1.setBounds(5,5, 215, 70);
		groupUser1.setText("推荐专家1");

		groupUser2 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser2.setBounds(5,80, 215, 70);
		groupUser2.setText("推荐专家2");		
		
		groupUser3 = new Group(groupGetUser, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		groupUser3.setBounds(5,155, 215, 70);
		groupUser3.setText("推荐专家3");

		textUser1 = new Text(groupUser1, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser1.setBounds(0, 0, 120, 50);
		
		Label labelUser1 = new Label(groupUser1, SWT.BORDER);
		labelUser1.setText("专家匹配率");
		labelUser1.setBounds(125, 5, 80, 20);
		
		textUser2 = new Text(groupUser2, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser2.setBounds(0, 0, 120, 50);
		
		Label labelUser2 = new Label(groupUser2, SWT.BORDER);
		labelUser2.setText("专家匹配率");
		labelUser2.setBounds(125, 5, 80, 20);
		
		textUser3 = new Text(groupUser3, SWT.BORDER | SWT.READ_ONLY |SWT.V_SCROLL | SWT.WRAP);
		textUser3.setBounds(0, 0, 120, 50);
		
		Label labelUser3 = new Label(groupUser3, SWT.BORDER);
		labelUser3.setText("专家匹配率");
		labelUser3.setBounds(125, 5, 80, 20);
		
		
	}
	
	
	protected void checkSubclass() {  
        // Disable the check that prevents subclassing of SWT components  
    }  
	
}
