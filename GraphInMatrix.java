package week10;

import java.util.Arrays;
import java.util.HashMap;


public class GraphInMatrix<T> {
	
	HashMap<T, Integer> citiesVertices;
	int[][] adjacentMatrix;
	int vertexValue [];
	
	void createGraph(int vertexLength) {
		adjacentMatrix = new int[vertexLength][vertexLength];
		citiesVertices = new HashMap<T, Integer>();
		
		vertexValue = new int[vertexLength];
		
		Arrays.fill(vertexValue, 999);
		for (int[] row: adjacentMatrix) Arrays.fill(row, 999);
	}
	
	boolean insertVertex(T v, int i) {
		if (!citiesVertices.containsKey(v)) {
			citiesVertices.put(v, i);
			return true;
		}else 
			return false;
	}
	
	boolean insertEdge(T u, T v, int weight) {
		int indexU = indexOf(u);
		int indexV = indexOf(v);
		if (indexU < 0 || indexV < 0) 
			{return false;}
		//undirected graph
		adjacentMatrix[indexU][indexV] = adjacentMatrix[indexV][indexU] = weight;
		return true;
	}
	
	protected int indexOf(T u) {
		if (citiesVertices.get(u)!=null) 
		{return citiesVertices.get(u);}
		
		else return -1;
	}
		
	public T getKey(HashMap<T, Integer> hash, int i) {
		for(T o: hash.keySet()) {
			if(hash.get(o).equals(i))
				return o;
			}
		return null;
	}
	
	void showGraph() {
		for (int i = 0; i < adjacentMatrix.length; i++) {
			System.out.print(getKey(citiesVertices, i) + " ");
			for (int j = 0; j < adjacentMatrix.length; j++) {
				if (adjacentMatrix[i][j] != 999) 
					System.out.print("- ("+ adjacentMatrix[i][j] +") - "+ getKey(citiesVertices, j));
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		String [] vertex = {"seoul", "daejeon", "daegu", "busan",
				"gwangju", "incheon", "ulsan", "jeju"};
		GraphInMatrix<String> myG = new GraphInMatrix<>();
		myG.createGraph(vertex.length);
		
		for (int i = 0; i < vertex.length; i++){
			System.out.print(vertex[i] + ", ");
			myG.insertVertex(vertex[i], i);
		}
		System.out.println();
		
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
				
		System.out.println("**** Graph Created. ****");
		myG.showGraph();
		
	}
}
