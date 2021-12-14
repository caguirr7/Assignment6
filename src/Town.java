/**
 * @author Christian Aguirre
 */


public class Town implements Comparable<Town>{
    private String name;
    private Town templateTown;


    /**
     * Constructor. Requires town's name.
     * @param name Town's name
     */
    public Town(String name) {
        this.name = name;

    }

    /**
     * Copy constructor
     * @param templateTown An instance of Town
     */
    public Town(Town templateTown) {

        this.templateTown = new Town(templateTown);

    }


    /**
     * Returns the town's name
     * @return Town's name
     */
    public String getName() {
        return name;
    }

    /**
     * Compare method
     * @param o - the Town to be compared with this instance
     * @return 0 if names are equal
     * a positive or negative number if the names are not equal.
     */
    @Override
    public int compareTo(Town o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * toString method that returns the name
     * @return the town name
     */
    @Override
    public String toString() {

        return name;
    }

    /**
     * Hashcode for the name of the town
     * @return The hashcode for the name of the town
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Check if two town objects are equal
     * @param o - the Town object to be compared with another town object
     * @return true if the town names are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Town town2)
            return this.getName().equals(town2.getName());
        return false;
    }

    /**
     * Sets the name of this town to the desired String
     * @param name Name of town
     */
    public void setName(String name) {
        this.name = name;
    }



}