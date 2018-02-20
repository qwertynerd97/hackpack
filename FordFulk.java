package hackpack;

import java.util.LinkedList;

public class FordFulk{
    public Node source;
	public Node sink;
	public Node nodes[];
    public LinkedList<State> queue;
    
    public FordFulk(int numNodes){
    	source = new Node("source");
    	sink = new Node("sink");
    	queue = new LinkedList<State>();
    	nodes = new Node[numNodes+2];
    	nodes[0] = source;
    	nodes[numNodes+1] = sink;
    }
    public void link(int first, int second, int capacity){
    	nodes[first].link(nodes[second], capacity);
    }
    
    public int fordFulkerson(){
    	int totalCap = 0;
    	
    	while(true){
    		for(Node g : nodes){
    			g.visited = false;
    		}
    		
    		queue.clear();
    		queue.add(new State(source, null, null));
    		
    		State finalstate = null;
    		
    		while(queue.size()>0){
    			State s = queue.removeFirst();
    		    
    		    if(s.node == sink){
    		    	finalstate = s;
    		    	break;
    		    }
    		    
    		    for(Edge e:s.node.edges){
		    		if(e.cap>0 && !e.dest.visited){
		    			e.dest.visited = true;
		    			queue.add(new State(e.dest, e, s ));
		    		}
		    	}
    		}
    		
    		if(finalstate==null){ // never reached sink
    			break;
    		}
    		
    		int capUsed = Integer.MAX_VALUE;
    		for(State s = finalstate;s.edge != null;s = s.prevstate){
    			if(s.edge.cap < capUsed){
    				capUsed = s.edge.cap;
    			}
    		}
    		
    		// Total capacity is increased by the capacity used on this route
    		totalCap += capUsed;
    		
    		// Modify the edges - remove the capacity used from each
    		// edge in the route, and put it on that edge's dual
    		for(State s = finalstate;s.edge != null;s = s.prevstate){
    			s.edge.cap -= capUsed;
    			s.edge.dual.cap += capUsed;
    		}
    	}
    	return totalCap;
    }
    
    
    
	class Node{
		public LinkedList<Edge> edges;
		public boolean visited;
		public String name;
		
		public Node(String n){
			name = n;
			edges = new LinkedList<Edge>();
			visited = false;
		}
		
		public void link(Node b, int capacity ){
	    	Edge a2b = new Edge(b);	
	        Edge b2a = new Edge(this);
	        
	        this.edges.add(a2b);
	        b.edges.add(b2a);
	        
	        a2b.dual = b2a;
	        b2a.dual = a2b;
	        
	        a2b.cap = capacity;
	        b2a.cap = 0;
	    }
		
		public String toString(){
			return name;
		}
	}
	
	class Edge{
		public int cap;
		public Node dest;
		public Edge dual;
		
		public Edge(Node d){
			cap = 0;
			dest = d;
			dual = null;
		}
	}
	
	class State{
		public Node node;
		public Edge edge;
		public State prevstate;
		public State(Node n, Edge e, State p){
			node = n;
			edge = e;
			prevstate = p;
		}
	}
}
