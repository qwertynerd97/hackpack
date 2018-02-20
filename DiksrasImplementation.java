package hackpack;
// NOTE: Java.util.PriorityQueue is a binary heap implementation of a 
// priority queue

// I used Keith Schwarz's Implementation of Fibonnacci Heap from
// http://www.keithschwarz.com/interesting/code/?dir=fibonacci-heap
import java.util.*;

public class DiksrasImplementation {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		int counter = 1;
		
		while(cases-->0){
			int nodes = scan.nextInt();
			int[][] adj = new int[nodes][nodes];
			System.out.println("Graph " + counter + ":");
			
			for(int i = 0; i < nodes; i++){
				for(int j = 0; j < nodes; j++){
					adj[i][j] = scan.nextInt();
					System.out.print(adj[i][j] + " ");
					if(adj[i][j] == 0){
						adj[i][j] = Integer.MAX_VALUE;
					}
				}
				System.out.println();
			}
			
			System.out.println();
			System.out.println("Running Binary Heap Dijkstra's:");
			
			long start = System.nanoTime();
			dijkstraBinHeap(adj,nodes);
			long end = System.nanoTime();
			System.out.println("Time Elapsed: " + (end-start));
			
			System.out.println();
			System.out.println("Running Fibonnacci Heap Dijkstra's:");
			
			start = System.nanoTime();
			dijkstraFibHeap(adj,nodes);
			end = System.nanoTime();
			System.out.println("Time Elapsed: " + (end-start));
			System.out.println();
			
			counter++;
		}
	}
	
	public static void dijkstraBinHeap(int[][] adj, int n){
		int nodes = n;
		int[] dist = new int[nodes];
		HashSet<Integer> finalized = new HashSet<Integer>();
		FibonacciHeap<Nd> fh = new FibonacciHeap<Nd>();
		
		Nd evalNode;
		for(int i = 1; i < nodes; i++){
			dist[i] = Integer.MAX_VALUE;
		}
		
		fh.enqueue(new Nd(0,0), 0);
		dist[0] = 0;
		
		while(!fh.isEmpty()){
			evalNode = fh.dequeueMin().getValue();
			finalized.add(evalNode.num);
			
			int edgeDistance = -1;
	        int newDistance = -1;
	 
	        for (int destinationNode = 0; destinationNode < nodes; destinationNode++){
	            if (!finalized.contains(destinationNode)){
	                if (adj[evalNode.num][destinationNode] != Integer.MAX_VALUE){
	                    edgeDistance = adj[evalNode.num][destinationNode];
	                    newDistance = dist[evalNode.num] + edgeDistance;
	                    if (newDistance < dist[destinationNode]){
	                        dist[destinationNode] = newDistance;
	                    }
	                    fh.enqueue(new Nd(destinationNode,dist[destinationNode]),dist[destinationNode]);
	                }   
	            }
	        }
		}
		
		for(int i = 0; i < nodes; i++){
			if(dist[i] == Integer.MAX_VALUE){
				System.out.println("0 to " + i + ": No Path");
			}else{
				System.out.println("0 to " + i + ": " + dist[i]);
			}
		}
	}
	
	public static void dijkstraFibHeap(int[][] adj, int n){
		int nodes = n;
		int[] dist = new int[nodes+1];
		HashSet<Integer> finalized = new HashSet<Integer>();
		PriorityQueue<Nd> pq = new PriorityQueue<Nd>(nodes);
		
		Nd evalNode;
		for(int i = 1; i < nodes; i++){
			dist[i] = Integer.MAX_VALUE;
		}
		
		pq.add(new Nd(0, 0));
		dist[0] = 0;
		
		while(!pq.isEmpty()){
			evalNode = pq.remove();
			finalized.add(evalNode.num);
			
			int edgeDistance = -1;
	        int newDistance = -1;
	 
	        for (int destinationNode = 0; destinationNode < nodes; destinationNode++){
	            if (!finalized.contains(destinationNode)){
	                if (adj[evalNode.num][destinationNode] != Integer.MAX_VALUE){
	                    edgeDistance = adj[evalNode.num][destinationNode];
	                    newDistance = dist[evalNode.num] + edgeDistance;
	                    if (newDistance < dist[destinationNode]){
	                        dist[destinationNode] = newDistance;
	                    }
	                    pq.add(new Nd(destinationNode,dist[destinationNode]));
	                }   
	            }
	        }
		}
		for(int i = 0; i < nodes; i++){
			if(dist[i] == Integer.MAX_VALUE){
				System.out.println("0 to " + i + ": No Path");
			}else{
				System.out.println("0 to " + i + ": " + dist[i]);
			}
		}
	}
}
class Nd implements Comparable<Nd>{
	public int num;
    public int cost;
 
    public Nd(int node, int cost)
    {
        this.num = node;
        this.cost = cost;
    }
 
    @Override
    public int compareTo(Nd node)
    {
        if (cost < node.cost)
            return -1;
        if (cost > node.cost)
            return 1;
        return 0;
    }

}