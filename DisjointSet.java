package hackpack;

public class DisjointSet {
	Pair[] disjoint;
	int trees;
	
	public DisjointSet(int num){
		trees = num;
		disjoint = new Pair[num];
		for(int i = 0; i < num; i++){
			disjoint[i] = new Pair(i,0);
		}
	}
	
	// Finds parent in lg(n) time
	public int find(int node){
		while(disjoint[node].value != node){
			node = disjoint[node].value;
		}
		return node;
	}
	
	// Joins two disjoint trees together, returns if successful
	public boolean union(int a, int b){
		int rootA = find(a);
		int rootB = find(b); // 2lgn time
		
		if(rootA == rootB){
			return false;
		}
		
		if(disjoint[rootA].height < disjoint[rootB].height){
			disjoint[rootA].value = rootB;
		}else{
			disjoint[rootB].value = rootA;
			
			if(disjoint[rootA].height == disjoint[rootB].height){
				disjoint[rootA].height++;
			}
		}
		trees--;
		return true;
	}
}
class Pair{
	int value;
	int height;
	
	public Pair(int v, int h){
		value = v; 
		height = h;
	}
}