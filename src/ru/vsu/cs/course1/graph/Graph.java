package ru.vsu.cs.course1.graph;

import java.util.*;

public class Graph {

    public int[][] adjMatrix;
    public boolean[] marks;
    private int vCount = 0; //вершины
    private int eCount = 0; //ребра
    final int INF = (int) Double.POSITIVE_INFINITY;

    public Graph(int vertexCount) {
        this.vCount = vertexCount;
        adjMatrix = new int[vertexCount][vertexCount];
        marks = new boolean[vertexCount];
    }

    public Graph() {
        this(0);
    }

    public int vertexCount() {
        return vCount;
    }

    public int edgeCount() {
        return eCount;
    }

    public int getWeight(int v1, int v2) {
        for (Integer adj : adjacencies(v1)) {
            if (adj == v2) {
                return adjMatrix[v1][v2];
            }
        }
        return (int) Double.POSITIVE_INFINITY;
    }

    public void addEdge(int v1, int v2, int weight) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new int[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (adjMatrix[v1][v2] != INF || adjMatrix[v1][v2] != 0) { //!adjMatrix[v1][v2]
            adjMatrix[v1][v2] = weight;
            adjMatrix[v2][v1] = weight;
            eCount++;
        }
    }

    public void removeEdge(int v1, int v2) {
        if (adjMatrix[v1][v2] != INF) {
            adjMatrix[v1][v2] = INF;
            adjMatrix[v2][v1] = INF;
            eCount--;
        }
    }

    public Iterable<Integer> adjacencies(int v) {
        return new Iterable<Integer>() {
            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {

                for (int i = 0; i < vCount; i++) {
                    if (adjMatrix[v][i] != INF && adjMatrix[v][i] != 0) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vCount; i++) {
                            if (adjMatrix[v][i] != INF && adjMatrix[v][i] != 0) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };

            }
        };
    }

    public static Graph fromString(String str) {
        Scanner sc = new Scanner(str);
        Graph myGraph = new Graph(sc.nextInt());

        while (sc.hasNext()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int weight = sc.nextInt();

            myGraph.addEdge(a, b, weight);
        }
        return myGraph;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb.append("strict graph").append(" {").append(nl);
        for (int v1 = 0; v1 < vertexCount(); v1++) {
            int count = 0;
            for (Integer v2 : this.adjacencies(v1)) {
                sb.append(String.format("  %d %s %d %s %d %s", v1, "--", v2, "[label = \"", getWeight(v1, v2), "\"]")).append(nl);
                count++;
            }
            if (count == 0) {
                sb.append(v1).append(nl);
            }
        }
        sb.append("}").append(nl);

        return sb.toString();
    }
}