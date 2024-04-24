import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manages the graph and graph objects like roads and town.
 *
 * @author Liam Ghershony
 */

public class TownGraphManager implements TownGraphManagerInterface{

	private Graph graph = new Graph();
	
    /**
     * Adds a road between two towns with the given weight and name.
     *
     * @param town1 -- the name of town 1
     * @param town2  -- the name of town 2
     * @param weight -- the weight of the road
     * @param roadName the name of the road
     * @return true if the road was successfully added, false otherwise
     */
	
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town one = new Town(town1);
		Town two = new Town(town2);
		if (graph.containsVertex(two)) graph.addVertex(two);
		if (graph.containsVertex(one)) graph.addVertex(one);
		return graph.addEdge(one, two, weight, roadName) != null;
	}

	

    /**
     * Retrieves the name of the road between two towns.
     *
     * @param town1 the name of town 1
     * @param town2 the name of town 2
     * @return the name of the road between the towns, or null if no road exists
     */
	
	public String getRoad(String town1, String town2) {
		Town one = new Town(town1);
		Town two = new Town(town2);
		Road three = graph.getEdge(one, two);
		if(three!=null) {
			return three.getName();
			}
		else {
			return null;
		}
		
	}

    /**
     * Adds a town to the graph.
     *
     * @param v the name of the town to add
     * @return true if the town was successfully added, false otherwise
     */
	
	
	public boolean addTown(String v) {
		Town town2add = new Town(v);
		return graph.addVertex(town2add);
	}

	
    /**
     * Retrieves a town from the graph.
     *
     * @param name the name of the town to retrieve
     * @return the town object if found, or null if not found
     */
	
	public Town getTown(String name) {
		for(Town town: graph.vertexSet()) {
			if(name.equals(town.getName())) {
				return town;
			}
		}
		return null;
	}

	

    /**
     * Checks if the graph contains a town with the given name.
     *
     * @param v the name of the town to check
     * @return true if the graph contains the town, false otherwise
     */
	
	public boolean containsTown(String v) {
		Town town = new Town(v);
		if(graph.vertexSet().contains(town)) {
			return true;
		}
		return false;
	}


    /**
     * Checks if there is a road connection between two towns.
     *
     * @param town1 the name of town 1
     * @param town2 the name of town 2
     * @return true if there is a road connection, false otherwise
     */
	
	public boolean containsRoadConnection(String town1, String town2) {
		Town one = new Town(town1);
		Town two = new Town(town2);
		return graph.getEdge(one, two) != null;
	}

	
    /**
     * Retrieves a list of all roads in the graph.
     *
     * @return an ArrayList containing the names of all roads
     */
	
	public ArrayList<String> allRoads() {
		Set<Road> allRoads = graph.edgeSet();
		ArrayList<String> roadStrings = new ArrayList<>();
		for(Road road: allRoads) {
			roadStrings.add(road.getName());
		}
		
		Collections.sort(roadStrings);
		return roadStrings;
	}

    /**
     * Deletes a road connection between two towns.
     *
     * @param town1 the name of town 1
     * @param town2 the name of town 2
     * @param road  the name of the road to delete
     * @return true if the road connection was successfully deleted, false otherwise
     */
	
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Town one = new Town(town1);
		Town two = new Town(town2);		
		Road threeR = graph.getEdge(one, two);
		if(threeR != null && threeR.getName() == road) {
			graph.removeEdge(one, two, threeR.getWeight(), road);
		}
		return false;
	}

    /**
     * Deletes a town from the graph.
     *
     * @param v the name of the town to delete
     * @return true if the town was successfully deleted, false otherwise
     */
	
	
	public boolean deleteTown(String v) {
		Town town = new Town(v);
		return graph.removeVertex(town);
	}

	
    /**
     * Retrieves a list of all towns in the graph.
     *
     * @return an ArrayList containing the names of all towns
     */
	
	public ArrayList<String> allTowns() {
		Set<Town> townSet = graph.vertexSet() ;
		ArrayList<String> townArray = new ArrayList<>();
		for(Town town: townSet) {
			townArray.add(town.getName());
		}
		Collections.sort(townArray);
		
		return townArray;
	}


    /**
     * Retrieves the shortest path between two towns.
     *
     * @param town1 the name of the starting town
     * @param town2 the name of the destination town
     * @return an ArrayList containing the names of towns on the shortest path, or an empty list if no path exists
     */
	
	public ArrayList<String> getPath(String town1, String town2) {
		Town one = new Town(town1);
		Town two = new Town(town2);	
		graph.dijkstraShortestPath(one);
		return graph.shortestPath(one, two);
	}

    /**
     * Populates the town graph with data from a file.
     *
     * @param selectedFile the file containing the data
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if an I/O error occurs
     */
	
	public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException {

	    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {

	    	String oneLine;
	    	
				while((oneLine = br.readLine())!= null) {
					
					String[] frags = oneLine.split(";");
					if (frags.length!= 3) continue;
					
					String[] roads = frags[0].split(",");
					if (roads.length != 2) continue;
					
					String nameRoad = roads[0].trim();
					
					int weight;
					try {
		                weight = Integer.parseInt(roads[1].trim());

					} catch(NumberFormatException e) {
		                continue;
					
				}
					
			String town1 = frags[1].trim();
			String town2 = frags[2].trim();
			
			
			if (!containsTown(town1)) {
                addTown(town1);}
			
			if (!containsTown(town2)) {
	             addTown(town2);
	            }
			
	        addRoad(town1, town2, weight, nameRoad);
			
			
			}
	    } 
	    
	    	
	}

}
