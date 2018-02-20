package hackpack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class EdmondKarp {
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		int counter = 1;
		
		while(cases-->0){
			int nodes = scan.nextInt();
			int[][] adj = new int[nodes][nodes];
			System.out.println("Graph " + counter + ":");
			int source = scan.nextInt();
			int sink = scan.nextInt();
			ArrayList<Integer>[] neighbors = new ArrayList[nodes];
			
			for(int i = 0; i < nodes; i++){
				neighbors[i] = new ArrayList<Integer>();
				for(int j = 0; j < nodes; j++){
					adj[i][j] = scan.nextInt();
					System.out.print(adj[i][j] + " ");
					if(adj[i][j] == 0){
						adj[i][j] = Integer.MAX_VALUE;
					}else{
						neighbors[i].add(j);
					}
				}
				System.out.println();
			}
			
			
			System.out.println();
			System.out.println("Running EdmundsKarp:");
			
			int flow = edmondsKarp(neighbors,adj,source,sink);
			System.out.println("Flow is " + flow);
			System.out.println();
			
			counter++;
		}
	}
	
    public static int edmondsKarp(ArrayList<Integer>[] neighbors, int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] flow = new int[n][n];
        
        while (true) {
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            parent[source] = source;
            
            int[] max = new int[n]; // Capacity of path to node
            max[source] = Integer.MAX_VALUE;
            
            // BFS queue
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.offer(source);
            
            // Run BFS
            bfs: while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : neighbors[u]) {
                    // Check if there is available capacity, and if v has not been seen before
                    if (capacity[u][v] - flow[u][v] > 0 && parent[v] == -1) {
                        parent[v] = u;
                        max[v] = Math.min(max[u], capacity[u][v] - flow[u][v]);
                        if (v != sink)
                            queue.offer(v);
                        else {
                            // Backtrack search, and write flow
                            while (parent[v] != v) {
                                u = parent[v];
                                flow[u][v] += max[sink];
                                flow[v][u] -= max[sink];
                                v = u;
                            }
                            break bfs;
                        }
                    }
                }
            }
            if (parent[sink] == -1) { // We did not find a path to t
                int sum = 0;
                for (int x : flow[source])
                    sum += x;
                return sum;
            }
        }
    }
}

/*
10
8
0 5
0 0 0 6 5 0 0 0
7 0 2 9 0 0 0 0
6 6 0 0 4 8 0 0
0 7 0 0 0 9 0 0
0 0 0 0 0 0 0 0
0 0 0 8 0 0 0 0
0 0 7 0 0 0 0 8
0 0 0 0 0 0 0 0
4
0 3
0 2 4 0
0 0 3 1
0 0 0 5
0 0 0 0
8
0 7
0 3 7 6 6 0 0 0
0 0 8 0 0 1 6 0
0 0 0 0 0 2 0 0
0 0 0 0 0 0 0 5
0 0 3 0 0 0 0 8
0 0 0 0 0 0 0 3
0 0 0 0 5 8 0 0
0 0 0 0 0 0 0 0
6
0 5
0 10 0 0 0 0
0 0 10 0 0 0 
0 0 0 0 7 0
0 5 0 0 0 0
0 0 0 5 0 15
0 0 0 0 0 0
8
1 7
0 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0
0 0 0 0 1 3 7 0
0 0 0 0 0 0 0 5
0 0 5 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 4 0 4 9 0 0
0 0 0 0 0 5 0 0
10
0 9
0 0 0 0 0 343 0 1435 464 0
0 0 0 0 0 879 954 811 0 524
0 0 0 0 1364 1054 0 0 0 0
0 0 0 0 0 0 433 0 0 1053
0 0 1364 0 0 1106 0 0 0 766
343 879 1054 0 1106 0 0 0 0 0
0 954 0 433 0 0 0 837 0 0
1435 811 0 0 0 0 837 0 0 0
464 0 0 0 0 0 0 0 0 0
0 524 0 1053 766 0 0 0 0 0
8
0 7
0 0 0 1 0 0 0 0
4 0 0 0 0 1 9 0
0 0 0 0 0 0 0 0
0 0 0 0 0 9 0 0 
0 0 8 0 0 0 6 0
0 0 3 0 0 0 0 9
0 0 0 0 0 6 0 0
0 0 0 0 0 0 0 0
2
0 1
0 18
0 0
8
0 7
0 0 0 0 2 0 0 0
0 0 0 7 0 5 0 0
0 6 0 0 0 0 4 0
0 0 0 0 0 0 0 6
0 0 8 0 0 0 0 8
0 0 0 6 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 4 6 0
6
0 5
0 15 0 4 0 0
0 0 12 0 0 0
0 0 0 5 0 7
0 0 0 0 10 0
0 3 0 0 0 10
0 0 0 0 0 0

*/