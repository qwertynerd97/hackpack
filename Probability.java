package hackpack;

public class Probability {

	int[][] pascal15 = {{1},// 0
						{1,1},
						{1,2,1},
						{1,3,3,1},
						{1,4,6,4,1},
						{1,5,10,10,5,1},
						{1,6,15,20,15,6,1},
						{1,7,21,35,35,21,7,1},
						{1,8,28,56,70,56,28,8,1},
						{1,9,36,84,126,126,84,36,9,1},
						{1,10,45,120,210,252,210,120,45,10,1},
						{1,11,55,165,330,462,462,330,165,55,11,1},
						{1,12,66,220,495,792,924,792,495,220,66,12,1},
						{1,13,78,286,715,1287,1716,1716,1287,715,286,78,13,1},
						{1,14,91,364,1001,2002,3003,3432,3003,2002,1001,364,91,14,1},
						{1,15,105,455,1365,3003,5005,6435,6435,5005,3003,1365,455,105,15,1}};
	int[][] pascal;
	void generatePascal(int n){
		pascal[0] = new int[]{1};
	    for (int i = 1; i < n+1; i++) {
	           pascal[i] = new int[i];
	           pascal[i][0] = 1;
	           pascal[i][i - 1] = 1;
	           for (int j = 1; j < i-1; j++) {
	        	   pascal[i][j] = pascal[i-1][j-1] + pascal[i][j];
	           }
	       }
	}

	double pSucceedsAtLeast(int wins,int rolls, int possibilities){
		double pLoss = 0.5; // replace with P(loss) in one roll
		double pWin = 0.5; // replace with P(success) in one roll
		double totLoss = 0.0; // running total
		
		// Go through each possible failing roll
		for(int i = 0; i < wins;i++){
			totLoss += pascal15[possibilities][i]*Math.pow(pWin,i)*Math.pow(pLoss,rolls-i);
		}
		return 1-totLoss;
	}
	
	
}
