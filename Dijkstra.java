package week11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Dijkstra {
	
	public static class GraphInArray<T>{
		LinkedList<T> candidateVertices;
		boolean done[];
		T cities[];
		Object[] prev;
		
		HashSet<EdgeUnit> selectedEdge;
		ArrayList<VertexUnit> adjacentArray;
		
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
		public GraphInArray(int numofvertices) {
			adjacentArray = new ArrayList<VertexUnit>();
			selectedEdge = new HashSet<EdgeUnit>();
			
			candidateVertices = new LinkedList<T>();
			done = new boolean[numofvertices];
			prev = new Object [numofvertices];
		}
		
		public void insertVertex(T u) {
			int index = indexOf(u);
			if (index>=0)
				return;
			adjacentArray.add(new VertexUnit(u));
			candidateVertices.add(u);
			
			return;
		}
		public void insertEdge(T s, T d, int weight) {
			if (weight < 0) {
				System.out.println("Can't have any negative weighted edge in this algorithm.");return;
			}
        	int destination = indexOf(d);
    		int source = indexOf(s);
    		if (destination < 0 || source < 0)
    			{return ;}
    		
    		adjacentArray.get(source).adjList.add(new EdgeUnit(s, d, weight));
    		//they have a direction
        }

		public int indexOf(T d) {
			for (int i=0; i<adjacentArray.size(); i++) 
				if (adjacentArray.get(i).data.equals(d)) return i;
			return -1;
		}
		public void dijkstra(T r) {
			int index = indexOf(r);
			adjacentArray.get(index).value = 0;
			while (candidateVertices.size() != 0) {
				T u = extractMin(candidateVertices);
				if (u == null) {System.out.println("Min vertex not found."); break;}
				
				int iu = indexOf(u);
				done[iu] = true;
				candidateVertices.remove(u);
				//after we put a vertex, we need to add an edge too.
				EdgeUnit tempEdge = findEdge((T) prev[iu], u);
				
				if (tempEdge != null) {
					System.out.println();
					System.out.println("@@ "+tempEdge+"added.");
					selectedEdge.add(tempEdge);
				}
				
				for (int i=0; i < adjacentArray.get(iu).adjList.size();i++) {//extractMin's edges
					
					T temp = adjacentArray.get(iu).adjList.get(i).destVertex;//edge unit
					int wei = adjacentArray.get(iu).adjList.get(i).weight;//edge unit's weight
					
					if (candidateVertices.contains(temp) 
							&& adjacentArray.get(iu).value + wei < adjacentArray.get(indexOf(temp)).value) {
						
						adjacentArray.get(indexOf(temp)).value = wei + adjacentArray.get(iu).value;
						prev[indexOf(temp)] = u;
						
						System.out.println("<<< " + u + "-> " +temp + "is relaxed with "+ adjacentArray.get(indexOf(temp)).value);
					}
				}
			}
		}
		
		private EdgeUnit findEdge(T u, T v) {
			if (u==null)
				return null;
			EdgeUnit retVal = null;
			for (int i=0; i<adjacentArray.get(indexOf(u)).adjList.size(); i++) {
				if (adjacentArray.get(indexOf(u)).adjList.get(i).destVertex.equals(v)) {
					return adjacentArray.get(indexOf(u)).adjList.get(i);
				}
			}
			return retVal;
		}
		public T extractMin(LinkedList<T> candidateCities) {
			int min = Integer.MAX_VALUE;
			T returnVertex = null;
			for (T u : candidateCities) {
				if (adjacentArray.get(indexOf(u)).value < min) 
				{min = adjacentArray.get(indexOf(u)).value;
				returnVertex = adjacentArray.get(indexOf(u)).data;}
			}
			return returnVertex;
		}
		
		public void showGraph() {
			System.out.println("\n$$Final Relaxation$$\nVertices' values : ");
			for (int i=0; i<adjacentArray.size(); i++) {
				System.out.print(adjacentArray.get(i).value+", "); 
			}
		}
		public void init() {
			for (int j = 0; j < adjacentArray.size(); j++) {
				adjacentArray.get(j).value = Integer.MAX_VALUE;
				done[j] = false;
			}
		}
	}

    public static void main(String[] args) {
    	String [] vertex = {"seoul", "daejeon", "daegu", "busan",
				"gwangju", "incheon", "ulsan", "jeju"};
        
    	GraphInArray<String> myG = new GraphInArray<>(vertex.length);
        
        for(String city : vertex) {myG.insertVertex(city);}
        
        myG.insertEdge(vertex[0], vertex[1], 8);
		myG.insertEdge(vertex[0], vertex[2], 9);
		myG.insertEdge(vertex[0], vertex[3], 11);
		
		myG.insertEdge(vertex[1], vertex[4], 10);
		
		myG.insertEdge(vertex[2], vertex[1], 6);
		myG.insertEdge(vertex[2], vertex[4], 1);
		myG.insertEdge(vertex[2], vertex[3], 3);
		
		myG.insertEdge(vertex[3], vertex[5], 8);
		myG.insertEdge(vertex[3], vertex[6], 8);
		
		myG.insertEdge(vertex[4], vertex[7], 2); 

		myG.insertEdge(vertex[5], vertex[7], 5);
		myG.insertEdge(vertex[5], vertex[2], 12);
		
		myG.insertEdge(vertex[6], vertex[5], 7);

		myG.insertEdge(vertex[7], vertex[6], 4);
		
		myG.init();
		
		myG.dijkstra(vertex[0]);
		myG.showGraph();
    }
}
