package graph;

import java.util.*;

public class WeightedMatrixGraph<V> extends AbstractGraph<V> {



    // Indicates whether the graph is directed or undirected
    private final boolean directed;


    // A matrix that holds the weight of the connection between two vertices
    private  Double[][] weights;

    // Constructor using SafeVarargs annotation for handling generic array arguments, i saw it used by our teacher Frederik Bonte
    @SafeVarargs
    public WeightedMatrixGraph(boolean directed, V... vertices) {
        super(vertices);
        this.directed = directed;
        this.weights = new Double[vertices.length][vertices.length]; // Initialize weights with Double to allow for null values
    }

    public boolean isDirected() {
        return directed;
    }

    // Connect two vertices with a specified weight
    public void connect(V vertex1, V vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        weights[index1][index2] = weight;
        if (!isDirected()) {
            weights[index2][index1] = weight;
        }
    }


    // Check if two vertices are directly connected
    @Override
    public boolean areConnected(V vertex1, V vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        return weights[index1][index2] != null;
    }


    // Return a list of vertices directly connected to the given vertex
    @Override
    public Iterable<V> getConnectedNeighbours(V vertex) {
        int vertexIndex = getIndex(vertex);
        List<V> neighbours = new ArrayList<>();

        for (int j = 0; j < weights[vertexIndex].length; j++) {
            if (weights[vertexIndex][j] != null) {
                neighbours.add(getVertices().get(j));
            }
        }

        return neighbours;
    }


    @Override
    public boolean contains(V vertex) {
        return getVertices().contains(vertex);
    }

    // Get the weight of the connection between two vertices
    protected int getIndex(V vertex) {
        int index = getVertices().indexOf(vertex);
        if (index == -1) {
            throw new IllegalArgumentException("Vertex not present in the graph.");
        }
        return index;
    }

    /*

    protected int getIndex(V vertex) {
        return getVertices().indexOf(vertex);
    }
    */

    // Get the weight of the connection between two vertices
    public Double getWeight(V vertex1, V vertex2) {
        if (!areConnected(vertex1, vertex2)) {
            return null; //new: Return null for no connection
        }
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        return weights[index1][index2];
    }

    public void addVertex(V vertex) {
        if (contains(vertex)) {
            throw new IllegalArgumentException("Vertex already present in the graph.");
        }
        int newSize = getVertices().size() + 1;

        // Create a new weight matrix with the updated size
        Double[][] newWeights = new Double[newSize][newSize];
        for (int i = 0; i < weights.length; i++) {
            System.arraycopy(weights[i], 0, newWeights[i], 0, weights[i].length);
        }

        // Update the weights matrix
        weights = newWeights;

        // Add the new vertex to the vertices list
        getVertices().add(vertex);
    }

    public String toWebGraph() {
        StringBuilder sb = new StringBuilder("digraph G {\n");

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                if (weights[i][j] != null) {
                    sb.append("\t")
                            .append(getVertices().get(i))
                            .append(" -> ")
                            .append(getVertices().get(j))
                            .append("[label=\"")
                            .append(weights[i][j])
                            .append("\"];\n");
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }


    public List<V> breadthFirst(V startVertex) {
        Set<V> visited = new HashSet<>();
        Queue<V> queue = new LinkedList<>();
        List<V> result = new ArrayList<>();
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            V currentVertex = queue.poll();
            if (!visited.contains(currentVertex)) {
                result.add(currentVertex);
                visited.add(currentVertex);
                int vertexIndex = getIndex(currentVertex);
                for (int j = 0; j < weights[vertexIndex].length; j++) {
                    if (weights[vertexIndex][j] != null && !visited.contains(getVertices().get(j))) {
                        queue.add(getVertices().get(j));
                    }
                }
            }
        }

        return result;
    }


    public List<V> depthFirst(V startVertex) {
        Set<V> visited = new HashSet<>();
        Stack<V> stack = new Stack<>();
        List<V> result = new ArrayList<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            V currentVertex = stack.pop();
            if (!visited.contains(currentVertex)) {
                result.add(currentVertex);
                visited.add(currentVertex);
                int vertexIndex = getIndex(currentVertex);
                for (int j = 0; j < weights[vertexIndex].length; j++) {
                    if (weights[vertexIndex][j] != null && !visited.contains(getVertices().get(j))) {
                        stack.push(getVertices().get(j));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WeightedMatrixGraph {");
        sb.append("\n  directed = ").append(directed);
        sb.append("\n  vertices = ").append(getVertices());

        sb.append("\n  connections = [\n");
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                if (weights[i][j] != null) {
                    sb.append("    ")
                            .append(getVertices().get(i))
                            .append(" -> ")
                            .append(getVertices().get(j))
                            .append(" [weight=")
                            .append(weights[i][j])
                            .append("]\n");
                }
            }
        }
        sb.append("  ]\n}");

        return sb.toString();
    }



}
