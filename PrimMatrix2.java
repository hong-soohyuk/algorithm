package week10;

import java.util.Arrays;
import java.util.LinkedList;

public class PrimMatrix2 {
	public static class GraphInnMatrix<T>{
		int matrix[][];
		int value[];
		LinkedList<T> candidateVertices;
		boolean done[];
		T cities[];
		Object[] selectedVertices;
		int totalWeight;
		
		public GraphInnMatrix(T[] vertex, int numofvertices) {
			matrix = new int[numofvertices][numofvertices];
			candidateVertices = new LinkedList<T>();
			value = new int[numofvertices];
			done = new boolean[numofvertices];
			selectedVertices = new Object [numofvertices];
			totalWeight = 0;
			cities = Arrays.copyOf(vertex, numofvertices);
			init(numofvertices);
		}
		private void init(int numofvertices) {
			for (int i = 0; i < numofvertices; i++) {
				value[i] = 999;
				candidateVertices.add(cities[i]);
				done[i] = false;
				for (int j = 0; j <numofvertices; j++) matrix [i][j] = Integer.MAX_VALUE;
			}
			
		}
		public void insertEdge(T s, T d, int weight) {
        	int destination = indexOf(d);
    		int source = indexOf(s);
    		if (destination < 0 || source < 0)
    			{return ;}
    		matrix[destination][source] = matrix[source][destination] = weight;
        }

		public int indexOf(T d) {
			for (int i = 0; i < cities.length; i++) if (cities[i].equals(d)) return i;
			
			return -1;
		}
		public void prim(T r) {
			int index = indexOf(r);
			value[index] = 0;
			while (candidateVertices.size() != 0) {
				T u = extractMin(candidateVertices);
				if (u == null) {System.out.println("Min vertex not found."); break;}
				
				int iu = indexOf(u);
				done[iu] = true;
				candidateVertices.remove(u);
				//after we put a vertex, we need to add an edge too.
				int edgeWeight = findEdge((T) selectedVertices[iu], u);
				if (edgeWeight != -1) {
					System.out.println(selectedVertices[iu]+"~"+edgeWeight+"~"+cities[iu]);
					totalWeight+=edgeWeight;
				}
				//
				for (int i = 0; i < matrix.length; i++) {
					if (matrix[iu][i] != Integer.MAX_VALUE) {
						T temp = cities[i];//index i is adjacent to iu.
						int w = matrix[iu][i]; //its weight.
						
						if (candidateVertices.contains(temp) && w < value[i]) {
							value[i] = w;
							selectedVertices[indexOf(temp)] = u;
						}
					}
				}
			}
		}
		
		private int findEdge(T u, T v) {
			if (u == null) return -1;
			return matrix[indexOf(u)][indexOf(v)];
		}
		public T extractMin(LinkedList<T> candidateCities) {
			int min = 999;
			T returnVertex = null;
			for (T u : candidateCities) {
				if (value[indexOf(u)] < min) 
				{min = value[indexOf(u)];
				returnVertex = cities[indexOf(u)];}
			}
			return returnVertex;
		}
		public void primState() {
			System.out.println("\nCandidate Set : " + candidateVertices);
			System.out.print("visited? : ");for(boolean qq : done) System.out.print(qq+" ");
			System.out.println("\ntotal Weight : "+totalWeight);
		}
	}

    public static void main(String[] args) {
    	String [] vertex = {"seoul", "daejeon", "daegu", "busan",
				"gwangju", "incheon", "ulsan", "jeju"};
        
        GraphInnMatrix<String> myG = new GraphInnMatrix<>(vertex, vertex.length);
        
        
        myG.insertEdge(vertex[0], vertex[3], 13);
		myG.insertEdge(vertex[0], vertex[7], 4);
		myG.insertEdge(vertex[3], vertex[1], 2);
		myG.insertEdge(vertex[3], vertex[7], 6);
		myG.insertEdge(vertex[1], vertex[4], 14);
		myG.insertEdge(vertex[1], vertex[5], 7);
		myG.insertEdge(vertex[5], vertex[2], 3); 
		myG.insertEdge(vertex[5], vertex[6], 9);
		myG.insertEdge(vertex[5], vertex[3], 1); 
		myG.insertEdge(vertex[5], vertex[7], 11);
		myG.insertEdge(vertex[6], vertex[2], 23);
		myG.insertEdge(vertex[6], vertex[0], 8);
		myG.insertEdge(vertex[7], vertex[0], 10);
		
		myG.prim(vertex[3]);
		myG.primState();
		
    }

	

	
}
