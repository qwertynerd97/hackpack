package hackpack;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TopologicalSort {
	public Node[] ordering;
	boolean[] used;
	int nodes;
	Node[] nodeList;
	
	public TopologicalSort(Node[] list){
		nodeList = list;
		nodes = list.length;
		used = new boolean[nodes];
		ordering = new Node[nodes];
	}
	
	// Top sort returns num of possible combinations
	public int sortTop(){
		int currIndex = 0;
		int retVal = 1;
		
		int count = 0;
		ArrayDeque<Node> queue = new ArrayDeque<Node>();
		for(int i = 0; i < nodes; i++){
			if(nodeList[i].inDegree == 0){
				queue.add(nodeList[i]);
				count++;
			}
		}
		
		while(currIndex < nodes){
			if(queue.size() == 0){
				return 0;
			}
			if(queue.size() < 1){
				retVal = 2;
			}
			
			for(int i = 0; i < count; i++){
				Node n = queue.poll();
				ordering[currIndex] = n;
				currIndex++;
				
				for(Line e: n.edgeList){
					e.towards.inDegree--;
					if(e.towards.inDegree == 0){
						queue.add(e.towards);
					}
				}
			}
		}
		return retVal;
	}
}

class Node{
	public int inDegree;
	public ArrayList<Line> edgeList;
	
	public Node(){
		inDegree = 0;
		edgeList = new ArrayList<Line>();
	}
}
class Line{
	public Node towards;
	public Line(Node n){
		towards = n;
	}
}
