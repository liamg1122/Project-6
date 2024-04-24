import java.util.*;

/**
 * Implements graph with towns as vertices and roads as edges.
 * @author Liam Ghershony
 */
public class Graph implements GraphInterface<Town, Road> {
    private Map<Town, List<Road>> adjacencyList;
    private Set<Town> vertices;
    private Set<Road> edges;

    /**
     * Constructs a new graph.
     */
    public Graph() {
        adjacencyList = new HashMap<>();
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    /**
     * Gets the edge between two towns.
     *
     * @param source the source town
     * @param destination the destination town
     * @return the road between the two towns, or null if no road exists
     */
    
    @Override
    public Road getEdge(Town source, Town destination) {
        List<Road> roads = adjacencyList.get(source);
        if (roads != null) {
            for (Road road : roads) {
                if ((road.getDestination().equals(destination) && road.getSource().equals(source)) ||
                    (road.getSource().equals(destination) && road.getDestination().equals(source))) {
                    return road;
                }
            }
        }
        return null;
    }

    
    /**
     * Adds a new road between two towns with a weight and description.
     *
     * @param source the source town
     * @param destination the destination town
     * @param weight the weight of the road
     * @param description the description of the road
     * @return the newly added road
     */
    
    @Override
    public Road addEdge(Town source, Town destination, int weight, String description) {
        if (source == null || destination == null) {
            throw new NullPointerException("Source or destination cannot be null.");
        }
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("Both vertices must be added before adding an edge.");
        }
        Road newRoad = new Road(source, destination, weight, description);
        adjacencyList.get(source).add(newRoad);
        adjacencyList.get(destination).add(newRoad);
        edges.add(newRoad);
        return newRoad;
    }

    /**
     * Adds a new town to the graph.
     *
     * @param vertex the town to add
     * @return true if the town was added successfully, false otherwise
     */
    
    @Override
    public boolean addVertex(Town vertex) {
        if (vertex == null) {
            throw new NullPointerException("Vertex cannot be null.");
        }
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            adjacencyList.put(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }

    /**
     * Checks if the graph contains a specified town.
     *
     * @param vertex the town to check
     * @return true if the town is in the graph, false otherwise
     */
    
    @Override
    public boolean containsVertex(Town vertex) {
        return vertices.contains(vertex);
    }

    /**
     * Checks if the graph contains an edge between two towns.
     *
     * @param source the source town
     * @param destination the destination town
     * @return true if there is an edge between the towns, false otherwise
     */
    
    @Override
    public boolean containsEdge(Town source, Town destination) {
        return getEdge(source, destination) != null;
    }

    /**
     * Gets the set of edges (roads) in the graph.
     *
     * @return the set of edges
     */
    
    @Override
    public Set<Road> edgeSet() {
        return new HashSet<>(edges);
    }

    /**
     * Gets the set of edges (roads) incident to a specified town.
     *
     * @param vertex the town
     * @return the set of edges incident to the town
     */
    
    @Override
    public Set<Road> edgesOf(Town vertex) {
        List<Road> roads = adjacencyList.get(vertex);
        if (roads == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(roads);
    }

    @Override
    public Road removeEdge(Town source, Town destination, int weight, String description) {
        Road road = getEdge(source, destination);
        if (road != null && road.getWeight() == weight && road.getName().equals(description)) {
            adjacencyList.get(source).remove(road);
            adjacencyList.get(destination).remove(road);
            edges.remove(road);
        }
        return road;
    }
    
    /**
     * Removes an edge between two towns.
     *
     * @param source the source town
     * @param destination the destination town
     * @param weight the weight of the road
     * @param description the description of the road
     * @return the removed road, or null if no road was removed
     */

    @Override
    public boolean removeVertex(Town vertex) {
        if (vertices.remove(vertex)) {
            List<Road> adjacentRoads = new ArrayList<>(adjacencyList.get(vertex));
            for (Road road : adjacentRoads) {
                adjacencyList.get(road.getSource()).remove(road);
                adjacencyList.get(road.getDestination()).remove(road);
                edges.remove(road);
            }
            adjacencyList.remove(vertex);
            return true;
        }
        return false;
    }

    /**
     * Gets the set of vertices (towns) in the graph.
     *
     * @return the set of vertices
     */
    
    @Override
    public Set<Town> vertexSet() {
        return new HashSet<>(vertices);
    }

    
    /**
     * Finds the shortest path between two towns using Dijkstra's algorithm.
     *
     * @param source the source town
     * @param destination the destination town
     * @return the shortest path as a list of road descriptions, or an empty list if no path exists
     */
    
    @Override
    public ArrayList<String> shortestPath(Town source, Town destination) {
        dijkstraShortestPath(source);
        List<String> path = new ArrayList<>();
        Town step = destination;

        if (previous.get(step) == null) {
            return new ArrayList<>(); 
        }

        while (step != null && previous.get(step) != null) {
            Town prev = previous.get(step);
            Road edge = getEdge(prev, step);
            if (edge == null) {
                break; 
            }
            String pathStep = String.format("%s via %s to %s %d mi", prev, edge.getName(), step, edge.getWeight());
            path.add(0, pathStep); 


            step = prev; 
        }

        return new ArrayList<>(path);
    }


    Map<Town, Integer> dist = new HashMap<>();
    Map<Town, Town> previous = new HashMap<>();
    PriorityQueue<Town> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));

    
    /**
     * Runs Dijkstra's algorithm to find the shortest paths from a source town to all other towns.
     *
     * @param source the source town
     */
    
    @Override
    public void dijkstraShortestPath(Town source) {
        dist.clear();
        previous.clear();
        queue.clear();

        for (Town v : vertices) {
            dist.put(v, v.equals(source) ? 0 : Integer.MAX_VALUE);
            previous.put(v, null);
            queue.add(v);
        }

        while (!queue.isEmpty()) {
            Town current = queue.poll();
            if (dist.get(current) == Integer.MAX_VALUE) break;

            for (Road edge : adjacencyList.get(current)) {
                Town adj = edge.getDestination().equals(current) ? (Town) edge.getSource() : (Town) edge.getDestination();
                int altDist = dist.get(current) + edge.getWeight();
                if (altDist < dist.get(adj)) {
                    dist.put(adj, altDist);
                    previous.put(adj, current);
                    queue.remove(adj);
                    queue.add(adj);
                }
            }
        }
    }
}
