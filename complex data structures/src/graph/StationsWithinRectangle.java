package graph;

import lists.DoublyLinkedList;
import model.Station;
import model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StationsWithinRectangle {

    private DoublyLinkedList<Station> stations;
    private List<Track> tracks;
    private WeightedMatrixGraph<Station> graph;

    public StationsWithinRectangle(String stationsFile, String tracksFile) {
        stations = (DoublyLinkedList<Station>) Station.readFromCSV(stationsFile);
        tracks = Track.readFromCSV(tracksFile);
        buildGraph();
    }

    private void buildGraph() {
        graph = new WeightedMatrixGraph<>(false);
        for (Station station : stations.toArrayList()) {
            graph.addVertex(station);
        }
        for (Track track : tracks) {
            Station fromStation = findByCode(track.getCode());
            Station toStation = findByCode(track.getNextCode());
            if (fromStation != null && toStation != null) {
                graph.connect(fromStation, toStation, track.getDistanceTo());
            }
        }
    }

    private Station findByCode(String code) {
        for (Station station : stations.toArrayList()) {
            if (station.getCode().equalsIgnoreCase(code)) {
                return station;
            }
        }
        return null;
    }

    public Station findStationByCode(String code) {
        return findByCode(code);
    }



    public void calculateMinimumSpanningTree(double lat1, double lon1, double lat2, double lon2) {

            // the starting station for BFS. in my case i start from the first index
            Station startStation = stations.toArrayList().get(0);

            List<Station> stationsWithin = findStationsWithinRectangle(lat1, lon1, lat2, lon2,startStation);

        if (stationsWithin.isEmpty()) {
            System.out.println("No stations available within the given rectangle.");
            return;
        }

        System.out.println("Stations within the rectangle:");
        for (Station station : stationsWithin) {
            System.out.println(station.getName());
        }

        WeightedMatrixGraph<Station> subgraph = new WeightedMatrixGraph<>(false);
        for (Station station : stationsWithin) {
            subgraph.addVertex(station);
        }
        for (Track track : tracks) {
            Station fromStation = findByCode(track.getCode());
            Station toStation = findByCode(track.getNextCode());
            if (fromStation != null && toStation != null && stationsWithin.contains(fromStation) && stationsWithin.contains(toStation)) {
                subgraph.connect(fromStation, toStation, track.getDistanceTo());
            }
        }

        MCSTPrim mcstPrim = new MCSTPrim(subgraph);
        mcstPrim.prim(stationsWithin.get(0));
        double totalLength = mcstPrim.calculateTotalLength();
        System.out.println("Total length of the minimum spanning tree: " + totalLength);
    }



    private List<Station> findStationsWithinRectangle(double lat1, double lon1, double lat2, double lon2, Station startStation) {
        // First, find all stations reachable from the start station using BFS
        List<Station> reachableStations = graph.breadthFirst(startStation);

        // Then, filter out stations that are not within the specified rectangle
        List<Station> stationsWithinRectangle = new ArrayList<>();
        for (Station station : reachableStations) {
            if (isWithinRectangle(station, lat1, lon1, lat2, lon2)) {
                stationsWithinRectangle.add(station);
            }
        }

        return stationsWithinRectangle;
    }


    private boolean isWithinRectangle(Station station, double lat1, double lon1, double lat2, double lon2) {
        double lat = station.getGeoLat();
        double lon = station.getGeoLng();
        return (Math.min(lat1, lat2) <= lat && lat <= Math.max(lat1, lat2)) &&
                (Math.min(lon1, lon2) <= lon && lon <= Math.max(lon1, lon2));
    }

}
