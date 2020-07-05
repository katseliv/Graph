package ru.vsu.cs.course1.graph;

public class Solution {

    public static int main(String string) {
        Graph graph = Graph.fromString(string);
        graph.marks[0] = true;

        int sum = Integer.MAX_VALUE;
        sum = count(graph.adjMatrix, graph.marks, 0, graph.vertexCount(), 1, 0, sum);

        System.out.println(sum);
        return sum;
    }

    static int count(int[][] graph, boolean[] v, int currPos, int n, int count, int cost, int sum) {
        if (count == n && graph[currPos][0] > 0) {
            sum = Math.min(sum, cost + graph[currPos][0]);
            return sum;
        }

        for (int i = 0; i < n; i++) {
            if (!v[i] && graph[currPos][i] > 0) {
                v[i] = true;
                sum = count(graph, v, i, n, count + 1, cost + graph[currPos][i], sum);
                v[i] = false;
            }
        }

        return sum;
    }

}

