import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
/**
 * Represents a town in a graph.
 * @author Liam Ghershony
 */
public class Town implements Comparable<Town> {
	
	private String name;
	private List<Town> adjacents;
	
	
	/**
	 * Constructs a new town with the given name.
	 * 
	 * @param name the name of the town
	 */
	public Town(String name) {
		this.name = name;
		this.adjacents = new ArrayList<>();
	}
	
	/**
	 * Compares this town with another town based on their names.
	 * 
	 * @param o the town to compare with
	 * @return a negative integer, zero, or a positive integer as this town is less than, equal to, or greater than the specified town
	 */
	
	@Override
	public int compareTo(Town o) {
		return this.name.compareTo(o.name);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj the reference object with which to compare
	 * @return true if this town is the same as the obj argument; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Town other = (Town) obj;
	    return Objects.equals(this.name, other.name);
	}

	/**
	 * Returns a hash code value for the town.
	 * 
	 * @return a hash code value for this town
	 */
	public int hashCode() {
	    return (name == null) ? 0 : name.hashCode();
	}
	/**
	 * Adds an adjacent town to this town.
	 * 
	 * @param town the town to add as adjacent
	 */
	public void addAdjacentTown(Town town) {
		adjacents.add(town);
	}
	
	/**
	 * Gets the list of adjacent towns.
	 * 
	 * @return the list of adjacent towns
	 */
	public List<Town> getAdjacentTowns(){
		return new ArrayList<>(adjacents);
	}
	
	/**
	 * Gets the name of the town.
	 * 
	 * @return the name of the town
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * Returns the name of the town.
	 * 
	 * @return the name of the town
	 */
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
