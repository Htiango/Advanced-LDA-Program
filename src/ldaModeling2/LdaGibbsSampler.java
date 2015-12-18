package ldaModeling2;

import java.util.Map;

public class LdaGibbsSampler {
	
	/**
	 * document data (with each word's id index) <br>
	 * 文档，每条是words的id
	 */
	int[][] documents;
	
	/**
	 * 
	 */
	Map<Integer, Integer> doc2UserMap;
	
	/**
	 * vocabulary size <br>
	 * 词表大小
	 */
	int V;
	
	/**
	 * user size <br>
	 * 专家的人数
	 */
	int U;
	
	/**
	 * number of topics <br> 
	 * 主题数目
	 */
	int K;
	
	/**
	 * number of expertise <br> 
	 * 专长数目
	 */
	int E;
	
	/**
	 * Dirichlet parameter (document--topic associations)<br> 
	 * 文档——主题的Dirichlet分布参数
	 */
	double alpha = 2.0;
	
	/**
	 * Dirichlet parameter (topic, expertise--words associations) <br> 
	 * 主题，专长——词语的Dirichlet分布参数
	 */
	double beta = 0.5;
	
	/**
	 * Dirichlet parameter (user--expertise associations)<br> 
	 * 用户——专长的Dirichlet分布参数
	 */
	double gamma = 50;

	/**
	 * topic assignments for each word <br> 
	 * 每个词语的主题 z[i][j] := 文档i的第j个词语的主题编号
	 */
	int z[][];
	
	/**
	 * expertise assignments for each word <br> 
	 * 每个词语的专长 x[i][j] := 文档i的第j个词语的专长编号
	 */
	int x[][];
	
	/**
	 * nmz[m][k] M*K  := number of words in document m assigned to topic k<br> 
	 * 计数器，nmz[m][k] := 文档m中归入主题k的词语的个数
	 */
	int nmz[][];
	
	/**
	 * nux[u][e] U*E   := number of words from user u assigned to expertise e <br>
	 * 计数器，nux[u][e] := 作者u中归入专长x的词语的个数
	 */
	int nux[][];
	
	/**
	 * nzxw[k][e][i] number of word i assigned to topic k and expertise e<br> 
	 * 计数器，nzxw[k][e][i] := 词语i归入主题k和专长e的次数
	 */
	int nzxw[][][];
	
	/**
	 * nzxsum[k][e] total number of words assigned to topic k and expertise e<br>
	 * 计数器，nzxsum[k][e] := 归入主题k，专长e的词语的个数
	 */
	int nzxsum[][];
	
	/**
	 * nwsum[i]  total number of words in document i <br>
	 * 计数器， nwsum[i] := 文档i中全部词语的数量
	 */
	int nwsum[];
	
	/**
	 * nusum[u]  total number of words from user u <br>
	 * 计数器， nusum[u] := 来自于专家u的词语的个数 
	 */
	int nusum[];
	
	/**
	 * cumulative statistics of theta<br>
	 * theta is multinomial mixture of document topics (M x K)<br>
	 * theta ：文档——主题矩阵
	 */
	double thetasum[][];
	
	/**
	 * cumulative statistics of phi<br>
	 * phi is multinomial mixture of topic\expertise words ( K x E x V )<br>
	 * phi := 主题、专长——词语矩阵
	 */
	double phisum[][][];
	
	/**
	 * cumulative statistics of psi<br>
	 * psi is multinomial mixture of user expertise (U x E) <br>
	 * psi := 用户——专长矩阵
	 */
	double psisum[][];
	
	/**
     * size of statistics<br>
     * 样本容量
     */
    int numstats;
    
    /**
     * sampling lag (?)<br>
     * 多久更新一次统计量
     */
    private static int THIN_INTERVAL = 20;

    /**
     * burn-in period<br>
     * 收敛前的迭代次数
     */
    private static int BURN_IN = 100;

    /**
     * max iterations<br>
     * 最大迭代次数
     */
    private static int ITERATIONS = 1000;

    /**
     * sample lag (if -1 only one sample taken)<br>
     * 最后的模型个数（取收敛后的n个迭代的参数做平均可以使得模型质量更高）
     */
    private static int SAMPLE_LAG = 10;

    private static int dispcol = 0;
	
    
    /**
     * Initialise the Gibbs Sampler with data. <br>
     * 用数据初始化采样器
     * 
     * @param documents 文档
     * @param V         vocabulary size 词表大小
     * @param U			User size 专家人数
     */
	public LdaGibbsSampler(int[][]documents, Map<Integer, Integer> doc2UserMap ,int V, int U){
		this.documents = documents;
		this.doc2UserMap = doc2UserMap;
		this.V = V;
		this.U = U;
	}

	/**
	 * Initialisation: Must start with an assignment of observations to topics and expertises <br>
	 * 随机初始化状态
	 * @param K  number of topics    K个主题
	 * @param E  number of expertise E个专长
	 */
	public void initialState(int K, int E){
		int M = documents.length;
		
		// initialise count variables. 初始化计数器
		nmz = new int[M][K];
		nux = new int[U][E];
		nzxw = new int[K][E][V];
		nzxsum = new int[K][E];
		nwsum = new int[M];
		nusum = new int[U];
		
		// The z_i are are initialised to values in [1,K] to determine the
        // initial state of the Markov chain.
		z = new int[M][]; // z_i := 1到K之间的值，表示马氏链的初始状态
		x = new int[M][];
		for(int m = 0; m < M; m++){
			int N = documents[m].length;
			z[m] = new int[N];
			x[m] = new int[N];
			int userId = doc2UserMap.get(m).intValue();
			for(int n = 0; n < N; n++){
				int topic = (int)(Math.random() * K);
				int expertise = (int)(Math.random() * E);
				// index of the topic assigned to this word
				z[m][n] = topic;
				// index of the expertise assigned to this word
				x[m][n] = expertise;
				// number of words in document m assigned to topic topic
				nmz[m][topic]++;
				// number of words from user userId assigned to expertise expertise
				nux[userId][expertise]++;
				// number of instances of word i assigned to certain topic and expertise
				nzxw[topic][expertise][documents[m][n]]++;
				// total number of words assigned to certain topic and expertise 
				nzxsum[topic][expertise]++;
			}
			// total number of words in document m
			nwsum[m] = N;
		    //  total number of words from user u
			nusum[userId] += N;
		}		
	}
	
	/**
	 * Main method: Select initial state and repeat a large number of times:<br>
	 * 1. Select an element <br>
	 * 2. Update conditional on other element. If appropriate, output summary for each run.<br>
	 * 吉布斯采样
	 * @param K			number of topics 		主题数
	 * @param E			number of expertise 	专长数
	 * @param alpha		symmetric prior parameter on document--topic associations 对称文档——主题先验概率
	 * @param beta		symmetric prior parameter on topic\expertise--term associations 对称主题、专长——词语先验概率
	 * @param gamma		symmetric prior parameter on user--expertise associations 对称专家——专长先验概率
	 */
	public void gibbs(int K, int E, double alpha, double beta, double gamma){
		this.K = K;
		this.E = E;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		
		// init sampler statistics 分配内存
		if (SAMPLE_LAG > 0){
			thetasum = new double[documents.length][K];
			phisum = new double[K][E][V];
			psisum = new double[U][E];
			numstats = 0;
		}
		
		// initial state if the Markov chain:
		initialState(K,E);
		
		System.out.println("Sampling " + ITERATIONS
                + " iterations with burn-in of " + BURN_IN + " (B/S="
                + THIN_INTERVAL + ").");
		
		for ( int i = 0 ; i < ITERATIONS; i++){
			// for all z_i
			for (int m = 0; m < documents.length; m++){
				int userId = doc2UserMap.get(m).intValue();
				
				for (int n = 0; n < documents[m].length; n++){
					
					// z_i = z[m][n]
					// sample from the formula .32 (see it in my paper!)
					int[] topicExpertise = new int[2];
					topicExpertise = sampleFullConditional(m,n, userId);
					
					int topic = topicExpertise[0];
					int expertise = topicExpertise[1];
					
					z[m][n] = topic;
					x[m][n] = expertise;
				}	
			}
            if ((i < BURN_IN) && (i % THIN_INTERVAL == 0))
            {
                System.out.print("B");
                dispcol++;
            }
            // display progress
            if ((i > BURN_IN) && (i % THIN_INTERVAL == 0))
            {
                System.out.print("S");
                dispcol++;
            }
            // get statistics after burn-in
            if ((i > BURN_IN) && (SAMPLE_LAG > 0) && (i % SAMPLE_LAG == 0))
            {
                updateParams();
                System.out.print("|");
                if (i % THIN_INTERVAL != 0)
                    dispcol++;
            }
            if (dispcol >= 100)
            {
                System.out.println();
                dispcol = 0;
            }
		}
		System.out.println();
	}
	
	/**
	 * sample from the formula .32 (see it in my paper!)
	 * @param m			number of document
	 * @param n			number of words
	 * @param userId	index of user Id	
	 * @return [topic, expertise]
	 */
	private int[] sampleFullConditional(int m, int n, int userId){
		int topic = z[m][n];
		int expertise = x[m][n];
		
		// remove word from the count variables  先将这个词从计数器中抹掉
		nmz[m][topic]--;
		nux[userId][expertise]--;
		nzxw[topic][expertise][documents[m][n]]--;
		nzxsum[topic][expertise]--;
		nwsum[m]--;
		nusum[userId]--;
		
		double pMax = 0; 
		int topicIndex = 0;
		int expertiseIndex = 0;
		
		double[][] p = new double[K][E];
		for (int k = 0; k<K; k ++){
			for (int e = 0; e < E; e++){
				p[k][e] = (nzxw[k][e][documents[m][n]] + beta) / (nzxsum[k][e] + V * beta)
						* (nmz[m][k] + alpha) / (nwsum[m] + K * alpha)
						* (nux[userId][e] + gamma) / (nusum[userId] + E * gamma);
				if (p[k][e] > pMax){
					pMax = p[k][e];
					topicIndex = k;
					expertiseIndex = e;
				}
			}
		}
		
		topic = topicIndex; 
		expertise = expertiseIndex;
				
//		double[] cdf = new double[K*E]; // cumulate multinomial parameters 累加多项式分布的参数
//
//		int cdfIndex = 0;
//		
//		// do multinomial sampling via cumulative method: 通过多项式方法采样多项式分布
//		double[][] p = new double[K][E];
//		for (int k = 0; k<K; k ++){
//			for (int e = 0; e < E; e++){
//				p[k][e] = (nzxw[k][e][documents[m][n]] + beta) / (nzxsum[k][e] + V * beta)
//						* (nmz[m][k] + alpha) / (nwsum[m] + K * alpha)
//						* (nux[userId][e] + gamma) / (nusum[userId] + E * gamma);
//				
//				cdf[cdfIndex] += p[k][e];
//				if (k!=0 || e!=0){
//					cdf[cdfIndex] += cdf[cdfIndex-1];					
//				}
//				cdfIndex ++;
//			}
//		}
//		
//		double u = Math.random() * cdf[K*E-1];
//		Out:
//			for(topic = 0; topic < K; topic++){
//				for(expertise = 0; expertise < E; expertise++){
//					if(u < cdf[topic * E + expertise])
//						break Out;
//				}
//			}
		
		nmz[m][topic]++;
		nux[userId][expertise]++;
		nzxw[topic][expertise][documents[m][n]]++;
		nzxsum[topic][expertise]++;
		nwsum[m]++;
		nusum[userId]++;
		
		int[] result = new int[2];
		result[0] = topic;
		result[1] = expertise; 
		
		return result;
	}
	
	/**
	 * Add to the statistics the values of theta, phi and psi for the current state.<br>
     * 更新参数  详见公式33，34，35
	 */
	private void updateParams(){
		for(int m = 0; m< documents.length; m++){
			for(int k= 0; k < K; k++){
				thetasum[m][k] += (nmz[m][k] + alpha) / (nwsum[m] + K * alpha);
			}
		}
		for(int k =0; k<K; k++){
			for(int e = 0; e<E; e++){
				for (int w = 0; w<V; w++){
					phisum[k][e][w] += (nzxw[k][e][w] + beta) / (nzxsum[k][e] + V * beta);
				}
			}
		}
		for(int u = 0 ; u < U; u++){
			for(int e = 0; e < E; e++){
				psisum[u][e] += (nux[u][e] + gamma) / (nusum[u] + E * gamma);
			}
		}	
		numstats ++;
	}
	
	/**
	 * Retrieve estimated document--topic associations. If sample lag > 0 then
     * the mean value of all sampled statistics for theta[][] is taken.
     * 获取文档——主题矩阵
     * 
	 * @return  theta multinomial mixture of document topics (M x K)
	 */
	public double[][] getTheta(){
		double[][] theta = new double[documents.length][K];
		 if (SAMPLE_LAG > 0){
			 for (int m = 0; m < documents.length; m++){
				 for (int k = 0; k < K; k++){
					 theta[m][k] = thetasum[m][k] / numstats;
				 }
			 }
		 }
		 else{
			 for (int m = 0; m < documents.length; m++){
				 for (int k = 0; k < K; k++){
					 theta[m][k] = (nmz[m][k] + alpha) / (nwsum[m] + K * alpha);
				 }
			 }
		 }
		 return theta;	        
	}
	
	/**
	 * Retrieve estimated topic\expertise--word associations. If sample lag > 0 then the
     * mean value of all sampled statistics for phi[][][] is taken.<br>
     * 获取主题、专长——词语矩阵
	 * @return phi  multinomial mixture of topic\expertise words ( K x E x V)
	 */
	public double[][][] getPhi(){
		double[][][] phi = new double[K][E][V];
		if (SAMPLE_LAG > 0){
			for (int k = 0; k < K; k++){
				for(int e = 0; e < E; e++){
					for(int w = 0; w < V; w++){
						phi[k][e][w] = phisum[k][e][w] / numstats;
					}
				}
			}
		}
		else{
			for (int k = 0; k < K; k++){
				for(int e = 0; e < E; e++){
					for(int w = 0; w < V; w++){
						phi[k][e][w] = (nzxw[k][e][w] + beta) / (nzxsum[k][e] + V * beta);
					}
				}
			}
		}
		return phi;
	}
	
	/**
	 * Retrieve estimated user--expertise associations. If sample lag > 0 then
     * the mean value of all sampled statistics for psi[][] is taken.
	 * @return psi multinomial mixture of user expertise (U x E) 
	 */
	public double[][] getPsi(){
		double[][] psi =new double[U][E];
		if (SAMPLE_LAG > 0){
			for (int u = 0; u < U; u++){
				for (int e = 0; e < E; e++){
					psi[u][e] = psisum[u][e] / numstats;
				}
			}
		}
		else{
			for (int u = 0; u < U; u++){
				for (int e = 0; e < E; e++){
					psi[u][e] = (nux[u][e] + gamma) / (nusum[u] + E * gamma);
				}
			}
		}
		return psi;
	}
	
	/**
     * Configure the gibbs sampler<br>
     * 配置采样器
     *
     * @param iterations   number of total iterations
     * @param burnIn       number of burn-in iterations
     * @param thinInterval update statistics interval
     * @param sampleLag    sample interval (-1 for just one sample at the end)
     */
    public void configure(int iterations, int burnIn, int thinInterval,
                          int sampleLag)
    {
        ITERATIONS = iterations;
        BURN_IN = burnIn;
        THIN_INTERVAL = thinInterval;
        SAMPLE_LAG = sampleLag;
    }

	
}
