package menu;

import graph.AStarPathFinding;
import graph.StationsWithinRectangle;
import lists.DoublyLinkedList;
import model.Station;
import model.StationSearch;
import model.Track;
import sorting.InsertionSort;
import sorting.MergeSort;

import java.util.*;

//This Class should not be tracked for line coverage as this is my menu options
public class Main {
    // Comparator for sorting stations by name
    private static final Comparator<Station> stationNameComparator = Comparator.comparing(Station::getName);
    // Comparator for sorting tracks by length
    private static final Comparator<Track> trackLengthComparator = Comparator.comparingInt(Track::getDistanceTo);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;


        DoublyLinkedList<Station> stations = (DoublyLinkedList<Station>) Station.readFromCSV("resources/stations.csv");
        ArrayList staionList = stations.toArrayList();

        ArrayList<Track> tracks = (ArrayList<Track>) Track.readFromCSV("resources/tracks.csv");
        AStarPathFinding astar = new AStarPathFinding(tracks, staionList);


        do {
            try {
                System.out.println("\n------------------ CDS MENU ------------------");
                System.out.println("1. Search a Station by name- Linear Search");
                System.out.println("2. Search a Station by name- Binary Search");
                System.out.println("3. Sort Connections/Tracks by length- InsertionSort");
                System.out.println("4. Sort Connections/Tracks by length- MergeSort");
                System.out.println("5. Sort Stations by name- InsertionSort");
                System.out.println("6. Sort Stations by name- MergeSort");
                System.out.println("7. Search Shortest Path between two stations");
                System.out.println("8. Determine MCST by Rectangle (all stations that fall within the rectangle)");
                System.out.println("0. Exit");
                System.out.println("\n-----------------------------------------------");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        searchStationLinear(staionList, scanner);
                        break;
                    case 2:
                        searchStationBinary(staionList, scanner);
                        break;
                    case 3:
                        sortTracksInsertionSort(tracks);
                        break;
                    case 4:
                        sortTracksMergeSort(tracks);
                        break;
                    case 5:
                        sortStationsInsertionSort(staionList);
                        break;
                    case 6:
                        sortStationsMergeSort(staionList);
                        break;
                    case 7:
                        findShortestPathBetweenTwoStations(scanner, astar);
                        astar.resetState(); // Resetting the state before finding the path so i can call this menu option multiple times and work
                        break;
                    case 8:
                        stationsWithinRectangleMCST(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please provide a valid menu option!");
            }
            scanner.nextLine();
            choice = -1; // Reseting choice to continue the loop
        } while (choice != 0);
    }

    private static void searchStationLinear(ArrayList<Station> stations, Scanner scanner) {
        System.out.print("Enter station name to search: ");
        String name = scanner.nextLine();
        Station foundStation = StationSearch.linearSearchByName(stations, name);

        if (foundStation != null) {
            System.out.println("Station found: " + foundStation);
        } else {
            System.out.println("Station not found.");
        }
    }

    private static void searchStationBinary(ArrayList<Station> stations, Scanner scanner) {
        StationSearch.sortStationsByName(stations);

        System.out.print("Enter station name to search: ");
        String name = scanner.nextLine();
        Station foundStation = StationSearch.binarySearchByName(stations, name);

        if (foundStation != null) {
            System.out.println("Station found: " + foundStation);
        } else {
            System.out.println("Station not found.");
        }
    }


    private static void sortTracksInsertionSort(ArrayList<Track> tracks) {
        InsertionSort.sort(tracks, trackLengthComparator);
        System.out.println("Tracks sorted by length (InsertionSort): ");
        tracks.forEach(System.out::println);
    }

    private static void sortTracksMergeSort(ArrayList<Track> tracks) {
        DoublyLinkedList<Track> trackLinkedList = new DoublyLinkedList<>();
        tracks.forEach(trackLinkedList::add);
        MergeSort.mergeSort(trackLinkedList, trackLengthComparator);
        System.out.println("Tracks sorted by length (MergeSort): ");
        trackLinkedList.forEach(System.out::println);
    }

    private static void sortStationsInsertionSort(ArrayList<Station> stations) {
        InsertionSort.sort(stations, stationNameComparator);
        System.out.println("Stations sorted by name (InsertionSort): ");
        stations.forEach(System.out::println);
    }

    private static void sortStationsMergeSort(ArrayList<Station> stations) {
        DoublyLinkedList<Station> stationLinkedList = new DoublyLinkedList<>();
        stations.forEach(stationLinkedList::add);
        MergeSort.mergeSort(stationLinkedList, stationNameComparator);
        System.out.println("Stations sorted by name (MergeSort): ");
        stationLinkedList.forEach(System.out::println);
    }

    private static void findShortestPathBetweenTwoStations(Scanner scanner, AStarPathFinding astar) {
        System.out.print("Enter the code of the first station: ");
        String startCode = scanner.nextLine().toUpperCase();
        System.out.print("Enter the code of the second station: ");
        String goalCode = scanner.nextLine().toUpperCase();

        List<Station> path = astar.findPath(startCode, goalCode);
        if (path != null && !path.isEmpty()) {
            System.out.println("The shortest path is:");
            for (Station station : path) {
                System.out.print(station.getName() + " (" + station.getCode().toUpperCase() + ") -> ");
            }
            System.out.println();

            double totalWeight = astar.calculateTotalPathWeight(path);
            System.out.println("Total distance: " + totalWeight);
        } else {
            System.out.println("No path found between the specified stations.");
        }
    }

    private static void stationsWithinRectangleMCST(Scanner scanner) {

        StationsWithinRectangle swr = new StationsWithinRectangle("resources/stations.csv", "resources/tracks.csv");

        // Ask for station codes
        System.out.println("Enter the code for the first station (one corner of the rectangle):");
        String firstStationCode = scanner.next().trim().toUpperCase();
        System.out.println("Enter the code for the second station (opposite corner of the rectangle):");
        String secondStationCode = scanner.next().trim().toUpperCase();
        scanner.nextLine();


        Station firstStation = swr.findStationByCode(firstStationCode);
        Station secondStation = swr.findStationByCode(secondStationCode);


        if (firstStation == null || secondStation == null) {
            System.out.println("One or both of the station codes are invalid.");
            return;
        }


        double lat1 = firstStation.getGeoLat();
        double lon1 = firstStation.getGeoLng();
        double lat2 = secondStation.getGeoLat();
        double lon2 = secondStation.getGeoLng();


        swr.calculateMinimumSpanningTree(lat1, lon1, lat2, lon2);
    }
}
