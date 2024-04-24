import java.util.Objects;

/**
 * Represents a road between two towns in a graph.
 *
 * @param <V> the type of the towns
 * @author Liam Ghershony
 */

public class Road<V> implements Comparable<Road<V>> {
    
    private V source;
    private V destination;
    private int weight;
    private String name;
    
    
    /**
     * Constructs a new road with the given source, destination, weight, and name.
     * 
     * @param source the source town of the road
     * @param destination the destination town of the road
     * @param weight the weight (distance) of the road
     * @param name the name of the road
     */
    
    public Road(V source, V destination, int weight, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }

    /**
     * Compares this road with another road based on their weights.
     * 
     * @param other the road to compare with
     * @return a negative integer, zero, or a positive integer as this road is less than, equal to, or greater than the specified road
     */
    
    @Override
    public int compareTo(Road<V> other) {
        int comp = Integer.compare(this.weight, other.weight);
        if (comp == 0) {
            return this.name.compareTo(other.name);
        }
        else {
            return comp;
        }
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj the reference object with which to compare
     * @return true if this road is the same as the obj argument; false otherwise
     */
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road<?> other = (Road<?>) obj;
        // Check for bidirectional equality
        boolean sameDirection = source.equals(other.source) && destination.equals(other.destination);
        boolean oppositeDirection = source.equals(other.destination) && destination.equals(other.source);
        return (sameDirection || oppositeDirection) && weight == other.weight && Objects.equals(name, other.name);
    }

    /**
     * Returns a hash code value for the road.
     * 
     * @return a hash code value for this road
     */
    
    @Override
    public int hashCode() {
        // Use symmetric hashing to handle bidirectional equality
        return Objects.hash(Math.min(source.hashCode(), destination.hashCode()), 
                            Math.max(source.hashCode(), destination.hashCode()), 
                            weight, name);
    }
    
    /**
     * Gets the source town of the road.
     * 
     * @return the source town of the road
     */
    public V getSource() {
        return source;
    }

    /**
     * Gets the destination town of the road.
     * 
     * @return the destination town of the road
     */
    public V getDestination() {
        return destination;
    }

    /**
     * Gets the weight (distance) of the road.
     * 
     * @return the weight of the road
     */
    
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the name of the road.
     * 
     * @return the name of the road
     */
    
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the road.
     * 
     * @return a string representation of the road
     */
    
    @Override
    public String toString() {
        return String.format("%s to %s via %s (%d)", source, destination, name, weight);
    }
}
