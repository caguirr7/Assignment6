/**
 * @author Christian Aguirre
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Integer.*;

public class TownGraphManager implements TownGraphManagerInterface{

    private Graph graph;

    public TownGraphManager() {
        graph = new Graph();
    }

    public Graph getGraph() {
        return graph;
    }



    /**
     * Adds a road with 2 towns and a road name
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param roadName name of road
     * @return true if the road was added successfully
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        getGraph().addVertex(source);
        getGraph().addVertex(destination);
        Road result = getGraph().addEdge(source, destination, weight, roadName);
        return result != null;
    }

    /**
     * Returns the name of the road that both towns are connected through
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return name of road if town 1 and town2 are in the same road, returns null if not
     */
    @Override
    public String getRoad(String town1, String town2) {
        Road result = getGraph().getEdge(new Town(town1), new Town(town2));
        if(result == null)
            return null;
        return result.getName();
    }

    /**
     * Adds a town to the graph
     * @param v the town's name (lastname, firstname)
     * @return true if the town was successfully added, false if not
     */
    @Override
    public boolean addTown(String v) {
        return getGraph().addVertex(new Town(v));
    }

    /**
     * Gets a town with a given name
     * @param name the town's name
     * @return the Town specified by the name, or null if town does not exist
     */
    @Override
    public Town getTown(String name) {
        Set<Town> vertices = getGraph().vertexSet();
        Iterator<Town> iterator = vertices.iterator();
        Town target = new Town(name);
        while(iterator.hasNext()) {
            Town current = iterator.next();
            if(current.equals(target))
                return current;
        }
        return null;
    }

    /**
     * Determines if a town is already in the graph
     * @param v the town's name
     * @return true if the town is in the graph, false if not
     */
    @Override
    public boolean containsTown(String v) {
        return getGraph().containsVertex(new Town(v));
    }

    /**
     * Determines if a road is in the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return true if the road is in the graph, false if not
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        return getGraph().containsEdge(source, destination);
    }

    /**
     * Creates an arraylist of all road titles in sorted order by road name
     * @return an arraylist of all road titles in sorted order by road name
     */
    @Override
    public ArrayList<String> allRoads() {
        Set<Road> roads = getGraph().edgeSet();
        ArrayList<String> result = new ArrayList<>();
        for(Road r: roads)
            result.add(r.getName());
        Collections.sort(result);
        return result;
    }

    /**
     * Deletes a road from the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param road the road name
     * @return true if the road was successfully deleted, false if not
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        Road target = getGraph().getEdge(source, destination);
        if(target == null)
            return false;
        getGraph().removeEdge(source, destination, target.getWeight(), road);
        return true;
    }

    /**
     * Deletes a town from the graph
     * @param v name of town (lastname, firstname)
     * @return true if the town was successfully deleted, false if not
     */
    @Override
    public boolean deleteTown(String v) {
        return getGraph().removeVertex(new Town(v));
    }

    /**
     * Creates an arraylist of all towns in alphabetical order (last name, first name)
     * @return an arraylist of all towns in alphabetical order (last name, first name)
     */
    @Override
    public ArrayList<String> allTowns() {
        Set<Town> towns = getGraph().vertexSet();
        ArrayList<String> result = new ArrayList<>();
        for(Town t: towns)
            result.add(t.getName());
        Collections.sort(result);
        return result;

    }

    /**
     * Returns the shortest path from town 1 to town 2
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return an Arraylist of roads connecting the two towns together, null if the
     * towns have no path to connect them.
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        if(getGraph().containsVertex(source) &&
                getGraph().containsVertex(destination) &&
                 !getGraph().edgesOf(source).isEmpty() &&
                !getGraph().edgesOf(destination).isEmpty()) {
            ArrayList<String> result = getGraph().shortestPath(source, destination);
            if(result == null)
                return new ArrayList<>();
            return result;
        }
        return new ArrayList<>();
    }



    /**
     * Populates the graph with the roads provided
     * in a file. The file extension must be included in
     * the file name or this method will not work.
     * @param fileName - the file name
     * @throws FileNotFoundException - If file not found
     */
    public void populateTownGraph(File fileName) throws FileNotFoundException {
        try (Scanner inFile = new Scanner(new File(String.valueOf(fileName)))) {

            String source;
            while (inFile.hasNext()) {
                String currentLine = inFile.nextLine();


                source = currentLine.substring(currentLine.indexOf(';') + 1);
                source = source.substring(0, source.indexOf(';'));

                String destination = currentLine.substring(currentLine.indexOf(';') + 1);
                destination = destination.substring(destination.indexOf(';') + 1);


                Town sourceTown = new Town(source);
                Town destinationTown = new Town(destination);
                if (!getGraph().containsVertex(sourceTown))
                    getGraph().addVertex(sourceTown);
                if (!getGraph().containsVertex(destinationTown))
                    getGraph().addVertex(destinationTown);
                String name = currentLine.substring(0, currentLine.indexOf(','));
                int weight = parseInt(currentLine.substring(currentLine.indexOf(',') + 1,
                        currentLine.indexOf(';')));
                if (!getGraph().containsEdge(sourceTown, destinationTown))
                    getGraph().addEdge(sourceTown, destinationTown, weight, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}