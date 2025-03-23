package helper;

import java.util.List;

public interface Graph {
    void addEdge(int from, int to, int weight);

    void removeEdge(int from, int to);

    boolean hasEdge(int from, int to);

    int weight(int from, int to);

    List<Edge> neighbors(int v);

    int size();
}