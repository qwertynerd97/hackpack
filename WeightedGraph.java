package hackpack;

import java.util.Arrays;

public class WeightedGraph {
	static int NO_SOLUTION = Integer.MAX_VALUE;
	
	/**
	 *  Returns the length of the shortest path that hits each ride exactly
	 *  once (a Hamiltonian Path) - Traveling salesmen solution. 
	 *  If none exists, NO_SOLUTION is returned
	 * @param perm
	 * @param used
	 * @param k
	 * @param rideloc
	 * @param blocked
	 * @return
	 */
    public static double solve(int[] perm, boolean[] used, int k, Point[] rideloc, int[][] blocked) {

        // Finished case!
        if (k == perm.length){
        	return calcdist(perm, rideloc);
        }

        double best = NO_SOLUTION;
        for (int i=0; i<perm.length; i++) {

            // See if ride i is a valid place to go next.
            if (!used[i]) {
                if (k == 0 || blocked[perm[k-1]][i] == 0) {

                    perm[k] = i;
                    used[i] = true;
                    best = Math.min(best, solve(perm, used, k+1, rideloc, blocked));
                    used[i] = false;
                }
            }
        } // end for

        return best;
    }
    public static double calcdist(int[] perm, Point[] loc) {
        Point origin = new Point(0,0);
        double dist = 0;

        // Add in each subsequent edge in the path.
        for (int i=0; i<perm.length; i++) {
            dist +=  origin.dist(loc[perm[i]]);
            origin = loc[perm[i]];
        }
        return dist;
    }
    
    /**
     * Finds the minspan tree for a non-directed graph
     */
    public static int minSpan(Edge[] list, int nodes){
		DisjointSet dj = new DisjointSet(nodes);
		int edgeCount = 0;
		int weight = 0;
		int i = 0;
		
		Arrays.sort(list);
		while(edgeCount < nodes-1 && i < list.length){
			boolean joined = dj.union(list[i].node1,list[i].node2);
			if(joined){
				edgeCount++;
				weight += list[i].cost;
			}
			i++;
		}
		
		if(edgeCount < nodes-1){
			return -1;
		}else{
			return weight;
		}
	}
}

class Point {
	public String display;
    public double x;
    public double y;

    public Point(int _x, int _y) {
        x = _x;
        y = _y;
    }
    public Point(String d,double _x, double _y) {
    	display = d;
        x = _x;
        y = _y;
    }

    public double dist(Point other) {
        return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y));
    }
    public String toString(){
    	return display + " x:" + x + " y:" + y;
    }
}

class DisjointSet{
	public int[] parents;
	public int[] heights;
	
	public DisjointSet(int nodes){
		parents = new int[nodes];
		heights = new int[nodes];
		for(int i = 0;i < nodes;i++){
			parents[i] = i;
		}
	}
	public int find(int node){
		if(parents[node] == node){
			return node;
		}else{
			return find(parents[node]);
		}
	}
	public boolean union(int node1, int node2){
		int parent1 = find(node1);
		int parent2 = find(node2);
		if(parent1 == parent2){
			return false;
		}
		
		if(heights[parent1] < heights[parent2]){
			parents[parent1] = parent2;
		}else if(heights[parent1] > heights[parent2]){
			parents[parent2] = parent1;
		}else{
			parents[parent1] = parent2;
			heights[parent2]++;
		}
		return true;
	}
}

class Edge implements Comparable<Edge>{
	public int node1;
	public int node2;
	public int cost;
	
	public Edge(int start, int end, int weight){
		node1 = start;
		node2 = end;
		cost = weight;
	}
	@Override
	public int compareTo(Edge o) {
		return cost - o.cost;
	}
	
}
