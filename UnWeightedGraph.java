package hackpack;

import java.util.PriorityQueue;

public class UnWeightedGraph {
	static boolean[][] adjacency;
	static boolean[] visited;
	/**
	 * Depth first search using adjacency array
	 * @param start
	 */
	void dfs(int start){
		int targetNode = 0;
		visited[start] = true;
		if(start == targetNode){
			// Process node
		}
		for(int i = 0; i < visited.length; i++){
			if(adjacency[start][i] && !visited[i]){
				dfs(i);
			}
		}
	}
	
	//boolean[][] adjacency;
	//boolean[] visited;
	PriorityQueue<Integer> que;
	/**
	 * Breadth first search using adjacency array
	 * @param start
	 */
	void bfs(int start){
		int targetNode = 0;
		
		que.add(start);
		visited[start] = true;
		if(start == targetNode){
			// Process node
		}
		while(!que.isEmpty()){
			int v = que.remove();
			for(int i = 0;i < visited.length;i++){
				if(adjacency[v][i] && !visited[i]){
					visited[i] = true;
					que.add(i);
				}
			}
		}
	}
	
	int[][] adj;
	int[][] A;
	/**
	 * Floyd - All shortest paths
	 * @param start
	 */
	void floyd(){
		A = adj;
		for(int k = 0; k < A[0].length;k++){
			for(int i = 0; i < A[0].length;i++){
				for(int j = 0; j < A[0].length;j++){
					A[i][j] = Math.min(A[i][j], A[i][k] + A[k][j]);
				}
			}
		}
	}
	
	
	char[][] map;
	boolean[][] visit;
	char target;
	int height;
	int length;
	/**
	 * DFS search through a character map.  
	 * '_' is a space that can be moved through
	 * @param h Starting height
	 * @param l Starting Length
	 */
	void characterDFS(int h,int l){
		visit[h][l] = true;
		if(map[h][l] == target){
			// process correct path
		}else{
			if((h-1) >= 0 && !visit[h-1][l] && (map[h-1][l] == '_' || map[h-1][l] == target)){
				//System.out.println("Up");
				characterDFS(h-1,l);
			}
			if((h+1) < height && !visit[h+1][l] && (map[h+1][l] == '_' || map[h+1][l] == target)){
				//System.out.println("Down");
				characterDFS(h+1,l);
			}
			if((l-1) >= 0 && !visit[h][l-1] && (map[h][l-1] == '_' || map[h][l-1] == target)){
				//System.out.println("Left");
				characterDFS(h,l-1);
			}
			if((l+1) < length && !visit[h][l+1] && (map[h][l+1] == '_' ||map[h][l+1] == target)){
				//System.out.println("Right");
				characterDFS(h,l+1);
			}
		}
	}
	
	// int[][] adj;
	int[] colored;
	int colorsAllowed;
	
	boolean canColor(int vert){
		// If vert is outside graph, all are colored
		if(vert == colored.length){
			return true;
		}
		
		// Loop through each color
		for(int c = 1; c <= colorsAllowed; c++){
			int i = 0;
			boolean safe = true;
			while(safe && i < colored.length){
				if(adj[vert][i] == 1 && c == colored[i]){
					safe = false;
				}
				i++;
			}
			if(safe){
				colored[vert] = c;
			}
			
			if(canColor(vert + 1)){
				return true;
			}
			
			// If that color didn't work, remove it
			colored[vert] = 0;
		}
		
		// if no colors can be assigned, the graph can't be colored
		return false;
	}
	
	
	void maxMatching(){
		
	}
	
}
