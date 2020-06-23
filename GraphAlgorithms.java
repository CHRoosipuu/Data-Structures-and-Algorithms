import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Carl Henry Roosipuu
 * @userid croosipuu3
 * @GTID 903328574
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {

        if (start == null) {
            throw new IllegalArgumentException("Starting vertex cannot be"
                    + "null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Graph must contain starting"
                    + "vertex");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        List<Vertex<T>> returnList = new ArrayList<>();

        Set<Vertex<T>> vs = new HashSet<>();
        Queue<Vertex<T>> q = new LinkedList<>();

        returnList.add(start);
        vs.add(start);
        q.add(start);

        int verticesCount = graph.getVertices().size();

        while (vs.size() < verticesCount && !q.isEmpty()) {
            Vertex<T> v = q.poll();
            for (VertexDistance<T> wd : adjList.get(v)) {
                Vertex<T> w = wd.getVertex();
                if (!vs.contains(w)) {
                    returnList.add(w);
                    vs.add(w);
                    q.add(w);
                }
            }

        }

        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Starting vertex cannot be"
                    + "null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Graph must contain starting"
                    + "vertex");
        }

        List<Vertex<T>> returnList = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        dfsHelper(start, graph, vs, returnList);

        return returnList;
    }

    /**
     * Private helper method for recursive implementation of depthFirstSearch.
     *
     * @param <T> the generic typing of the data
     * @param v the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param vs the set of visited vertices
     * @param returnList the list to be returned
     */
    private static <T> void dfsHelper(Vertex<T> v,
                                      Graph<T> graph,
                                      Set<Vertex<T>> vs,
                                      List<Vertex<T>> returnList) {
        if (!vs.contains(v)) {
            returnList.add(v);
            vs.add(v);
            Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                    graph.getAdjList();
            for (VertexDistance<T> wd : adjList.get(v)) {
                Vertex<T> w = wd.getVertex();
                if (!vs.contains(w)) {
                    dfsHelper(w, graph, vs, returnList);
                }
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Starting vertex cannot be"
                    + "null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Graph must contain starting"
                    + "vertex");
        }

        // Setup the distance map
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> v: graph.getVertices()) {
            distanceMap.put(v, Integer.MAX_VALUE);
        }
        distanceMap.replace(start, 0);

        // Set up priority Queue
        PriorityQueue<VertexDistance<T>> q = new PriorityQueue<>();
        q.add(new VertexDistance<>(start, 0));

        // Visited vertices
        Set<Vertex<T>> vs = new HashSet<>();

        while (!(q.isEmpty())) {
            Vertex<T> v = q.remove().getVertex();
            if (vs.add(v)) {
                for (VertexDistance<T> u : graph.getAdjList().get(v)) {
                    q.add(u);
                    if (distanceMap.get(u.getVertex()) > (distanceMap.get(v)
                            + u.getDistance())) {
                        distanceMap.put(u.getVertex(), distanceMap.get(v)
                                + u.getDistance());
                    }
                }
            }
        }
        return distanceMap;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {

        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        pq.addAll(graph.getEdges());

        DisjointSet ds = new DisjointSet(graph.getVertices());

        Set<Edge<T>> mst = new HashSet<>();

        while (!pq.isEmpty()
                && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> e = pq.poll();
            if (!ds.find(e.getU()).equals(ds.find(e.getV()))) {
                ds.union(e.getU(), e.getV());
                mst.add(e);
                Edge<T> oppositeE =
                        new Edge<>(e.getV(), e.getU(), e.getWeight());
                mst.add(oppositeE);
            }
        }
        if (mst.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        } else {
            return mst;
        }
    }
}
