package hackpack;

public class NthStatisitc {
	
	public int[] getKMedians(int k, int[] nums){
		// median of an array is n/2
		// k/2 before the median is n/2-k/2 = (n-k)/2
		// k/2 after median is n/2+k/2 = (n+k/2)
		
		// Get statistic of (n-k)/2
		int upper = getNthStatistic((nums.length-k)/2,nums,0,nums.length-1);
		
		// Get statistic of (n+k)/2
		int lower = getNthStatistic((nums.length+k)/2,nums,0,nums.length-1);
		
		// Loop through array and save items that are between upper and lower
		int[] answers = new int[k];
		int counter = 0;
		for(int i = 0; i < nums.length || counter < k;i++){
			if(nums[i] >= upper && nums[i] <= lower){
				answers[counter++] = nums[i];
			}
		}
		return answers;
	}
	 
	public int getNthStatistic(int n, int[] nums, int start, int end) {
		int pivot = nums[end];
	 
		int left = start;
		int right = end;
	 
		// Loop through partitioner
		while (true) {
			while (nums[left] < pivot && left < right) {
				left++;
			}
			while (nums[right] >= pivot && right > left) {
				right--;
			}
			if (left == right) {
				break;
			}
			int temp = nums[left];
			nums[left] = nums[right];
			nums[right] = temp;
		}
		int temp = nums[left];
		nums[left] = nums[end];
		nums[end] = temp;
	 
		if (n == left+1) {
			return pivot;
		}else if (n < left+1) {
			return getNthStatistic(n, nums, start, left-1);
		}else {
			return getNthStatistic(n, nums, left+1, end);
		}
	}
	
	public int cutRods(int[] prices, int n, int cost){
	int[] bests = new int[n+1];
	for(int i = 0; i <= n; i++){
		int curMax = prices[i];
		for(int j = 1; j < i; j++){
			curMax = Math.max(curMax, prices[i] + bests[i-j] - cost);
		}
		bests[i] = curMax;
	}
	return bests[n];
}
}
