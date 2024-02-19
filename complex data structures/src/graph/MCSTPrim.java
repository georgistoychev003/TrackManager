package graph;

import heap.MinHeap;
import model.Station;

import java.util.*;

public class MCSTPrim {
    private final WeightedMatrixGraph<Station> graph;
    private final Map<Station, Double> key = new HashMap<>();
    private final Map<Station, Station> parent = new HashMap<>();
    private final MinHeap<Station> priorityQueue;

    public MCSTPrim(WeightedMatrixGraph<Station> graph) {
        this.graph = graph;
        this.priorityQueue = new MinHeap<>(Station.class, graph.getVertices().size());
    }

    public void prim(Station startVertex) {
        if (!graph.contains(startVertex)) {
            System.out.println("The starting vertex does not exist in the graph.");
            return;
        }

        for (Station vertex : graph.getVertices()) {
            key.put(vertex, Double.MAX_VALUE);
            parent.put(vertex, null);
            priorityQueue.push(vertex);
        }

        key.put(startVertex, 0.0);

        System.out.println("Prim's displayed:");
        printPriorityQueue();  // Print initial state of the priority queue


        while (!priorityQueue.isEmpty()) {
            Station currentVertex = priorityQueue.pop();

            for (Station neighbor : graph.getConnectedNeighbours(currentVertex)) {
                Double edgeWeight = graph.getWeight(currentVertex, neighbor);

                if (priorityQueue.contains(neighbor) && edgeWeight < key.get(neighbor)) {
                    parent.put(neighbor, currentVertex);
                    key.put(neighbor, edgeWeight);
                    priorityQueue.update(neighbor);
                }
            }
            printPriorityQueue();  // printing the state of the priority queue after each update
        }
    }


    private void printPriorityQueue() {
        System.out.println("Priority queue:");
        for (Station station : priorityQueue.getElements()) {
            String parentCode = (parent.get(station) != null) ? parent.get(station).getCode() : "None"; //Initially all stations except the start vertex have a parent of null because they are not yet part of the tree and thus i show a "none'' string to indicate that.
            String keyVal = (key.get(station) < Double.MAX_VALUE) ? key.get(station).toString() : "Infinity"; // initially all the stations are considered to be at an infinite distance from the start vertex until they are actually reached by the algorithm thus i show it as a word and not the int value which is confusing
            System.out.println(station.getCode() + " - " + keyVal + " - " + parentCode);
        }
        System.out.println();
    }

    public double calculateTotalLength() {
        double totalLength = 0.0;
        for (Station station : key.keySet()) {
            if (key.get(station) < Double.MAX_VALUE) {
                totalLength += key.get(station);
            }
        }
        return totalLength;
    }


    public Map<Station, Station> getParentMap() {
        return parent;
    }
}

