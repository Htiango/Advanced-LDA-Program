package ldaModeling2;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



/**
 * a set of documents
 * 
 * @author hty
 *
 */
public class Corpus {
	
	/**
	 * each represents a doc with words' id index
	 */
	List<int[]> documentList;
	
	/**
	 * key is the index of the doc, value is the id index of the author of the doc
	 */
	Map<Integer, Integer> doc2UserMap;
	
	Vocabulary vocabulary;
	User user;
	 
	/**
	 * the child mode of the seg info 
	 */
	private static String[] CHILDREN = 
	    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
	 
	/**
	 * the child mode which represent the user
	 */
	private static String[] CHILDREN2 = 
	    	{"回复人1姓名","回复人2姓名","回复人3姓名","回复人4姓名","回复人5姓名"};
	
	/**
     * The child mode of the question content from the segged map
     */
    private static String CHILDREN3 = "问题内容";
	
	
	public Corpus(int wordsNum, int userNum){
		documentList = new LinkedList<int[]>();
		doc2UserMap = new HashMap<Integer, Integer>();
		vocabulary = new Vocabulary(wordsNum);
		user = new User(userNum);
	}
	
	/**
	 * change the doc from string[] to int[]
  	 * @param document -- a group of words, one doc
	 * @return int[] which replace each word with its id index
	 */
	public int[] addDocument(List<String> document){
		int[] doc = new int[document.size()];
		int i = 0;
		for (String word : document){
			doc[i++] = vocabulary.getId(word, true);
		}
		documentList.add(doc);
		return doc;
	}
	
	/**
	 * add the userId to the Map
	 * @param userName the name of the author of a doc
	 * @return the id index of the user
	 */
	public int addUser(String userName){
		int docIndex = documentList.size() - 1;
		int userId = user.getId(userName, true);
		
		doc2UserMap.put(Integer.valueOf(docIndex), Integer.valueOf(userId));
		return userId;
	}
	
	/**
	 * change the linkedList to a 2-D Array
	 * @return a 2-D Array, 1st-D represent the index of the doc, 2nd-D is the words' id
	 */
	public int[][] toArray(){
		return documentList.toArray(new int[0][]);
	}
	
	/**
	 * get the num of unique words from the size of the vocabulary
	 * @return the number of the unique words
	 */
	public int getVocabularySize(){
		return vocabulary.size();
	}
	
	/**
	 * get the num of the unique user from the size of the user
	 * @return the num of the unique user
	 */
	public int getUserSize(){
		return user.size();
	}
	
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		for(int[] doc:documentList){
			sb.append(Arrays.toString(doc)).append("\n");
		}
		sb.append(vocabulary).append("\n");
		sb.append(user);
		return sb.toString();
	}
	
	/**
	 * load document from preprocessed data
	 * @param segDocMapMap  a doc Map that already be segged
	 * @param docMapMap     a doc Map that is not segged
	 * @param wordsNum      the num of the unique words
	 * @param userNum       the num of the unique user
	 * @return a corpus
	 */
	public static Corpus loading(Map<Integer, Map<String, String>> segDocMapMap,
			Map<Integer, Map<String, String>> docMapMap,
			int wordsNum, int userNum){
		Corpus corpus = new Corpus(wordsNum, userNum);
		String segSentense;
		
		for (Map.Entry<Integer, Map<String, String>>entry : segDocMapMap.entrySet())
		{
			Integer docIndex = entry.getKey();
			segSentense = entry.getValue().get(CHILDREN3);
			List<String> wordList = new LinkedList<String>();
			
			if (segSentense != null){
				String[] words = segSentense.split(" ");
				for(String word : words){
					if (word.trim().length()<2) continue;
					wordList.add(word);
				}
			}
			corpus.addDocument(wordList);
			
			for(int i = 0; i < CHILDREN2.length;i++){
				
				String userName = docMapMap.get(docIndex).get(CHILDREN2[i]);
				
//				List<String> wordList = new LinkedList<String>();
				
//				if (segSentense != null){
//					String[] words = segSentense.split(" ");
//					for(String word : words){
//						if (word.trim().length()<2) continue;
//						wordList.add(word);
//					}
//				}
//				corpus.addDocument(wordList);
				corpus.addUser(userName);
			}
		}
		
		if (corpus.getVocabularySize() == 0 || corpus.getUserSize() == 0){
			return null;
		}
		
		return corpus;
	}
	
	public Vocabulary getVocabulary(){
		return vocabulary;
	}
	
	public User getUser(){
		return user;
	}
	
	public int[][] getDocument(){
		return toArray();
	}
		
	public Map<Integer, Integer> getDoc2UserMap(){
		return doc2UserMap;
	}
	
}

