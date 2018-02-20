package hackpack;

public class QuickSort {
	public void quickSort(int[] arr, int low, int high)
	{
	    if (low < high){
	        int pivot = arr[high];  
	        int i = (low - 1);

	        for (int j = low; j <= high- 1; j++){
	            if (arr[j] >= pivot){
	                i++;
	                int temp = arr[i];
	                arr[i] = arr[j];
	                arr[j] = temp;
	            }
	        }
	        int temp = arr[i+1]; 
	        arr[i+1] = arr[high];
	        arr[high] = temp;
	       
	        int partIndex = i+1;

	        quickSort(arr, low, partIndex-1);  
	        quickSort(arr, partIndex + 1, high); 
	    }
	}
}
