/**
 * @author Christian Aguirre
 */

public class Road implements Comparable<Road>{
    private final Town source;
    private final Town destination;
    private final String name;
    private final int weight;

    /**
     *
     * @param source One Town on the road
     * @param destination Another Town on the Road
     * @param degrees The weight of the edge, i.e, distance from one town to the other
     * @param name Name of the road
     */
    public Road(Town source, Town destination, int degrees, String name) {
        this.source = source;
        this.destination = destination;
        weight = degrees;
        this.name = name;
    }

    /**
     * Copy constructor which sets the weight to 1 by default
     * @param source One town on the road
     * @param destination Another town on the road
     * @param name Name of the road
     */
    public Road(Town source, Town destination, String name) {
        this.source = source;
        this.destination = destination;
        this.name = name;
        weight = 1;
    }

    /**
     * Returns true only if the edge contains the given town
     * @param town - A vertex of the graph
     * @return true if the road contains the given vertex, false otherwise
     */
    public boolean contains(Town town) {
        return (source.equals(town) || destination.equals(town));
    }

    /**
     * Returns a String representation of the road
     * @return a String representation of the road
     */
    @Override
    public String toString() {
        return getName() + " " +
                getSource().getName() + " " +
                getDestination().getName();

    }

    /**
     * Returns the road name
     * @return the name of the Road
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the second town on the road.
     * @return the second town on the road
     */
    public Town getDestination() {
        return destination;
    }

    /**
     * Returns the Town at the first end of this Road
     * @return the Town at the first end of this Road
     */
    public Town getSource() {
        return source;
    }
    /**
     * Compares the roads based on weight, and returns the following:
     * @return 0 if the road names are the same, a positive or negative
     * number if the road names are not the same.
     */
    @Override
    public int compareTo(Road o) {
        return this.getWeight() - o.getWeight();
    }

    /**
     * Returns the distance of the road
     * @return the distance of the road
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns true if each of the ends of the road r is the same
     * as the ends of this road. Remember that a road that goes from
     * point A to point B is the same as a road that goes from point B
     * to point A.
     * @param o Road object to compare it to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Road road)
            return (road.contains(this.getSource()) && road.contains(this.getDestination()));
        return false;
    }

    /**
     * Returns the hash code of the road, which is based on its two towns added together
     * @return the hash code of the road, which is based on its two towns added together
     */
    @Override
    public int hashCode() {
        return getSource().hashCode() + getDestination().hashCode();
    }


}