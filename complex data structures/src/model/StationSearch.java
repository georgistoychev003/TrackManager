package model;

import lists.DoublyLinkedList;
import sorting.MergeSort;

import java.util.ArrayList;
import java.util.Comparator;

public class StationSearch {

    /**
     * Performs binary search on the ArrayList of Stations.
     */
    public static Station binarySearchByName(ArrayList<Station> stations, String name) {
        int left = 0;
        int right = stations.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            Station midStation = stations.get(mid);
            int comparison = midStation.getName().compareToIgnoreCase(name);

            if (comparison == 0) {
                return midStation; // Name matches
            } else if (comparison < 0) {
                left = mid + 1; // Look in the upper half
            } else {
                right = mid - 1; // Look in the lower half
            }
        }

        return null; // Not found
    }


    public static void sortStationsByName(ArrayList<Station> stations) {
        // Collections.sort(stations);
        DoublyLinkedList<Station> stationDoublyLinkedList = new DoublyLinkedList<>();
        for (Station station : stations) {
            stationDoublyLinkedList.add(station);
        }
        MergeSort<Station> sorter = new MergeSort<>();
        Comparator<Station> comparator = Comparator.comparing(Station::getName);
        sorter.mergeSort(stationDoublyLinkedList, comparator);

        stations.clear();
        stations.addAll(stationDoublyLinkedList.toArrayList());

    }

    public static Station linearSearchByName(ArrayList<Station> stations, String name) {
        for (Station station : stations) {
            if (station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null; // Station not found
    }

}



