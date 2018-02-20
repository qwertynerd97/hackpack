package hackpack;

public class MaxSubarray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int[] slowMax(int[] array, int low, int high){
		int[] sums = new int[array.length];
		sums[0] = array[0];
		for(int i = 1; i < array.length; i++){
			sums[i] = array[i] + sums[i-1];
		}
		
		int currMax = sums[0];
		int bestLower = 0;
		int bestUpper = 0;
		int sum = 0;
		
		for(int i = 0; i < array.length; i++){
			for(int j = i+1; j < array.length; j++){
				sum = sums[j] - sums[i];
				if(sum > currMax){
					currMax = sum;
					bestLower = i+1;
					bestUpper = j;
				}
			}
		}
		
		return new int[]{bestLower,bestUpper,currMax};
	}
	
	/**
	 * Recursively finds the max continuous subarray from a given array
	 * @param array The array to find the max subarray
	 * @param low The smallest array index, inclusive
	 * @param high The largest array index, inclusive
	 * @return (left index, right index, subarray sum)
	 */
	public static int[] findMax(int[] array, int low, int high){
		// Arrays of size 1 are their own max
		if(high==low){
			return new int[]{low, high, array[low]};
		}
		
		// Get the possible subsets to compare
		int mid = high/2;
		int[] left = findMax(array, low, mid);
		int[] right = findMax(array, mid+1, high);
		int[] cross = findMaxCrossing(array,low,mid,high);
		
		// return the highest subset
		if(left[2] >= cross[2] && left[2] >= right[2]){
			return left;
		}else if(cross[2] > left[2] && cross[2] >= right[2]){
			return cross;
		}else{
			return right;
		}
		
	}
	
	public static int[] findMaxCrossing(int[] array, int low, int mid, int high){
		// find max array[low...mid]
		int leftsum = Integer.MIN_VALUE;
		int sum = 0;
		int maxleft = mid;
		
		for(int i=mid; i>= low; i--){
			sum+=array[i];
			if(sum>leftsum){
				leftsum = sum;
				maxleft = i;
			}
		}
		
		int rightsum = Integer.MIN_VALUE;
		sum = 0;
		int maxright = mid+1;
		
		for(int i=mid+1; i <= high; i++){
			sum+=array[i];
			if(sum>rightsum){
				rightsum = sum;
				maxright = i;
			}
		}
		
		return new int[]{maxleft,maxright,leftsum+rightsum};
	}

}
