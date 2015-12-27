package ldaModeling1;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * a set of documents
 * 
 *
 * @author hty
 */
public class Corpus
{
    List<int[]> documentList;
    Vocabulary vocabulary;
    public static String[] CHILDREN = 
    	{"回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
//    private static ArrayList<ArrayList <String>> list2;

    public Corpus(int wordsNum)
    {
        documentList = new LinkedList<int[]>();
        vocabulary = new Vocabulary(wordsNum);
    }

    public int[] addDocument(List<String> document)
    {
        int[] doc = new int[document.size()];
        int i = 0;
        for (String word : document)
        {
            doc[i++] = vocabulary.getId(word, true);
        }
        documentList.add(doc);
        return doc;
    }

    public int[][] toArray()
    {
        return documentList.toArray(new int[0][]);
    }

    public int getVocabularySize()
    {
        return vocabulary.size();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (int[] doc : documentList)
        {
            sb.append(Arrays.toString(doc)).append("\n");
        }
        sb.append(vocabulary);
        return sb.toString();
    }

    /**
     * Load documents from disk
     *
     * @param folderPath is a folder, which contains text documents.
     * @return a corpus
     * @throws IOException
     */
    public static Corpus loading(Map<Integer, Map<String,String>> segDocMapMap, int wordsNum) 
    {
        Corpus corpus = new Corpus(wordsNum);
        String segSentense;

        for (Map.Entry<Integer, Map<String,String>> entry : segDocMapMap.entrySet())
        {
        	for (int i = 0; i < CHILDREN.length; i++){
        		segSentense = entry.getValue().get(CHILDREN[i]);        
            
	            List<String> wordList = new LinkedList<String>();
	            if (segSentense != null)
	            {
	                String[] words = segSentense.split(" ");
	                for (String word : words)
	                {
	                    if (word.trim().length() < 2) continue;
	                    wordList.add(word);
	                }
	            }
	            corpus.addDocument(wordList);
        	}
        }
        if (corpus.getVocabularySize() == 0) return null;

        return corpus;
    }
    
    
    
    public Vocabulary getVocabulary()
    {
        return vocabulary;
    }

    public int[][] getDocument()
    {
        return toArray();
    }

    public static int[] loadDocument(String path, Vocabulary vocabulary) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        List<Integer> wordList = new LinkedList<Integer>();
        while ((line = br.readLine()) != null)
        {
            String[] words = line.split(" ");
            for (String word : words)
            {
                if (word.trim().length() < 2) continue;
                Integer id = vocabulary.getId(word);
                if (id != null)
                    wordList.add(id);
            }
        }
        br.close();
        int[] result = new int[wordList.size()];
        int i = 0;
        for (Integer integer : wordList)
        {
            result[i++] = integer;
        }
        return result;
    }
}
