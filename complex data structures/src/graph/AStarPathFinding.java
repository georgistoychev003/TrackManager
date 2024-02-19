package graph;
import lists.MyList;
import model.Station;
import model.Track;

import java.util.*;

public class AStarPathFinding {

    private WeightedMatrixGraph<Station> graph;
    private Map<String, Station> stationMap;
    private PriorityQueue<Node> openSet;
    private Set<Station> closedSet;
    private Map<Station, Node> allNodes;

    protected static class Node {
        Station station;
        Node parent;
        double g;
        double h;
        double f;

        public Node(Station station) {
            this.station = station;
            this.g = Double.MAX_VALUE;
            this.h = 0;
            this.f = 0;
        }

        public String getCode() {
            return station.getCode();
        }
    }

    public AStarPathFinding(List<Track> tracks, List<Station> stations) {
        graph = new WeightedMatrixGraph<>(false);
        stationMap = new HashMap<>();
        openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        closedSet = new HashSet<>();
        allNodes = new HashMap<>();

        for (Station station : stations) {
            String code = station.getCode().trim().toLowerCase();
            graph.addVertex(station);
            stationMap.put(code, station);
            allNodes.put(station, new Node(station));
        }

        for (Track track : tracks) {
            Station fromStation = stationMap.get(track.getCode().trim().toLowerCase());
            Station toStation = stationMap.get(track.getNextCode().trim().toLowerCase());
            if (fromStation != null && toStation != null) {
                graph.connect(fromStation, toStation, track.getDistanceTo());
            }
        }
    }

    public double heuristic(Station current, Station goal) {
        double dLat = goal.getGeoLat() - current.getGeoLat();
        double dLng = goal.getGeoLng() - current.getGeoLng();
        return Math.sqrt(dLat * dLat + dLng * dLng);
    }

    public List<Station> findPath(String startCode, String goalCode) {
        startCode = startCode.trim().toLowerCase();
        goalCode = goalCode.trim().toLowerCase();

        Station startStation = stationMap.get(startCode);
        Station goalStation = stationMap.get(goalCode);

        if (startStation == null || goalStation == null) {
            System.out.println("Start or goal station not found in the station map.");
            return null;
        }

        Node startNode = allNodes.get(startStation);
        startNode.g = 0;
        startNode.h = heuristic(startStation, goalStation);
        startNode.f = startNode.g + startNode.h;
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            if (currentNode.station.equals(goalStation)) {
                return reconstructPath(currentNode);
            }
            closedSet.add(currentNode.station);

            for (Station neighbor : graph.getConnectedNeighbours(currentNode.station)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                Node neighborNode = allNodes.get(neighbor);
                double tentativeG = currentNode.g + graph.getWeight(currentNode.station, neighbor);

                if (tentativeG < neighborNode.g) {
                    neighborNode.parent = currentNode;
                    neighborNode.g = tentativeG;
                    neighborNode.h = heuristic(neighbor, goalStation);
                    neighborNode.f = neighborNode.g + neighborNode.h;

                    openSet.remove(neighborNode); // Remove and re-insert to update my priority queue
                    openSet.add(neighborNode);
                }
            }
        }

        return null; // Path not found
    }

    private List<Station> reconstructPath(Node node) {
        List<Station> path = new ArrayList<>();
        while (node != null) {
            path.add(node.station);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public double calculateTotalPathWeight(List<Station> path) {
        if (path == null || path.size() < 2) {
            return 0.0;
        }

        double totalWeight = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Station current = path.get(i);
            Station next = path.get(i + 1);
            totalWeight += graph.getWeight(current, next);
        }
        return totalWeight;
    }


    //I had to add this method to reset the state as when i tried to use the menu option to calculate the
    // shortest path between two stations more than once i was not able as the state of the
    // astar was not renewed but with this update my tests worked flawlessly
    public void resetState() {
        openSet.clear();
        closedSet.clear();
        for (Node node : allNodes.values()) {
            node.g = Double.MAX_VALUE;
            node.h = 0;
            node.f = 0;
            node.parent = null;
        }
    }


    // Main method for testing, it can be uncommented and tested to directly test the functionality
    // of thr A* within the class
    /*
    public static void main(String[] args) {
        MyList<Station> stationsMyList = Station.readFromCSV("resources/stations.csv");
        List<Station> stations = Station.convertToList(stationsMyList); // Convert to List
        List<Track> tracks = Track.readFromCSV("resources/tracks.csv");

        AStarPathFinding astar = new AStarPathFinding(tracks, stations);


        String startCode = "dv";
        String goalCode = "na";
        System.out.println("Finding path from " + startCode + " to " + goalCode);

        List<Station> path = astar.findPath(startCode, goalCode);
        if (path != null) {
            StringBuilder pathOutput = new StringBuilder();
            double totalWeight = 0;
            for (int i = 0; i < path.size(); i++) {
                Station station = path.get(i);
                pathOutput.append(station.getName()).append(" (").append(station.getCode().toUpperCase()).append(")");
                if (i < path.size() - 1) {
                    pathOutput.append(" -> ");
                    totalWeight += astar.graph.getWeight(station, path.get(i + 1));
                }
            }
            System.out.println("The shortest path is: " + pathOutput);
            System.out.println("The total weight (distance) of the route is: " + totalWeight);
        } else {
            System.out.println("No path found between the specified stations.");
        }
    }

     */

}


