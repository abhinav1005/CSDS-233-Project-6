
/**
 * Abhinav Khanna axk1312
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    /**
     * Nested Vertex class
     */
    private class Vertex implements Comparable<Vertex> {
        // stores the edges
        private LinkedList<Edge> edges = new LinkedList<>();

        // stores the string
        private String id = "";

        // stores the parent of the node
        private Vertex parent;

        // boolean used later for searching algo
        private boolean encountered;

        // constructor for vertex
        public Vertex(String id) {
            this.id = id;
        }

        /**
         * equals to compare the objects and vertices
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

        // compares vertices based on their ID
        @Override
        public int compareTo(Graph.Vertex obj) {
            return id.compareTo(obj.id);
        }
    }

    // **************End of Nested Vertex Class */

    /**
     * Nested class Edge
     */
    private class Edge implements Comparable<Edge> {
        // stores the start node
        private Vertex beginNode;

        // stores the end node
        private Vertex endNode;

        // to compare two Edges
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge))
                return false;

            Edge edge = (Edge) obj;
            return beginNode.equals(edge.beginNode) && endNode.equals(edge.endNode);
        }

        // compare two edges
        @Override
        public int compareTo(Edge obj) {
            return endNode.id.compareTo(obj.endNode.id);
        }
    }

    // stores the vertices
    private ArrayList<Vertex> verticesList = new ArrayList<>();

    // counts the vertices
    private int countVertices = 0;

    /**
     * Constructor to initialise a graph
     * 
     * @param maximum
     */
    public Graph() {
        verticesList = new ArrayList<>();
        countVertices = 0;
    }

    /**
     * adds a node to the graph
     */
    public boolean addNode(String name) {
        // if node is already there in the list then return false
        if (contains(name)) {
            return false;
        }
        verticesList.add(new Vertex(name));
        countVertices++;
        return true;
    }

    /**
     * adds all the names to the list
     * if can't add any to the list then return false
     */
    public boolean addNodes(String[] names) {
        // flag to check wif all nodes have been added
        boolean status = true;
        for (String name : names) {
            // if the name is already in the graph then return false and goes to add next
            // node if any
            if (contains(name)) {
                status = false;
                continue;
            }
            verticesList.add(new Vertex(name));
            countVertices++;
        }
        return status;
    }

    /**
     * adds an edge between the from and to nodes
     */
    public boolean addEdge(String from, String to) {
        // Vertex form of the strings
        Vertex vFrom = new Vertex(from);
        Vertex vTo = new Vertex(to);

        // if the vertices are not in the graph then return false
        if (!(verticesList.contains(vFrom) && verticesList.contains(vTo))) {
            return false;
        }
        // use helper method to add edge
        return addEdgeHelper(from, to);
    }

    /**
     * adds edges between the from and the list of the to nodes
     * if can't add any one, then returns false
     */
    public boolean addEdges(String from, String[] tolist) {
        boolean status = true;
        for (int i = 0; i < tolist.length; i++) {
            Vertex fromV = new Vertex(from);
            Vertex toV = new Vertex(tolist[i]);
            if (!(verticesList.contains(fromV) && verticesList.contains(toV))) {
                return false;
            }
            boolean added = addEdgeHelper(from, tolist[i]);
            if (!added) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Remove a vertex from graph
     */
    public boolean removeNode(String name) {
        if (countVertices == 0 || !contains(name)) {
            return false;
        }
        removeNodeHelper(name);
        return true;
    }

    /**
     * Remove multiple vertices
     */
    public boolean removeNodes(String[] namelist) {
        for (String name : namelist) {
            if (countVertices == 0 || contains(name) == false) {
                return false;
            }
            removeNodeHelper(name);
        }
        return true;
    }

    /**
     * Adding edge helper method
     */
    private boolean addEdgeHelper(String from, String to) {
        // gets the vertex from the string
        Vertex firstNode = lookUpVertex(from);
        Vertex endNode = lookUpVertex(to);

        // makes an edge in two directions
        Edge edge1 = new Edge();
        edge1.beginNode = firstNode;
        edge1.endNode = endNode;

        Edge edge2 = new Edge();
        edge2.beginNode = endNode;
        edge2.endNode = firstNode;

        // if not in edges then adds
        boolean status = !firstNode.edges.contains(edge1);
        if (status) {
            firstNode.edges.add(edge1);
            endNode.edges.add(edge2);
        }
        return status;
    }

    private void removeNodeHelper(String name) {
        int pointer = verticesList.indexOf(new Vertex(name));
        Vertex delete = verticesList.get(pointer);

        Iterator<Edge> itEdge = delete.edges.iterator();
        while (itEdge.hasNext()) {
            Edge connectedEdge = itEdge.next();
            Edge temp = new Edge();
            temp.endNode = delete;
            temp.beginNode = connectedEdge.endNode;
            connectedEdge.endNode.edges.remove(temp);
        }
        verticesList.remove(pointer);
        countVertices--;
    }

    /**
     * Print all vertexes and their edges
     */
    public void printGraph() {
        Vertex[] vertex = new Vertex[countVertices];
        vertex = verticesList.toArray(vertex);
        Arrays.sort(vertex);
        for (int i = 0; i < vertex.length; i++) {
            System.out.print(vertex[i].id + " ");
            Edge[] edgeArray = new Edge[vertex[i].edges.size()];
            edgeArray = vertex[i].edges.toArray(edgeArray);
            Arrays.sort(edgeArray);
            for (int j = 0; j < edgeArray.length; j++) {
                System.out.print(edgeArray[j].endNode.id + " ");
            }
            System.out.println(" ");
        }
    }

    /**
     * reads a file from the file path as included in the parameter
     */
    public static Graph read(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        Graph graph = new Graph();
        ArrayList<String[]> readNode = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String temp = scanner.nextLine();
            String[] arr = temp.split("\\s");
            readNode.add(arr);
        }

        for (int i = 0; i < readNode.size(); i++) {
            graph.addNode(readNode.get(i)[0]);
        }
        for (int i = 0; i < readNode.size(); i++) {
            for (int j = 1; j < readNode.get(i).length; j++) {
                graph.addEdge(readNode.get(i)[0], readNode.get(i)[j]);
            }
        }
        scanner.close();
        return graph;
    }

    /**
     * Depth-first search
     */
    public String[] DFS(String from, String to, String neighborOrder) {
        // if start or end does not exist
        if (!contains(from) || !contains(to)) {
            return new String[0];
        }

        Vertex start = lookUpVertex(from);
        Vertex target = lookUpVertex(to);

        Vertex pointer = target;
        sort(neighborOrder);
        dfsHelper(verticesList.indexOf(start), -1, verticesList.indexOf(target));

        String[] toRet = new String[0];

        ArrayList<String> path = new ArrayList<>();
        while (pointer != null) {
            path.add(pointer.id);
            pointer = pointer.parent;
        }
        if (!start.equals(target) && path.size() == 1) {
            return new String[0];
        }

        toRet = path.toArray(toRet);
        for (Vertex vertex : verticesList) {
            vertex.encountered = false;
        }

        if (neighborOrder.equals("alphabetical")) {
            reverseArray(toRet);
        }
        return toRet;
    }

    /**
     * Breadth-first search
     */
    public String[] BFS(String from, String to, String neighborOrder) {
        if (!contains(from) || !contains(to)) {
            return new String[0];
        }
        sort(neighborOrder);

        Vertex start = lookUpVertex(from);
        Vertex target = lookUpVertex(to);
        ArrayList<String> path = bfsHelper(start, target);

        String[] toRet = new String[0];
        toRet = path.toArray(toRet);

        if (neighborOrder.equals("alphabetical")) {
            reverseArray(toRet);
        }
        return toRet;
    }

    private ArrayList<String> bfsHelper(Vertex first, Vertex target) {
        ArrayList<String> toRet = new ArrayList<>();
        first.parent = null;
        first.encountered = true;
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.addLast(first);
        while (!queue.isEmpty()) {
            Vertex vCurr = queue.remove();
            if (vCurr.equals(target)) {
                break;
            }
            Iterator<Edge> iterator = vCurr.edges.iterator();
            while (iterator.hasNext()) {
                Vertex neighbor = iterator.next().endNode;
                if (neighbor.encountered == false) {
                    neighbor.encountered = true;
                    neighbor.parent = vCurr;
                    queue.addLast(neighbor);
                }
            }
        }
        for (Vertex vertex : verticesList) {
            vertex.encountered = false;
        }
        while (target != null) {
            toRet.add(target.id);
            target = target.parent;
        }
        if (!first.equals(target) && toRet.size() == 1) {
            return new ArrayList<>();
        }
        return toRet;
    }

    /**
     * Find second shortest path from start point to another
     */
    public String[] secondShortestPath(String from, String to) {
        if (!contains(from) || !contains(to))
            return new String[0];
        Vertex first = lookUpVertex(from);
        Vertex target = lookUpVertex(to);
        Iterator<Edge> it = target.edges.iterator();
        String[] toRet = new String[0];
        ArrayList<String> path = bfsHelper(first, target);

        int expected = path.size();
        int secondLength = Integer.MAX_VALUE;
        while (it.hasNext()) {
            Vertex neighbor = it.next().endNode;
            ArrayList<String> temp = bfsHelper(first, neighbor);
            if (secondLength > temp.size() && temp.size() > expected - 1) {
                path = temp;
                secondLength = temp.size();
            }
        }
        path.add(0, target.id);
        // return an empty array if no other path found
        if (secondLength == Integer.MAX_VALUE) {
            return new String[0];
        }

        toRet = path.toArray(toRet);
        // reset encountered
        for (Vertex vertex : verticesList) {
            vertex.encountered = false;
        }
        reverseArray(toRet);
        return toRet;
    }

    /**
     * Helper method to check if the name is already in the vertices list
     */
    private boolean contains(String name) {
        Vertex toCheck = new Vertex(name);
        if (verticesList.contains(toCheck)) {
            return true;
        }
        return false;
    }

    private Vertex lookUpVertex(String string) {
        return verticesList.get(verticesList.indexOf(new Vertex(string)));
    }

    private void reverseArray(String arr[]) {
        String temp;
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    private void dfsHelper(Integer initial, Integer parent, Integer target) {
        // base case for breaking our of loop
        if (verticesList.get(target).encountered == true) {
            return;
        }
        Vertex vertex = verticesList.get(initial);
        vertex.encountered = true;
        vertex.parent = (parent == -1) ? null : verticesList.get(parent);

        Iterator<Edge> it = vertex.edges.iterator();

        while (it.hasNext()) {
            Edge edgePtr = it.next();
            int index = verticesList.indexOf(edgePtr.endNode);
            if (verticesList.get(index).encountered == false) {
                dfsHelper(index, initial, target);
            }
        }
    }

    private void sort(String neighborOrder) {
        if (neighborOrder.equals("alphabetical")) {
            for (Vertex v : verticesList) {
                v.edges.sort(Comparator.naturalOrder());
            }
        } else if (neighborOrder.equals("reverse")) {
            for (Vertex v : verticesList) {
                v.edges.sort(Comparator.reverseOrder());
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Graph graph = Graph.read("C:\\Users\\Abhinav\\Desktop\\test.txt");
        System.out.println("*****************Demo for Graph************************");
        System.out.println("The first initial Graph: ");
        graph.printGraph();
        System.out.println("*****************Demo for addNode************************");
        graph.addNode("X");
        // System.out.println("The Graph after adding X ");
        graph.printGraph();

        System.out.println("*****************Demo for addNodes************************");
        System.out.println("The Graph after adding X, Y, Z");
        graph.addNodes(new String[] { "X", "Y", "Z" });
        graph.printGraph();

        System.out.println("*****************Demo for removeNodes************************");
        System.out.println("The Graph after removing Y, Z");
        graph.removeNodes(new String[] { "Y", "Z" });
        graph.printGraph();

        System.out.println("*****************Demo for BFS************************");
        System.out.println("Path using BFS : ");
        System.out.println(Arrays.toString(graph.BFS("A", "C", "reverse")));

        System.out.println("*****************Demo for DFS************************");
        System.out.println("Path using DFS : No such path is found ");
        System.out.println(Arrays.toString(graph.DFS("A", "X", "Alphabetical")));

        System.out.println("*****************Demo for DFS************************");
        System.out.println("Path using DFS");
        System.out.println(Arrays.toString(graph.BFS("A", "C", "reverse")));
    }
}
