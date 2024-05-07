import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class PlaceManager {
    private static final Random rand = new Random();
    private static final String[] types = {"Restaurant", "Hospital", "Library", "Cafe", "Market"};
    private static final String[] additionalServices = {"WiFi", "Parking", "Drive-through", "Delivery", "Pet-friendly"};

    // Method to generate a large number of places and save them to a file
    public static void generateAllPlaces(Map2D map, String filename) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(filename))))) {
            // Generate places for different regions (A, B, C, D) with increasing ranges
            // Each region is represented by a character ('A', 'B', 'C', 'D')
            // The ranges for each region are defined as (startId, endId, maxResults)
            // The number of places generated for each region increases exponentially
            generatePlaces(map, out, 'A', 1, 10000, 12500);
            generatePlaces(map, out, 'B', 10001, 100000, 12500);
            generatePlaces(map, out, 'C', 100001, 1000000, 12500);
            generatePlaces(map, out, 'D', 1000001, 10000000, 12500);
        } catch (IOException e) {
            // If an IOException occurs while writing to the file, print an error message
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to generate places within a specified range and add them to the map and a file
    private static void generatePlaces(Map2D map, PrintWriter out, char prefix, int min, int max, int count) {
        // Loop to generate the specified number of places
        for (int i = 0; i < count; i++) {
            // Generate random x and y coordinates within the specified range
            int x = rand.nextInt(max - min + 1) + min;
            int y = rand.nextInt(max - min + 1) + min;

            // Select a random type of service for the place
            String type = types[rand.nextInt(types.length)];
            // Create an array of services for the place, including the type and an additional random service
            String[] services = {type, additionalServices[rand.nextInt(additionalServices.length)]};
            String id = prefix + Integer.toString(i + 1);
            String name = "Place " + id;

            // Use the addNew method to place new Place into the KD-Tree structure
            map.add(id, name, x, y, services);

            // Output the new Place data to a file
            out.printf("%s,%s,%d,%d,%s\n", id, name, x, y, String.join(";", services));
        }
    }

    public static void loadPlacesFromFile(Map2D map, String filename, int maxPoints) {
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            int pointsLoaded = 0; // Counter for the number of points loaded
            while (scanner.hasNextLine() && pointsLoaded < maxPoints) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5) {  // Ensure the line has enough parts to parse
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int x = Integer.parseInt(parts[2].trim());
                    int y = Integer.parseInt(parts[3].trim());
                    String[] services = parts[4].split(";"); // Split services by semicolon

                    // Add the new Place to the Map2D using the updated addNew method that handles KD-tree logic
                    map.add(id, name, x, y, services);
                    pointsLoaded++; // Increment the counter after each place is added
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: The file " + filename + " was not found.");
        } catch (NumberFormatException e) {
            System.err.println("Error: Number format exception while parsing coordinates.");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
