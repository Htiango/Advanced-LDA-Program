package preprocessing;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class XMLReader {
	SAXBuilder sb = new SAXBuilder();
    Document doc = null;
    
    public Map<Integer, Map<String,String>> docMapMap = new HashMap<Integer, Map<String,String>>();
    public int docNum;
    public int ansNum = 0;
    public static String[] CHILDREN = 
    	{"问题标题","问题内容","提问者用户名","提问者性别","提问者年龄","提问时间",
    	"回复人1姓名", "回复人1职称", "回复人1分析",  "回复人1回复时间",
    	"回复人2姓名", "回复人2职称", "回复人2分析",  "回复人2回复时间",
    	"回复人3姓名", "回复人3职称", "回复人3分析",  "回复人3回复时间",
    	"回复人4姓名", "回复人4职称", "回复人4分析",  "回复人4回复时间",
    	"回复人5姓名", "回复人5职称", "回复人5分析",  "回复人5回复时间"};
    
    
    
    public void readXml(String filePath){
    	
    	
    	
    	try{    		
    		doc = sb.build(filePath);   // path
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("问答信息");
            
            docNum = list.size();
            
            @SuppressWarnings("unchecked")
			Map<String, String>[] docMap = new Map[docNum];
            
            int i = 0;
            
            for(Element el:list){
            	String idTemp = el.getAttributeValue("id");
            	Integer id = Integer.valueOf(idTemp);
            	
            	docMap[i] = new HashMap<String,String>();
            	String childText;
            	String childContent;
            	
            	for (int j = 0; j < CHILDREN.length; j++){
            		childText = CHILDREN[j];
            		childContent = el.getChildText(childText);
            		docMap[i].put(childText, childContent);
            		if (j == 8 || j == 12 || j == 16 || j ==20 || j == 24)
            		if (childContent.length() != 0){
                		ansNum += 1;
                	}
            	}
            	            	            	
            	docMapMap.put(id, docMap[i]);
            	i++;
            }
            
    	}catch (Exception e) {
            e.printStackTrace();
    	}
    }

}
