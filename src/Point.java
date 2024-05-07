public class Point implements Comparable<Point> {
    final private int x, y;

    // Constructor to initialize the x and y coordinates of the point
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter methods to retrieve the x and y coordinates of the point
    public int getX() { return x; }
    public int getY() { return y; }

    // Method to retrieve the coordinate by index (0 for x, 1 for y)
    public int getByIndex(int index) {
        // Check if the index is valid and return the corresponding coordinate
        if (index == 0) return x;
        if (index == 1) return y;
        // If the index is invalid, throw an IllegalArgumentException
        throw new IllegalArgumentException("Invalid index for Point dimensions.");
    }

    // Implementation of the compareTo method from the Comparable interface
    @Override
    public int compareTo(Point point) {
        // Check if the provided point is null, if so, throw a NullPointerException
        if (point == null) {
            throw new NullPointerException("Attempted to compare to null");
        }
        // Compare the x and y coordinates of this point with the provided point
        if (x == point.getX() && y == point.getY()) {
            // If the coordinates are the same, return 0
            return 0;
        }
        // If the coordinates are different, return -1
        return -1;
    }
}
