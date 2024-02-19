package graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Credits: Idea and overall structure of what methods to have in this class is provided
 * by Mr. Frederik Bonte during class lecture
 */

public abstract class AbstractGraph<V> implements Graph<V> {

    private final List<V> vertices;

    public AbstractGraph(V... vertices) {
        this.vertices = new ArrayList<>(Arrays.asList(vertices));
    }

    protected List<V> getVertices() {
        return vertices;
    }


    public boolean contains(V vertex) {
        return vertices.contains(vertex);
    }

    protected int getVertexCount(){
        return vertices.size();
    }


}
