
/**
 * Abhinav Khanna axk1312
 */
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class WeightedGraph {
    /**
     * Nested Vertex Class
     */
    private class Vertex implements Comparable<Vertex> {
        private String id = "";
        private Vertex parent;
        private int weight;
        private LinkedList<Edge> edges = new LinkedList<>();
        private ArrayList<Vertex> connectedNodes = new ArrayList<>();

        /**
         * Constructor
         */
        public Vertex(String id) {
            this.id = id;
        }

        /**
         * Override equals method to compare
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Vertex)) {
                return false;
            }
            Vertex vertex = (Vertex) obj;
            return id.equals(vertex.id);
        }

        /**
         * override method compareTo for sorting
         */
        @Override
        public int compareTo(Vertex v) {
            // compares id and returns in alphabetical order
            return id.compareTo(v.id);
        }

        public Edge findEdge(String to) {
            if (!vertices.contains(new Vertex(to))) {
                return new Edge();
            }
            Edge search = new Edge();
            search.endNode = new Vertex(to);
            search = edges.get(edges.indexOf(search));
            return search;
        }
    }

    private class Edge implements Comparable<Edge> {

        private Vertex startNode = new Vertex("");
        private Vertex endNode = new Vertex("");
        private int weight;

        /**
         * Override equals method
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if ((obj instanceof Edge) == false) {
                return false;
            }

            Edge edge = (Edge) obj;
            return endNode.equals(edge.endNode);
        }

        @Override
        public int compareTo(Edge e) {
            return endNode.id.compareTo(e.endNode.id);
        }
    }

    /**
     * weight comparator for comparing weights and sorting accordingly
     */
    private class weightComparator implements Comparator<Vertex> {
        @Override
        public int compare(WeightedGraph.Vertex o1, WeightedGraph.Vertex o2) {
            return Integer.valueOf(o1.weight).compareTo(o2.weight);
        }
    }

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private int countVertex = 0;
    private int maxCount = 0;

    public WeightedGraph(int max) {
        vertices = new ArrayList<>();
        countVertex = 0;
        maxCount = max;
    }

    /**
     * Inserts a node into graph
     */
    public boolean addNode(String name) {
        if (countVertex == maxCount || vertexPresent(name))
            return false;
        vertices.add(new Vertex(name));
        countVertex++;
        return true;
    }

    /**
     * Inserts multiple nodes into graph using
     */
    public boolean addNodes(String[] names) {
        for (int i = 0; i < names.length; i++) {
            if (countVertex == maxCount || vertexPresent(names[i]))
                return false;
            vertices.add(new Vertex(names[i]));
            countVertex++;
        }
        return true;
    }

    /**
     * adds weighted edges to graph
     * returns false if either of nodes are not present in grapg
     */
    public boolean addWeightedEdge(String from, String to, int weight) {
        if (!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(to))))
            return false;
        boolean status = addEdgeHelper(from, to, weight);
        return status;
    }

    /**
     * Adds multiple specified edges to graph
     */
    public boolean addWeightedEdges(String from, String[] tolist, int[] weightlist) {
        boolean status = true;
        for (int i = 0; i < tolist.length; i++) {
            // if either form or to does not exist in the graph then false
            if (!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(tolist[i])))) {
                return false;
            }
            boolean added = addEdgeHelper(from, tolist[i], weightlist[i]);
            // updates the status after addition
            if (status == true) {
                status = added;
            }
        }
        return status;
    }

    /**
     * Remove a vertex from the graph
     */
    public boolean removeNode(String name) {
        if (countVertex == 0 || !vertexPresent(name)) {
            return false;
        }
        vertices.remove(vertices.indexOf(new Vertex(name)));
        countVertex--;
        return true;
    }

    /**
     * Remove multiple vertex from graph.
     * returns true when all are removed or else false
     */
    public boolean removeNodes(String[] namelist) {
        for (int i = 0; i < namelist.length; i++) {
            if (countVertex == 0 || !vertexPresent(namelist[i])) {
                return false;
            }
            vertices.remove(vertices.indexOf(new Vertex(namelist[i])));
            countVertex--;
        }
        return true;
    }

    /**
     * Prints the graph
     */
    public void printWeightedGraph() {
        Vertex[] listV = new Vertex[countVertex];
        listV = vertices.toArray(listV);
        Arrays.sort(listV);
        for (int i = 0; i < listV.length; i++) {
            System.out.print(listV[i].id + " ");
            Edge[] edgeAr = new Edge[listV[i].edges.size()];
            edgeAr = listV[i].edges.toArray(edgeAr);
            Arrays.sort(edgeAr);
            for (int count = 0; count < edgeAr.length; count++) {
                System.out.print(edgeAr[count].weight + " " + edgeAr[count].endNode.id + " ");
            }
            System.out.println(" ");
        }
    }

    /**
     * Constructs a graph from filename
     */
    public static WeightedGraph readWeighted(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        WeightedGraph graph = new WeightedGraph(Integer.MAX_VALUE);
        ArrayList<String[]> arrList = new ArrayList<>();
        while (scan.hasNextLine()) {
            String temp = scan.nextLine();
            String[] array = temp.split("\\s");
            arrList.add(array);
        }

        for (int i = 0; i < arrList.size(); i++) {
            graph.addNode(arrList.get(i)[0]);
        }
        for (int i = 0; i < arrList.size(); i++) {
            for (int j = 1; j < arrList.get(i).length; j = j + 2) {
                graph.addWeightedEdge(arrList.get(i)[0], arrList.get(i)[j + 1], Integer.parseInt(arrList.get(i)[j]));
            }
        }
        scan.close();
        return graph;
    }

    /**
     * find the shortest path using dijkstra
     */
    public String[] shortestPath(String from, String to) {
        if (!vertexPresent(from) || !vertexPresent(to))
            return new String[0];
        ArrayList<String> toRet = new ArrayList<>();
        String[] type = new String[0];
        Vertex startVert = findVertex(from);
        Vertex endVert = findVertex(to);
        dijkstraShortest(startVert);
        while (endVert != null) {
            toRet.add(endVert.id);
            endVert = endVert.parent;
        }
        return toRet.toArray(type);
    }

    /**
     * uses dijkstra to find the second shortest path, compares the weights using
     * min heap priority queues
     * calculates the weight difference and keeps on updating as it traverses the
     * nodes
     */
    public String[] secondShortestPath(String from, String to) {
        if (!vertexPresent(from) || !vertexPresent(to))
            return new String[0];
        ArrayList<String> toRet = new ArrayList<>();
        String[] type = new String[0];
        Vertex start = findVertex(from);
        Vertex end = findVertex(to);
        Vertex ptr = end;
        int weight = Integer.MAX_VALUE;
        int diff = Integer.MAX_VALUE;
        Edge newEdge = new Edge();
        Edge second = new Edge();
        dijkstraShortest(start);

        // compares connected nodes
        while (ptr.parent != null) {
            for (Vertex vertex : ptr.connectedNodes) {
                Edge edge = vertex.findEdge(ptr.id);
                if (!vertex.equals(ptr.parent) && weight > (vertex.weight + edge.weight)) {
                    weight = vertex.weight + edge.weight;
                    newEdge = edge;
                }
            }
            int currDifference = newEdge.startNode.weight - newEdge.endNode.weight + newEdge.weight;
            if (!newEdge.equals(new Edge()) && currDifference < diff && currDifference != 0) {
                diff = newEdge.startNode.weight - newEdge.endNode.weight + newEdge.weight;
                second = newEdge;
            }
            ptr = ptr.parent;
            weight = Integer.MAX_VALUE;
        }
        if (second.equals(new Edge()))
            return new String[0];
        second.endNode.parent = second.startNode;
        while (end != null) {
            toRet.add(end.id);
            end = end.parent;
        }
        return toRet.toArray(type);
    }

    // checks if the vertex is present
    private boolean vertexPresent(String name) {
        if (vertices.contains(new Vertex(name)))
            return true;
        return false;
    }

    private Vertex findVertex(String node) {
        return vertices.get(vertices.indexOf(new Vertex(node)));
    }

    private boolean addEdgeHelper(String from, String to, Integer weight) {
        int start = vertices.indexOf(new Vertex(from));
        int end = vertices.indexOf(new Vertex(to));
        Vertex startNode = vertices.get(start);
        Vertex endNode = vertices.get(end);

        Edge newEdge = new Edge();
        newEdge.startNode = startNode;
        newEdge.endNode = endNode;
        newEdge.weight = weight;

        endNode.connectedNodes.add(startNode);

        boolean status = !startNode.edges.contains(newEdge);
        if (status) {
            startNode.edges.add(newEdge);
        }
        return status;
    }

    /**
     * dijkstra algorithm. using a min heap priority queue
     */
    private void dijkstraShortest(Vertex from) {
        ArrayList<Vertex> arrList = new ArrayList<>();
        PriorityQueue<Vertex> heap = new PriorityQueue<>(new weightComparator());
        arrList.add(from);
        for (Vertex vertex : vertices) {
            if (!vertex.equals(from) && vertex.connectedNodes.contains(from)) {
                vertex.parent = from;
                vertex.weight = from.findEdge(vertex.id).weight;
                heap.add(vertex);
            } else {
                vertex.weight = Integer.MAX_VALUE;
            }
        }
        from.weight = 0;
        while (arrList.size() < countVertex) {
            Vertex curr = heap.poll();
            if (!arrList.contains(curr))
                arrList.add(curr);
            for (Edge edge : curr.edges) {
                Vertex neighbor = edge.endNode;
                if (!arrList.contains(neighbor)) {
                    int orig = neighbor.weight;
                    int temp = curr.weight + edge.weight;
                    neighbor.weight = Math.min(orig, temp);
                    if (temp < orig)
                        neighbor.parent = curr;
                    heap.add(neighbor);

                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        WeightedGraph graph = new WeightedGraph(100);
        graph = WeightedGraph.readWeighted("C:\\Users\\Abhinav\\Desktop\\weightedTest.txt");
        System.out.println("***************Demo for Weighted Graph**********");
        System.out.println("The Weighted graph is the following");
        graph.printWeightedGraph();
        System.out.println("***************Demo for shortestPath**********");
        System.out.println("Shortest Path using shortestPath() method from A to F");
        System.out.println(Arrays.toString(graph.shortestPath("A", "F")));

        System.out.println("***************Demo for secondShortestPath**********");
        System.out.println("Second shortest path using secondShortestPath() method from A to F");
        System.out.println(Arrays.toString(graph.secondShortestPath("A", "F")));
    }
}