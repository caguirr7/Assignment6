/**
 * @author Christian Aguirre
 */

import java.util.*;

public class Graph implements GraphInterface<Town, Road>{
    private final HashSet<Town> vertices;
    private final HashSet<Road> edges;
    private final Map<String, Town> adjacent;


    public Graph(){
       vertices = new HashSet<>();
       edges = new HashSet<>();
       adjacent = new HashMap<>();
    }

    /**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise, returns
     * null. If any of the specified vertices is null
     * returns null
     *
     * In undirected graphs, the returned edge may have its source and target
     * vertices in the opposite order.
     *
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     *
     * @return an edge connecting source vertex to target vertex.
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        if (sourceVertex != null && destinationVertex != null) {
            for(Road current : getEdges())
                if (current.contains(sourceVertex) &&
                        current.contains(destinationVertex))
                    return current;
        }
        return null;

    }

    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge.
     *
     * The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.
     *
     *
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description for edge
     *
     * @return The newly created edge if added to the graph, otherwise null.
     *
     * @throws IllegalArgumentException if source or target vertices are not
     * found in the graph.
     * @throws NullPointerException if any of the specified vertices is null.
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description)
            throws IllegalArgumentException, NullPointerException{

        if(sourceVertex == null || destinationVertex == null)
            throw new NullPointerException();

        if(!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
            throw new IllegalArgumentException();

        Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);
        boolean result = getEdges().add(newRoad);


            if (result)
                return newRoad;

            return null;

    }

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, v, to this graph if
     * this graph contains no vertex u such that
     * u.equals(v). If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns false. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     *
     * @param v vertex to be added to this graph.
     *
     * @return true if this graph did not already contain the specified
     * vertex.
     *
     * @throws NullPointerException if the specified vertex is null.
     */
    @Override
    public boolean addVertex(Town v) throws NullPointerException{


        if (v != null) {
            if (getVertices().contains(v))
                return false;
            getVertices().add(v);
            return true;
        } else
            throw new NullPointerException();
    }

    /**
     * Returns true if and only if this graph contains an edge going
     * from the source vertex to the target vertex. In undirected graphs the
     * same result is obtained when source and target are inverted. If any of
     * the specified vertices does not exist in the graph, or if is
     * null, returns false.
     *
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     *
     * @return true if this graph contains the specified edge.
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {

        for(Road r : getEdges())
            if ((sourceVertex.equals(r.getSource()) || sourceVertex.equals(r.getDestination()))
                    && (destinationVertex.equals(r.getDestination()) || destinationVertex.equals(r.getSource())))
                return true;
        return false;
    }

    /**
     * Returns true if this graph contains the specified vertex. More
     * formally, returns true if and only if this graph contains a
     * vertex u such that u.equals(v). If the
     * specified vertex is null returns false.
     *
     * @param v vertex whose presence in this graph is to be tested.
     *
     * @return true if this graph contains the specified vertex.
     */
    @Override
    public boolean containsVertex(Town v) {

        for (Town current : getVertices())
            if (current.equals(v))
                return true;
        return false;
    }

    /**
     * Returns a set of the edges contained in this graph. The set is backed by
     * the graph, so changes to the graph are reflected in the set. If the graph
     * is modified while an iteration over the set is in progress, the results
     * of the iteration are undefined.
     *
     *
     * @return a set of the edges contained in this graph.
     */
    @Override
    public Set<Road> edgeSet() {
        return getEdges();
    }

    /**
     * Returns a set of all edges touching the specified vertex (also
     * referred to as adjacent vertices). If no edges are
     * touching the specified vertex returns an empty set.
     *
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return a set of all edges touching the specified vertex.
     *
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException if vertex is null.
     */
    @Override
    public Set<Road> edgesOf(Town vertex) throws NullPointerException, IllegalArgumentException{

        if(vertex == null)
            throw new NullPointerException();
        if(!containsVertex(vertex))
            throw new IllegalArgumentException();

        Iterator<Road> iterator = getEdges().iterator();

        HashSet<Road> result = new HashSet<>();
        while(iterator.hasNext()) {
            Road current = iterator.next();
            if(current.contains(vertex))
                result.add(current);
        }
        return result;
    }

    /**
     * Removes an edge going from source vertex to target vertex, if such
     * vertices and such edge exist in this graph.
     *
     * If weight >- 1 it must be checked
     * If description != null, it must be checked
     *
     * Returns the edge if removed
     * or null otherwise.
     *
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description of the edge
     *
     * @return The removed edge, or null if no edge removed.
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road removedRoad = new Road(sourceVertex, destinationVertex, weight, description);

        for (Road current : getEdges())
            if ((weight > -1 && weight == current.getWeight() || weight == -1)
                    && current.equals(removedRoad)
                    && ((description == null) || description.equals(current.getName()))) {

                getEdges().remove(current);

                return current;
            }
        return null;
    }

    /**
     * Removes the specified vertex from this graph including all its touching
     * edges if present. More formally, if the graph contains a vertex
     * u such that u.equals(v), the call removes all edges
     * that touch u and then removes u itself. If no
     * such u is found, the call leaves the graph unchanged.
     * Returns true if the graph contained the specified vertex. (The
     * graph will not contain the specified vertex once the call returns).
     *
     * If the specified vertex is null returns false.
     *
     * @param v vertex to be removed from this graph, if present.
     *
     * @return true if the graph contained the specified vertex;
     * false otherwise.
     */
    @Override
    public boolean removeVertex(Town v) {
        HashSet<Road> edgesToBeRemoved = new HashSet<>();
        if (getVertices().contains(v)) {
            getVertices().remove(v);

            for (Road current : getEdges())
                if (current.contains(v))
                    edgesToBeRemoved.add(current);
            for (Road road : edgesToBeRemoved)
                getEdges().remove(road);
            return true;
        }
        return false;
    }

    /**
     * Returns a set of the vertices contained in this graph. The set is backed
     * by the graph, so changes to the graph are reflected in the set. If the
     * graph is modified while an iteration over the set is in progress, the
     * results of the iteration are undefined.
     *
     *
     * @return a set view of the vertices contained in this graph.
     */
    @Override
    public Set<Town> vertexSet() {
        return getVertices();
    }

    /**
     * Find the shortest path from the sourceVertex to the destinationVertex
     * call the dijkstraShortestPath with the sourceVertex
     * @param sourceVertex starting vertex
     * @param destinationVertex ending vertex
     * @return An arraylist of Strings that describe the path from sourceVertex
     * to destinationVertex
     * They will be in the format: startVertex "via" Edge "to" endVertex weight
     * As an example: if finding path from Vertex_1 to Vertex_10, the ArrayList<String>
     * would be in the following format(this is a hypothetical solution):
     * Vertex_1 via Edge_2 to Vertex_3 4 (first string in ArrayList)
     * Vertex_3 via Edge_5 to Vertex_8 2 (second string in ArrayList)
     * Vertex_8 via Edge_9 to Vertex_10 2 (third string in ArrayList)
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        ArrayList<String> path = new ArrayList<>();
        dijkstraShortestPath(sourceVertex);
        Town newTown = destinationVertex;
        while (!newTown.equals(sourceVertex)) {
            if (getAdjacent().containsKey(newTown.getName())) {
                Town parentTown = getAdjacent().get(newTown.getName());
                Road road = getEdge(parentTown, newTown);
                path.add(0, parentTown.getName() + " via " + road.getName() + " to " + newTown.getName() + " " + road.getWeight() + " mi");
                newTown = parentTown;
            } else
                path.clear();
        }
        return path;
    }

    /**
     * Dijkstra's Shortest Path Method. Internal structures are built which hold
     * the ability to retrieve the path, the shortest distance from the sourceVertex
     * to all the other vertices in the graph, etc.
     *
     * @param sourceVertex the vertex to find the shortest path from
     *
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        ArrayList<Town> unvisitedTown = new ArrayList<>();
        HashMap<String, Integer> weight = new HashMap<>();
        getAdjacent().clear();
        /*
        Add unvisited town
        Add distance (or weight) between each vertex
        Set adjacent towns from source to null
         */
        for (Town town : getVertices()) {
            unvisitedTown.add(town);
            weight.put(town.getName(), Integer.MAX_VALUE);
            getAdjacent().put(town.getName(), null);
        }
        //Start source vertex at 0
        weight.put(sourceVertex.getName(), 0);

        /*
        While the unvisited town is not empty
        For each adjacent town from source and then after check which edge is lower
         */
        while (!unvisitedTown.isEmpty()) {
            int shortestRoad = 0;

            for (int i = 1; i < unvisitedTown.size(); i++) {
                Town unvisitedVertex = unvisitedTown.get(i);

                //If the weight(edge) of the unvisited town(vertex) is less than the other distance identify for each
                if (weight.get(unvisitedVertex.getName()) < weight.get(unvisitedTown.get(shortestRoad).getName()))
                    shortestRoad = i;
            }

            Town closestTown = unvisitedTown.remove(shortestRoad);

                /*
                Get the smallest edge between each vertex from the source then
                on the next iteration on after find the lowest edge until each vertex has been processed
                 */
                for (Road road : edgesOf(closestTown)) {
                Town neighbor = road.getDestination();

                if (neighbor.equals(closestTown))
                    neighbor = road.getSource();
                if ((weight.get(closestTown.getName()) + road.getWeight()) < weight.get(neighbor.getName())) {
                    weight.put(neighbor.getName(), weight.get(closestTown.getName()) + road.getWeight());
                    getAdjacent().put(neighbor.getName(), closestTown);
                }
            }
        }
    }

    public HashSet<Road> getEdges() {
        return edges;
    }

    public HashSet<Town> getVertices() {
        return vertices;
    }

    public Map<String, Town> getAdjacent() {
        return adjacent;
    }


}