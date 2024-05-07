import ADTs.ArrayQueue;

public class Map2D {
    private PlaceNode root;

    // Method to add a new place to the map
    public boolean add(String id, String name, int x, int y, String[] services) {
        // If the root is null, create a new tree with the new place as the root
        if (root == null) {
            root = new PlaceNode(new Place(id, name, new Point(x, y), services), null);
            return true;
        }
        // Traverse the tree to find the appropriate position to insert the new place
        PlaceNode temp = root;
        int depth = 0;
        while (temp != null) {
            int axis = depth % 2; // Determine the axis (x-axis or y-axis)
            int target = axis == 0 ? x : y; // Determine the target coordinate
            int currentPlace = axis == 0 ? temp.getPlace().getPoint().getX() : temp.getPlace().getPoint().getY(); // Get the current place's coordinate along the axis
            // Move left or right depending on the comparison with the target coordinate
            if (target < currentPlace) {
                if (temp.getLeft() == null) {
                    PlaceNode newPlace = new PlaceNode(new Place(id, name, new Point(x, y), services), temp);
                    temp.setLeft(newPlace);
                    return true;
                }
                temp = temp.getLeft();
            } else {
                if (temp.getRight() == null) {
                    PlaceNode newPlace = new PlaceNode(new Place(id, name, new Point(x, y), services), temp);
                    temp.setRight(newPlace);
                    return true;
                }
                temp = temp.getRight();
            }
            depth++;
        }
        return false;
    }

    // Method to search for a place based on its coordinates
    public PlaceNode searchPlace(Point point) {
        PlaceNode temp = root;
        int depth = 0;
        while (temp != null) {
            if (temp.getPlace().getPoint().compareTo(point) == 0) {
                return temp;
            }

            // Determine the axis to search along
            int target = depth % 2 == 0 ? point.getX() : point.getY();
            int currentPlace = depth % 2 == 0 ? temp.getPlace().getPoint().getX() : temp.getPlace().getPoint().getY();

            // Move left or right based on the comparison with the target coordinate
            if (target < currentPlace) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
            depth++;
        }

        return null;
    }

    // Methods to edit the name, services, or both of a place at given coordinates
    public Place edit(int x, int y, String newName, String[] newServices) {
        Point pos = new Point(x, y);            // Create a point with the provided coordinates
        PlaceNode node = searchPlace(pos);      // Find the node that corresponds to this point

        if (node != null) {                     // Check if the node actually exists
            node.getPlace().setName(newName);        // Set new name
            node.getPlace().setServices(newServices); // Set new services
            return node.getPlace();
        }
        return null;
    }

    public Place edit(int x, int y, String newName) {
        Point pos = new Point(x, y);            // Create a point with the provided coordinates
        PlaceNode node = searchPlace(pos);      // Find the node that corresponds to this point

        if (node != null) {                     // Check if the node actually exists
            node.getPlace().setName(newName);        // Set new name
            return node.getPlace();
        }
        return null;
    }

    public Place edit(int x, int y, String[] services) {
        Point pos = new Point(x, y);            // Create a point with the provided coordinates
        PlaceNode node = searchPlace(pos);      // Find the node that corresponds to this point

        if (node != null) {                     // Check if the node actually exists
            node.getPlace().setServices(services);        // Set new name
            return node.getPlace();
        }
        return null;
    }

    // Method to search for available places within a given rectangle and with a specified service type
    public ArrayQueue<Place> searchAvailable(Point center, int width, int height, String type, int maxResults) {
        ArrayQueue<Place> results = new ArrayQueue<>();
        int minX = center.getX() - width/2;
        int maxX = center.getX() + width/2;
        int minY = center.getY() - height/2;
        int maxY = center.getY() + height/2;

        searchInRectangle(root, minX, maxX, minY, maxY, type, results, maxResults,0);
        return results;
    }

    // Helper method to recursively search for places within a rectangle
    private void searchInRectangle(PlaceNode node, int minX, int maxX, int minY, int maxY, String type, ArrayQueue<Place> results, int maxResults, int depth) {
        // Base case: if the current node is null or the maximum number of results has been reached, return
        if (node == null || results.size() >= maxResults) {
            return;
        }

        // Get the coordinates and services of the place stored in the current node
        int x = node.getPlace().getPoint().getX();
        int y = node.getPlace().getPoint().getY();
        String[] services = node.getPlace().getServices();

        // Check if the place falls within the specified rectangle and offers the required service
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            for (String service : services) {
                if (service.equals(type)) {
                    results.enQueue(node.getPlace()); // If the service matches, enqueue the place
                    break;
                }
            }
        }

        // Alternate dimensions for traversal (x-axis and y-axis)
        int cd = depth % 2; // Calculate the current dimension
        if (cd == 0) { // If the current dimension is x-axis
            if (x > minX && node.getLeft() != null) {
                // Recursively search in the left subtree if applicable
                searchInRectangle(node.getLeft(), minX, maxX, minY, maxY, type, results, maxResults, depth + 1);
            }
            if (x < maxX && node.getRight() != null) {
                // Recursively search in the right subtree if applicable
                searchInRectangle(node.getRight(), minX, maxX, minY, maxY, type, results, maxResults, depth + 1);
            }
        } else {  // If the current dimension is y-axis
            if (y > minY && node.getLeft() != null) {
                // Recursively search in the left subtree if applicable
                searchInRectangle(node.getLeft(), minX, maxX, minY, maxY, type, results, maxResults, depth + 1);
            }
            if (y < maxY && node.getRight() != null) {
                // Recursively search in the right subtree if applicable
                searchInRectangle(node.getRight(), minX, maxX, minY, maxY, type, results, maxResults, depth + 1);
            }
        }
    }

    public boolean remove(int x, int y) {
        Point pos = new Point(x, y);
        root = deleteNode(root, pos, 0); // Call deleteNode to attempt to remove the node starting from the root at depth 0.
        return root != null;            // Return true if the root is not null after deletion, indicating the tree is not empty.
    }

    private PlaceNode deleteNode(PlaceNode root, Point point, int depth) {
        if (root == null) return null;  // Base case: if the current node is null, return null (nothing to delete here).

        int cd = depth % 2;             // Calculate the current dimension to compare (0 for x-axis, 1 for y-axis).
        Point rootPoint = root.getPlace().getPoint(); // Get the point stored in the current node.
        if (rootPoint.compareTo(point) == 0) {  // Check if the current node is the one to be deleted.
            if (root.getRight() != null) {   // If the node has a right child, find the minimum in the right subtree.
                PlaceNode min = findMin(root.getRight(), cd, depth + 1); // Find the min node in the right subtree along the current axis.
                root.setPlace(min.getPlace()); // Replace the current node's place with the min node's place.
                root.setRight(deleteNode(root.getRight(), min.getPlace().getPoint(), depth + 1)); // Recursively delete the min node from the right subtree.
            } else if (root.getLeft() != null) { // If there's no right child but a left child exists,
                PlaceNode min = findMin(root.getLeft(), cd, depth + 1); // Find the min node in the left subtree along the current axis.
                root.setPlace(min.getPlace()); // Replace the current node's place with the min node's place.
                root.setRight(deleteNode(root.getLeft(), min.getPlace().getPoint(), depth + 1)); // Move the left subtree to the right and delete the min node.
                root.setLeft(null); // Clear the left pointer.
            } else {
                return null;           // If no children, simply remove this node by returning null.
            }
            return root;               // Return the updated root of the subtree.
        }

        if (point.getByIndex(cd) < rootPoint.getByIndex(cd)) // Compare the point to be deleted with the current node's point along the current axis.
            root.setLeft(deleteNode(root.getLeft(), point, depth + 1)); // If less, recurse into the left subtree.
        else
            root.setRight(deleteNode(root.getRight(), point, depth + 1)); // If greater or equal, recurse into the right subtree.
        return root;                   // Return the root (helps in re-linking parents during recursion).
    }

    private PlaceNode findMin(PlaceNode node, int d, int depth) {
        if (node == null) return null;  // Base case: if node is null, return null.

        int cd = depth % 2;             // Calculate current dimension (x or y).
        if (cd == d) {                  // If the dimension matches the required dimension,
            if (node.getLeft() == null) return node; // If there's no left child, this node is the minimum.
            return findMin(node.getLeft(), d, depth + 1); // Recursively find the minimum in the left subtree.
        }

        PlaceNode left = findMin(node.getLeft(), d, depth + 1); // Find the minimum in the left subtree for the specified dimension.
        PlaceNode right = findMin(node.getRight(), d, depth + 1); // Find the minimum in the right subtree for the specified dimension.
        PlaceNode res = node;          // Assume the current node is the minimum.

        // Determine the true minimum by comparing the found values.
        if (left != null && left.getPlace().getPoint().getByIndex(d) < res.getPlace().getPoint().getByIndex(d)) res = left;
        if (right != null && right.getPlace().getPoint().getByIndex(d) < res.getPlace().getPoint().getByIndex(d)) res = right;

        return res;                    // Return the minimum node.
    }

    // Getter method to retrieve the root of the KD Tree
    public PlaceNode getRoot() {
        return root;
    }
}

