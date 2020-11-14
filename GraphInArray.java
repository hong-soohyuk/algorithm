package week10;

import java.util.ArrayList;

public class GraphInArray<T> {
	public class EdgeUnit {
		T sourceVertex;
		T destVertex;
		int weight;
		public EdgeUnit(T du, T dv, int w) {
			sourceVertex =du;
			destVertex=dv;
			weight = w;
		}
		public String toString() {
			return "<"+sourceVertex+"("+weight+")"+destVertex+">";
		}
		
		public int compareTo(EdgeUnit other) {
			if (this.weight>other.weight)
				return 1;
			else if (this.weight==other.weight)
				return 0;
			else
				return -1;
		}
	}
	
	public class VertexUnit {
		T data;
		int value;
		ArrayList<EdgeUnit> adjList; 
		
		public VertexUnit(T d) {
			data = d;
			value =0;
			adjList = new ArrayList<>();
		}
		
		public String toString() {
			String retStr = "";
			retStr = retStr + data;
			for (int i=0;i<adjList.size();i++)
				retStr = retStr + " :-("+ adjList.get(i).weight+")-> " + adjList.get(i).destVertex;
			return retStr;
		}
	}
	
	ArrayList<VertexUnit> adjacentArray;
	
	public void createGraph() {
		adjacentArray= new ArrayList<>();
	}
	
	public boolean insertVertex(T u) {
		int index = indexOf(u);
		if (index>=0)
			return false;
		adjacentArray.add(new VertexUnit(u));
		return true;
	}
	
	public int indexOf(T u) {
		for (int i=0; i<adjacentArray.size(); i++) {
			if (adjacentArray.get(i).data.equals(u)) 
				return i;
		}
		return -1;
	}

	public boolean insertEdge(T u, T v, int weight) {
		int indexU = indexOf(u);
		int indexV = indexOf(v);
		if (indexU<0 || indexV<0)
			return false; 
		// undirected graph
		adjacentArray.get(indexU).adjList.add(new EdgeUnit(u, v,weight));
		adjacentArray.get(indexU).value++;
		adjacentArray.get(indexV).adjList.add(new EdgeUnit(v, u,weight));
		adjacentArray.get(indexV).value++;
		return true;
	}
	
	public void showGraph() {
		for (int i=0; i<adjacentArray.size(); i++) {
			System.out.println(adjacentArray.get(i)); 
		}
	}
	
	public static void main(String[] args) { 
		String [] vertex = {"seoul", "daejeon", "daegu", "busan",
				"kwangju", "incheon", "ulsan", "jeju"};
		GraphInArray<String> myG = new GraphInArray<>();

		myG.createGraph();

		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]);

		myG.insertEdge(vertex[0], vertex[3], 3);
		myG.insertEdge(vertex[0], vertex[7], 4);
		myG.insertEdge(vertex[3], vertex[1], 2);
		myG.insertEdge(vertex[3], vertex[7], 6);
		myG.insertEdge(vertex[1], vertex[4], 4);
		myG.insertEdge(vertex[1], vertex[5], 7);
		myG.insertEdge(vertex[5], vertex[2], 3);
		myG.insertEdge(vertex[5], vertex[6], 9);
		myG.insertEdge(vertex[5], vertex[3], 1); 
		myG.insertEdge(vertex[5], vertex[7], 11);
		myG.insertEdge(vertex[6], vertex[2], 3);
		myG.insertEdge(vertex[6], vertex[0], 8);
		myG.insertEdge(vertex[7], vertex[0], 10);

		System.out.println("*** Graph created ***");
		myG.showGraph();
	}

}
