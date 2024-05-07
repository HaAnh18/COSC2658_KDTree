import ADTs.ArrayQueue;

public class Main {
    public static void main(String[] args) {
        Map2D map = new Map2D();
        PlaceManager.loadPlacesFromFile(map, "point.csv",100000);
        Point center = new Point(715000, 3690000);
        int width = 100000, height = 100000;
        long start = System.currentTimeMillis();
        ArrayQueue<Place> listOfPlaces = map.searchAvailable(center, width, height, "Restaurant", 50);
        long end = System.currentTimeMillis();
        System.out.println(listOfPlaces.size());
        long dur = end - start;
        System.out.println("Total Kd-tree execution time: " + dur + " milliseconds");
    }
}


