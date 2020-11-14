package week11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class topology {
	public static class GraphInList<T>{
		private class GraphNode{
			T data;
			GraphNode link;
			public GraphNode(T input) {
				data = input;
				link = null;
			}
		}
		
		boolean[] visited;
		LinkedList<T> topolo2List;
		ArrayList<T> topoloArray;
		
		ArrayList<GraphNode> adjacentList;
		int incomingEdge[];
		
		public GraphInList(int numofvertices) {
			adjacentList = new ArrayList<GraphNode>();
			incomingEdge = new int[numofvertices];
			visited = new boolean[numofvertices];
			
			Arrays.fill(visited, false);
			Arrays.fill(incomingEdge, 0);
		}
		private boolean insertVertex(T v) {
			int index = indexOf(v);
			
			if (index>=0) return false; //this vertex is already exist.
			
			adjacentList.add(new GraphNode(v));
			return true;
		}
		
		public boolean insertEdge(T u, T v) {
			if (indexOf(u)<0||indexOf(v)<0) 
				return false;
			int source = indexOf(u);
			int destination = indexOf(v);
			GraphNode newNode = new GraphNode(v);
			newNode.link = adjacentList.get(source).link;// insertion at the first position.
			adjacentList.get(source).link = newNode;
			incomingEdge[destination]++;
			return true;
		}

		private int indexOf(T u) {
		for (int i = 0; i < adjacentList.size(); i++) 
			{if (adjacentList.get(i).data.equals(u)) {return i;}}
		
		return -1;
		}
		public void removeEdges(ArrayList<GraphNode> adjacentList, int index) {
			GraphNode tobeDelete = adjacentList.get(index).link;
			while(tobeDelete != null) {
				incomingEdge[indexOf(tobeDelete.data)]--;
				tobeDelete = tobeDelete.link;
			}
			
		}
		public ArrayList<T> topologyAlgo() {
			topoloArray = new ArrayList<T>();
			while(topoloArray.size() != adjacentList.size()) {
				for (int index = 0; index < incomingEdge.length; index++) {
					if (incomingEdge[index] == 0) {
						topoloArray.add(adjacentList.get(index).data);
						removeEdges(adjacentList, index);
					}
				}
			}
			//at this point, the graph is topology sorted.
			return topoloArray;
		}
		public LinkedList<T> topologyAlgo2() {
			topolo2List = new LinkedList<T>();
			for (int i = 0; i < visited.length; i++) 
				if (visited[i] == false) DFS(topolo2List,i);
			
			return topolo2List;
		}
		private void DFS(LinkedList<T> topolo2List, int index) {
			if (visited[index]) return;
			visited[index] = true;
			
			GraphNode temp = adjacentList.get(index).link;
			while (temp != null) {
				if (!visited[indexOf(temp.data)]) 
					DFS(topolo2List, indexOf(temp.data));
				temp = temp.link;
			}
			topolo2List.addFirst(adjacentList.get(index).data);// its link == null, then addFirst itself.
		}
	}

    public static void main(String[] args) {
    	String [] recipe = {"냄비에 물 넣기", "가스레인지", "라면봉지 뜯기", "분말 넣기",
				"면 넣기", "계란 넣기"};//encoding : utf-8
        
    	GraphInList<String> myG = new GraphInList<>(recipe.length);
    	
        for(String x : recipe) 
        	myG.insertVertex(x);
        
        myG.insertEdge(recipe[0], recipe[1]);	//its directed edge
		myG.insertEdge(recipe[1], recipe[3]);
		myG.insertEdge(recipe[1], recipe[4]);
		myG.insertEdge(recipe[1], recipe[5]);
		myG.insertEdge(recipe[2], recipe[3]);
		myG.insertEdge(recipe[2], recipe[4]); 
		myG.insertEdge(recipe[3], recipe[5]);
		myG.insertEdge(recipe[4], recipe[5]); 
		
		System.out.println();
		System.out.println("topology Sort1 : " + "\n" + myG.topologyAlgo());
		System.out.println();
		System.out.println("topology Sort2 : " + "\n" + myG.topologyAlgo2());
    }
}
