package graph;


//Credits: the interface is taken from our teacher Frederik Bonte when shown in class
public interface Graph<V> {
    boolean areConnected(V vertex1, V vertex2);
    Iterable<V> getConnectedNeighbours(V vertex);
    boolean contains(V vertex);
}
