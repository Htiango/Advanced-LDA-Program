package preprocessing;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;

public class SegWords {
	
	public static String[] CHILDREN = 
    	{"问题内容","回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
	
	/**
	 * have no stop words and are segged
	 */
	public Map<Integer, Map<String,String>> segDocMapMap = 
			new HashMap<Integer, Map<String,String>>();
	
	
	public void segWords(Map<Integer, Map<String,String>> docMapMap, int docNum){
		
		Integer id;
		int idIndex;
		String sentense;		
		CWSTagger tag;
		String segSentense;
		String[] words;
		String non_stopwords_segSentense;
		List<String> baseWords;		
		@SuppressWarnings("unchecked")
		Map<String, String>[] segDocMap = new Map[docNum];

		
		try{
			tag = new CWSTagger("./models/seg.m");
			StopWords stopWords = new StopWords("./models/stopwords/StopWords.txt"); 
			
			for (Map.Entry<Integer, Map<String,String>> entry : docMapMap.entrySet()){
				id = entry.getKey();
				idIndex = id.intValue();
				segDocMap[idIndex-1] = new HashMap<String,String>();
				for (int i = 0; i < CHILDREN.length; i++){
					sentense = entry.getValue().get(CHILDREN[i]);
					segSentense = tag.tag(sentense);
					words = segSentense.split("\\s");
					baseWords = stopWords.phraseDel(words);
					non_stopwords_segSentense = listToString(baseWords);
					segDocMap[idIndex-1].put(CHILDREN[i], non_stopwords_segSentense);
				}
				segDocMapMap.put(id, segDocMap[idIndex-1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static List<String> segQuestionWords(String questionContent){
    	CWSTagger tag;
    	String seg_questionContent = null;
    	try{
			tag = new CWSTagger("./models/seg.m");
			
			seg_questionContent = tag.tag(questionContent);
		}catch (Exception e) {
            e.printStackTrace();
        }
    	
    	StopWords stopWords = new StopWords("./models/stopwords/StopWords.txt"); 
    	
    	String[] words = seg_questionContent.split("\\s+");		
		List<String> result = stopWords.phraseDel(words);
		
		return result;   	
    }
	
	public static String listToString(List<String> stringList){
         /**
          *  turning a list to a string
          */
		 if (stringList==null) {
		     return null;
		 }
		 StringBuilder result=new StringBuilder();
		 
		 boolean flag=false;
		 
		 for (String string : stringList) {
		     if (flag) {
		         result.append(" ");
		     }
		     else {
		         flag=true;
		     }
		     result.append(string);
		 }
		 return result.toString();
	}
}
