public class Place implements Comparable<Place> {
    private String id;
    private String name;
    private final Point point;
    private String[] services;

    // Constructor to initialize a Place object with an ID, name, position (Point), and services
    public Place(String id, String name, Point position, String[] services) {
        this.id = id;
        this.name = name;
        this.point = position;
        this.services = services;
    }

    // Getter and setter methods for name and services
    // Getters for point and ID are not provided as they are immutable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getServices() {
        return services;
    }

    public Point getPoint() {
        return point;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    // Override toString method to represent a Place object as a string
    @Override
    public String toString() {
        String res = "";
        res += "ID: " + id + ", Name: " + name + ", Point: (" + point.getX() + "," + point.getY() + "), Services: ";
        for (String service : services) {
            res += service + ", ";
        }
        return res;
    }

    // CompareTo method to define the natural ordering of Place objects based on their Point positions
    @Override
    public int compareTo(Place otherPlace) {
        // Compare Place objects based on their Point positions
        if (point.compareTo(otherPlace.point) == 0) {
            return 0;
        }
        return -1;
    }
}

// Class representing a node in the kd tree for Place objects
class PlaceNode {
    private Place place;
    private PlaceNode left, right, parent;

    // Constructor to initialize a PlaceNode with a Place object and a parent node
    public PlaceNode(Place place, PlaceNode parent) {
        this.place = place;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

    // Getter and setter methods for place, left child, right child, and parent node
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PlaceNode getLeft() {
        return left;
    }

    public void setLeft(PlaceNode left) {
        this.left = left;
    }

    public PlaceNode getRight() {
        return right;
    }

    public void setRight(PlaceNode right) {
        this.right = right;
    }

    // Override toString method to represent a PlaceNode object as a string
    @Override
    public String toString() {
        return place.toString();
    }
}
