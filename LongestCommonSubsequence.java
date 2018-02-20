package hackpack;

import java.util.Scanner;

public class LongestCommonSubsequence {

	// Assumes memo has been initialized and filled with -1
	public static int lcs(int[][] memo, char[] first, char[] second, int m, int n){
	    if(memo[m][n] != -1){
	    	return memo[m][n];
	    }
	    
	    if(m == 0 || n == 0){
	    	memo[m][n] = 0;
	    	return 0;
	    }
	    
	    if(first[m-1] == second[n-1]){
	    	memo[m][n] =  1 + lcs(memo,first,second,m-1,n-1);
	    	return memo[m][n];
	    }
	    
	    memo[m][n] = Math.max(lcs(memo,first,second,m,n-1), lcs(memo,first,second,m-1,n));
	    return memo[m][n];
	 }
	
	public static String longestPalindrome(String str, int length){
		int[][] memo = new int[length+1][length+1];
		for(int i = 0;i < length+1; i++){
			for(int j = 0; j < length+1; j++){
				memo[i][j] = -1;
			}
		}
		
		char[] original = str.toCharArray();
		char[] reverse = new char[length];
		for(int i = 0; i < length; i++){
			reverse[(length-1)-i] = original[i];
		}
		
		int size = lcs(memo, original, reverse, length, length);
		char[] pali = new char[size];
		
		int i = length;
		int j = length;
		int currIndex = size-1;
		
		while(i > 0 && j > 0){
			if(original[i-1] == reverse[j-1]){
				pali[currIndex] = original[i-1];
				
				i--;
				j--;
				currIndex--;
			}else if(memo[i-1][j] > memo[i][j-1]){
				i--;
			}else{
				j--;
			}
		}
		
		return new String(pali);
		
	}

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		while(true){
			String word = scan.next();
			System.out.println(longestPalindrome(word,word.length()));
		}
	}
}

/*
character
civic
abcdefghijklmnopqrstuvwxyz
*/