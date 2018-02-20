 package hackpack;

import java.util.ArrayList;

public class Combinatorics {
	int[] items;
	boolean[] inSubset;
	/**
	 * Access all subsets in a set
	 * Total subsets = 2^n
	 * @param i current set item
	 */
	void subset(int i){
		if(i == items.length){
			// process completed subset
		}
		inSubset[i] = true;
		subset(i+1);
		inSubset[i] = false;
		subset(i+1);
	}
	
	//int[] items
	int[] perm;
	boolean[] used;
	/**
	 * Processes each permutation of a set
	 * Maximum of 10 items without restrictions
	 * Total permutations = avalible!/(avalible - needed)!
	 * @param i
	 */
	void permute(int i){
		if(i == items.length){
			// process the permutation
		}
		for(int j = 0; j < items.length;j++){
			if(used[j] == false){
				used[j] = true;
				perm[i] = items[j];
				permute(i + 1);
				used[j] = false;
			}
		}
	}
	
	
	/**
	 * Gets the nth permutation of a lexicographically 
	 * sorted set, given no repetitions in the set
	 * @param set The unique set to use
	 * @param n   The specific permutation to find
	 * @return    The nth permutation
	 */
	String nthPerm(ArrayList<String> set,int n){
		StringBuilder s = new StringBuilder();
		long factorial;
		int pos;
		while (set.size() > 0){
			factorial = factorial(set.size() - 1);
			pos = (int)(n/factorial);
			s.append(set.get(pos));
			set.remove(pos);
		}
		return s.toString();
	}
	long factorial(int n){
		for(int i = 2;i < n;i++){
			n *= i;
		}
		return n;
	}
	
	// int[] items
	boolean[] inCombo;
	int degree;
	/**
	 * Process each combination in a set
	 * Total combinations = avalible!/((avalible-needed)!needed!)
	 * @param i
	 * @param in
	 */
	void combo(int i, int in){
		if(in == degree){
			// process the combination
		}
		if(i < items.length){
			inCombo[i] = true;
			combo(i+1,in+1);
			inCombo[i] = false;
			combo(i+1,in);
		}
	}
}
