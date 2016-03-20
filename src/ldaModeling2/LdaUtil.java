package ldaModeling2;

import java.util.*;

public class LdaUtil {
	
	/**
	 * To translate a LDA matrix to a readable Map result
	 * @param phi			the LDA model
	 * @param vocabulary	
	 * @param limit			limit of max words in a topic
	 * @return	a 2-D map array   K x E
	 */
	public static Map<String, Double>[][] translate(double[][][] phi, Vocabulary vocabulary, int limit)
	{
		limit = Math.min(limit, phi[0][0].length);
		
		int K = phi.length;
		int E = phi[0].length;
		
		@SuppressWarnings("unchecked")
		Map<String, Double>[][] result = new Map[K][E];
		
		for(int k = 0; k < K; k++){
			
			for(int e = 0; e < E; e++){
				Map<Double, String> rankMap = new TreeMap<Double, String>(Collections.reverseOrder());
				for(int w = 0; w < phi[k][e].length; w++){
					rankMap.put(phi[k][e][w], vocabulary.getWord(w));
				}
				Iterator<Map.Entry<Double,String>> iterator = rankMap.entrySet().iterator();
				result[k][e] = new LinkedHashMap<String, Double>();
				for(int i = 0; i < limit; i++){
					if(iterator.hasNext()){
						Map.Entry<Double, String> entry = iterator.next();
						result[k][e].put(entry.getValue(), entry.getKey());
					}
				}				
			}			
		}
		return result;
	}
	
	/**
	 * To print the result in a well formatted form
	 * @param result
	 */
	public static void explain(Map<String, Double>[][] result){
//		int K = result.length;
//		int E = result[0].length;
		int topic = 0;
		for (Map<String, Double>[] topicExpertiseMaps : result){
			int expertise = 0;
			for (Map<String,Double> topicExpertiseMap : topicExpertiseMaps){
				System.out.printf("topic %d, expertise %d :\n", topic, expertise++);
				explain(topicExpertiseMap);
				System.out.println();
			}
			topic ++; 
		}
	}
	
	public static void explain(Map<String, Double> topicExpertiseMap){
		for(Map.Entry<String, Double> entry : topicExpertiseMap.entrySet()){
			System.out.println(entry);
		}
	}

}
