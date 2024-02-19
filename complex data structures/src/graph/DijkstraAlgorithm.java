package graph;

import model.Station;
import model.Track;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraAlgorithm {

    public static List<Station> findShortestPath(WeightedMatrixGraph<Station> graph, Station source, Station target) {
        Map<Station, Double> shortestDistances = new HashMap<>();
        Map<Station, Station> previous = new HashMap<>();

        // Using a custom comparator for the PriorityQueue
        PriorityQueue<Station> unvisited = new PriorityQueue<>(Comparator.comparingDouble(shortestDistances::get));

        for (Station station : graph.getVertices()) {
            shortestDistances.put(station, Double.MAX_VALUE);
        }

        shortestDistances.put(source, 0.0);
        unvisited.add(source);

        // Lists tp record the processing order and priority queue content
        List<String> visitedOrder = new ArrayList<>();
        List<String> priorityQueueContent = new ArrayList<>();

        while (!unvisited.isEmpty()) {
            Station current = unvisited.poll();


            visitedOrder.add(current.getName() + "(" + shortestDistances.get(current) + "," + (previous.get(current) != null ? previous.get(current).getName() : "-") + ")");

            StringBuilder priorityQueueValues = new StringBuilder();
            for (Station s : unvisited) {
                priorityQueueValues.append(s.getName()).append("(").append(shortestDistances.get(s)).append(",").append(previous.get(s) != null ? previous.get(s).getName() : "-").append(") ").append("/");
            }
            if (priorityQueueValues.length() > 0) {
                priorityQueueValues.setLength(priorityQueueValues.length() - 1);
            }
            priorityQueueContent.add(priorityQueueValues.toString());

            for (Station neighbor : graph.getConnectedNeighbours(current)) {
                Double newDist = shortestDistances.get(current) + graph.getWeight(current, neighbor);
                if (shortestDistances.get(neighbor) == null || newDist < shortestDistances.get(neighbor)) {
                    shortestDistances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    unvisited.add(neighbor);
                }
            }
        }

        if (shortestDistances.get(target).equals(Double.MAX_VALUE)) {
            // no path was found so i return an empty list
            return Collections.emptyList();
        }

        // Print the results
        for (int i = 0; i < visitedOrder.size(); i++) {
            System.out.println(visitedOrder.get(i) + " | " + priorityQueueContent.get(i));
        }

        // Reconstructing the shortest path
        List<Station> path = new ArrayList<>();
        for (Station at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return path;
    }




  //You Can uncomment the main method and test the functionality of the dijkstra algorithm within the class if you wish to

    /*
    public static void main(String[] args) {
        // Create graph from stations.csv and tracks.csv
        List<Station> stations = Station.convertToList(Station.readFromCSV("resources/stations.csv"));
        List<Track> tracks = Track.readFromCSV("resources/tracks.csv");

        WeightedMatrixGraph<Station> graph = new WeightedMatrixGraph<>(false, stations.toArray(new Station[0]));

        for (Track track : tracks) {
            Station from = stations.stream().filter(station -> station.getCode().equalsIgnoreCase(track.getCode())).findFirst().orElse(null);
            Station to = stations.stream().filter(station -> station.getCode().equalsIgnoreCase(track.getNextCode())).findFirst().orElse(null);
            if (from != null && to != null) {
                graph.connect(from, to, track.getDistanceTo());
                System.out.println("Connected: " + from.getName() + " to " + to.getName());
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start station code (only All uppercase letters allowed):");
        String startCode = scanner.nextLine().toUpperCase();
        System.out.println("Enter end station code (only All uppercase letters allowed):");
        String endCode = scanner.nextLine().toUpperCase();

        Station startStation = stations.stream().filter(station -> station.getCode().equals(startCode)).findFirst().orElse(null);
        Station endStation = stations.stream().filter(station -> station.getCode().equals(endCode)).findFirst().orElse(null);

        if (startStation == null || endStation == null) {
            System.out.println("Invalid station code(s) provided.");
            return;
        }

        List<Station> shortestPath = findShortestPath(graph, startStation, endStation);
        System.out.println("Shortest path from " + startStation.getName() + " to " + endStation.getName() + ": " + shortestPath.stream().map(Station::getName).collect(Collectors.joining(" -> ")));
    }

     */
}




