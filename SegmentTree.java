package hackpack;


public class SegmentTree {
	int n; 			// nodes in tree
	int[] delta; 	// changes to push down tree; per node
	int[] low; 		// low end of range, inclusive; per node
	int[] high; 	// high end of range, inclusive; per node
	
	// Optional variables depending on tree use
	int[] minVal;
	int[] maxVal;
	int[] sum;
	
	
	/**
	 * Create and initialize the Segment Tree values
	 * 
	 * @param size The length of the array
	 */
	public SegmentTree(int size){
		n = 	4*size + 1; // add plenty of extra nodes - its an uneven binary tree
		delta = new int[n];
		low = 	new int[n];
		high = 	new int[n];
		
		// Initialize optional holders
		minVal = 	new int[n];
		maxVal = 	new int[n];
		sum = 		new int[n];
		
		// Initialize the tree
		init(1,0,size-1);
	}
	
	/**
	 * Set up all nodes in the range
	 * 
	 * @param i The current node
	 * @param a The high end of the range, inclusive
	 * @param b The low end of the range, inclusive
	 */
	private void init(int i, int a, int b){
		// Set up the interval
		low[i] = a;
		high[i] = b;
		
		// If its a leaf, do nothing
		if(a == b){
			return;
		}
		
		// If it has children, initialize them
		int middle = (a+b)/2;
		init(2*i,a,middle);
		init(2*i + 1,middle+1,b);
	}
	
	/**
	 * Add a value to all items in the given range from a to b, inclusive
	 * 
	 * @param i the current node - Should always be 1 in outside calls
	 * @param a The minimum value of the range, inclusive
	 * @param b The maximum value of the range, exclusive
	 * @param value The value to add to each item in the range
	 */
	public void add(int i, int a, int b, int value){
		// If this node is outside of the range, do nothing
		if(high[i] < a || b < low[i]){
			return;
		}
		
		// If this node is entirely within the range, update it
		if(a <= low[i] && high[i] <= b){
			delta[i] += value; 	// Increment the flag with the given value
			update(i); 			// Update the children
			return;
		}
		
		// If the node is partially in the range, go down to the children
		
		propagate(i); 				// Propagate the changes down to the children
		
		add(2*i,a,b,value);      	// Add to the left child
		add(2*i + 1,a,b,value);		// Add to the right child
		
		update(i); 					// Update the current values based on the children
	}
	
	/**
	 * Sends change flags down the tree
	 * 
	 * @param i The current node
	 */
	private void propagate(int i){
		delta[2*i] += delta[i];
		delta[2*i + 1] += delta[i];
		delta[i] = 0;
	}
	
	/**
	 * Update stored values back up the tree
	 * Change this method to reflect what is being stored
	 * 
	 * @param i The current node
	 */
	private void update(int i){
		// Minimum is the change plus the minimum of the children, because
		// change will eventually move down to all children
		minVal[i] = delta[i] + Math.min(minVal[2*i], minVal[2*i + 1]);
		
		// Maximum is the change plus the max of the children, because
		// change will eventually move down to all children
		maxVal[i] = delta[i] + Math.max(maxVal[2*i], maxVal[2*i + 1]);
		
		// Sum is the sum of children plus the change times the range, because
		// the change will be added to each object in the range
		sum[i] = 	sum[2*i] + sum[2*i+1] + delta[i]*(high[i]-low[i] + 1);
	}
	
	/**
	 * Find the minimum value of a range
	 * @param i The current node.  External calls should always start with 1
	 * @param a The lower end of the range, inclusive
	 * @param b The upper end of the range, inclusive
	 * @return The smallest value in the range
	 */
	public int minQuery(int i, int a, int b){
		// If this node is outside of the range, return an absolute maximum
		if(high[i] < a || b < low[i]){
			return Integer.MAX_VALUE;
		}
				
		// If this node is entirely within the range, return its minimum
		if(a <= low[i] && high[i] <= b){
			return minVal[i];
		}
		
		// If the node is partially in the range, go down to the children
		
		propagate(i);							// Propagate the changes down to the children
		
		int leftMin = minQuery(2*i,a,b);		// Find the minimum of the left child
		int rightMin = minQuery(2*i +1,a,b);	// Find the minimum of the right child
		
		update(i);								// Update the current values based on the children
		
		return Math.min(leftMin,rightMin);		// Return the minimum of the children
	}
	
	/**
	 * Find the minimum value of a range
	 * @param i The current node.  External calls should always start with 1
	 * @param a The lower end of the range, inclusive
	 * @param b The upper end of the range, inclusive
	 * @return The largest value in the range
	 */
	public int maxQuery(int i, int a, int b){
		// If this node is outside of the range, return an absolute minimum
		if(high[i] < a || b < low[i]){
			return Integer.MIN_VALUE;
		}
				
		// If this node is entirely within the range, return its maximum
		if(a <= low[i] && high[i] <= b){
			return maxVal[i];
		}
		
		// If the node is partially in the range, go down to the children
		
		propagate(i);							// Propagate the changes down to the children
		
		int leftMax = maxQuery(2*i,a,b);		// Find the maximum of the left child
		int rightMax = maxQuery(2*i +1,a,b);	// Find the maximum of the right child
		
		update(i);								// Update the current values based on the children
		
		return Math.max(leftMax,rightMax);		// Return the maximum of the children
	}
	
	/**
	 * Find the sum of a range
	 * @param i The current node.  External calls should always start with 1
	 * @param a The lower end of the range, inclusive
	 * @param b The upper end of the range, inclusive
	 * @return The sum of all numbers in the range
	 */
	public int sumQuery(int i, int a, int b){
		// If this node is outside of the range, return zero
		if(high[i] < a || b < low[i]){
			return 0;
		}
				
		// If this node is entirely within the range, return its sum
		if(a <= low[i] && high[i] <= b){
			return sum[i];
		}
		
		// If the node is partially in the range, go down to the children
		
		propagate(i);							// Propagate the changes down to the children
		
		int leftSum = sumQuery(2*i,a,b);		// Find the sum of the left child
		int rightSum = sumQuery(2*i +1,a,b);	// Find the sum of the right child
		
		update(i);								// Update the current values based on the children
		
		return leftSum + rightSum;				// Return the sum of the children
	}
}

