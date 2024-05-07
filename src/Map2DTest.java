import ADTs.ArrayQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Map2DTest {
    private Map2D map;
    private PlaceNode place1, place2, place3, place4, place5;

    @BeforeEach
    public void setUp() {
        map = new Map2D();

        // Add the places to the map
        map.add("A1", "Restaurant A1", 10, 20, new String[]{"Restaurant", "WiFi"});
        map.add("A2", "Cafe A2", 15, 25, new String[]{"Cafe", "Parking"});
        map.add("A3", "Library A3", 20, 30, new String[]{"Library", "WiFi"});
        map.add("A4", "Market A4", 25, 35, new String[]{"Market", "Delivery"});
        map.add("A5", "Hospital A5", 30, 40, new String[]{"Hospital", "Parking"});
    }

    @Test
    public void testAddPlace() {
        // Test adding a new place
        assertTrue(map.add("A6", "Restaurant A6", 35, 45, new String[]{"Restaurant", "Delivery"}), "New place should be added successfully");

        // Ensure that the newly added place is correctly placed in the KD-tree
        PlaceNode newPlaceNode = map.searchPlace(new Point(35, 45));
        assertNotNull(newPlaceNode, "Newly added place should exist in the map");
        assertEquals("Restaurant A6", newPlaceNode.getPlace().getName(), "The name of the newly added place should match");
    }

    @Test
    public void testEditPlaceName() {
        // Edit the name of an existing place
        Place editedPlace = map.edit(10, 20, "Updated Restaurant A1");
        assertNotNull(editedPlace, "Edited place should exist");
        assertEquals("Updated Restaurant A1", editedPlace.getName(), "Name should be updated");
    }

    @Test
    public void testEditPlaceServices() {
        // Edit the services of an existing place
        Place editedPlace = map.edit(15, 25, new String[]{"Cafe", "WiFi"});
        assertNotNull(editedPlace, "Edited place should exist");
        assertArrayEquals(new String[]{"Cafe", "WiFi"}, editedPlace.getServices(), "Services should be updated");
    }

    @Test
    public void testEditPlaceNameAndServices() {
        // Edit the name and services of an existing place
        Place editedPlace = map.edit(20, 30, "Updated Library A3", new String[]{"Library", "Cafe"});
        assertNotNull(editedPlace, "Edited place should exist");
        assertEquals("Updated Library A3", editedPlace.getName(), "Name should be updated");
        assertArrayEquals(new String[]{"Library", "Cafe"}, editedPlace.getServices(), "Services should be updated");
    }

    @Test
    public void testSearchAvailablePlaces() {
        // Search for places offering "Parking" within a specified region
        ArrayQueue<Place> results = map.searchAvailable(new Point(20, 30), 30, 30, "Parking", 3);
        assertEquals(2, results.size(), "Two places should be found with 'Parking'");
        assertEquals("Cafe A2", results.peekFront().getName(), "First found place should be 'Cafe A2'");
        results.deQueue();
        assertEquals("Hospital A5", results.peekFront().getName(), "Second found place should be 'Hospital A5'");
    }

    @Test
    public void testRemovePlace() {
        // Test removing an existing place
        assertTrue(map.remove(10, 20), "Place should be removed at coordinates (10, 20)");
        assertNull(map.searchPlace(new Point(10, 20)), "Place should no longer exist at coordinates (10, 20)");

        // Test removing a non-existing place
        assertFalse(map.remove(100, 200), "Attempting to remove a non-existing place should return false");
    }

    @Test
    public void testFindMinimum() {
        // Test finding the minimum node along the x-axis (0 dimension)
        PlaceNode minNode = map.getRoot();
        PlaceNode minXNode = minNode != null ? map.findMin(minNode, 0, 0) : null;
        assertNotNull(minXNode, "Minimum node along the x-axis should be found");
        assertEquals("Restaurant A1", minXNode.getPlace().getName(), "The minimum x-axis place should be 'Restaurant A1'");

        // Test finding the minimum node along the y-axis (1 dimension)
        PlaceNode minYNode = minNode != null ? map.findMin(minNode, 1, 0) : null;
        assertNotNull(minYNode, "Minimum node along the y-axis should be found");
        assertEquals("Restaurant A1", minYNode.getPlace().getName(), "The minimum y-axis place should be 'Restaurant A1'");
    }
}
