public class Place implements Comparable<Place> {
    private String id;
    private String name;
    private final Point point;
    private String[] services;

    public Place(String id, String name, Point position, String[] services) {
        this.id = id;
        this.name = name;
        this.point = position;
        this.services = services;
    }

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

    @Override
    public String toString() {
        String res = "";
        res += "ID: " + id + ", Name: " + name + ", Point: (" + point.getX() + "," + point.getY() + "), Services: ";
        for (String service : services) {
            res += service + ", ";
        }
        return res;
    }

    @Override
    public int compareTo(Place otherPlace) {
        if (point.compareTo(otherPlace.point) > 0) {
            return 0;
        }
        return -1;
    }
}

class PlaceNode {
    private Place place;
    private PlaceNode left, right, parent;

    public PlaceNode(Place place, PlaceNode parent) {
        this.place = place;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

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

    public PlaceNode getParent() {
        return parent;
    }

    public void setParent(PlaceNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return place.toString();
    }
}
