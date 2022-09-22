package karat;

import java.util.*;

public class FindEarliestAncestor {
    private static int ancestor=-1;
    private static int curDepth;

    public static void main(String[] args) {
        List<int[]> pairs = List.of(
                new int[]{2, 3},
                new int[]{3, 15},
                new int[]{3, 6},
                new int[]{5, 6},
                new int[]{5, 7},
                new int[]{4, 5},
                new int[]{4, 8},
                new int[]{4, 9},
                new int[]{9, 11},
                new int[]{14,4}
        );
        //findFarest2(pairs);
        //findNearestCommon(pairs,5,9);
        //findNearestCommon(pairs,4,9);
        //findNearestCommon(pairs,6,8);
        //findNearestCommon(pairs,15,6);
    }

    public static void findNearestCommon(List<int[]> pairs, int id1, int id2) {
        //DAG，然后路径用map存储，节点->depth
        //其中一个起点上bfs，第一个交集就是最近公共先祖

        Map<Integer, List<Integer>> graph = new HashMap<>();

        // bottom up
        for (int[] p : pairs) {
            int u = p[1], v = p[0];
            List<Integer> temp = graph.getOrDefault(u, new ArrayList<>());
            temp.add(v);
            graph.put(u, temp);
        }

        // 打两个source的路径，求交集
        Map<Integer, Integer> path1 = new HashMap<>();
        Map<Integer,Integer> path2 = new HashMap<>();
        dfsClimb(id1,0,path1,graph);
        dfsClimb(id2,0,path2,graph);

        curDepth = Integer.MAX_VALUE;
        for (Integer node : path1.keySet()) {
                if (path2.containsKey(node) && path1.get(node)<curDepth) {
                    ancestor = node;
                    curDepth = path1.get(node);
                }
            }
        System.out.println(ancestor);
    }

    public static void dfsClimb(int source, int depth, Map<Integer, Integer> path, Map<Integer, List<Integer>> graph) {
        if (!graph.containsKey(source)) {
            return; //reach sink
        }

        path.put(source, depth);
        for (int neighbor: graph.get(source)) {
            dfsClimb(neighbor,depth+1, path,graph);
        }
    }

    public static void findFarest2(List<int[]> pairs){
        // 建有向图，top down
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> nodeId = new HashSet<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int[] p : pairs) {
            int u = p[0], v = p[1];
            nodeId.add(u);
            nodeId.add(v);
            indegree.put(v, indegree.getOrDefault(v, 0) + 1);
            List<Integer> temp = graph.getOrDefault(u, new ArrayList<>());
            temp.add(v);
            graph.put(u, temp);
        }

        // 入度为0的source开始深搜，只选路径最长的
        Queue<Integer> sources = new LinkedList<>();
        for (int id : nodeId) {
            if (!indegree.containsKey(id)) {//i.e. indegree=0
                sources.offer(id);
            }
        }

        int maxDepth=-1;
        int target=15;
        while (!sources.isEmpty()) {
            int curSource = sources.poll();
            int curDepth = findDepth(curSource, target, graph, 0);
            if (maxDepth < curDepth) {
                ancestor = curSource;
                maxDepth = curDepth;
            }
        }
        System.out.println(ancestor);
    }

    public static int findDepth(int source, int target, Map<Integer, List<Integer>> graph, int curDepth) {
        if (!graph.containsKey(source)) {//sink
            if (source == target) {
                return curDepth;
            }
            return -1;//unknown id
        }

        int maxDepth=-1;
        for (int each : graph.get(source)) {
            maxDepth = Math.max(maxDepth, findDepth(each, target, graph, curDepth + 1));
        }
        return maxDepth;
    }

    public static void findFarest(List<int[]> pairs){
        // 建有向图
        Map<Integer, List<Integer>> graph = new HashMap<>();
        //Map<Integer, Integer> indegree = new HashMap<>();

        // bottom up
        for (int[] p : pairs) {
            int v=p[0], u=p[1];
            List<Integer> temp = graph.getOrDefault(u, new ArrayList<>());
            temp.add(v);
            graph.put(u, temp);
            //indegree.put(v, indegree.getOrDefault(v, 0) + 1);
        }

        Set<Integer> visited = new HashSet<>();

        // dfs输入neighbors，返回最深的
        int start = 11;
        dfs(start, graph, 0, visited);
        System.out.println(ancestor);
    }

    public static void dfs(int cur, Map<Integer, List<Integer>> graph, int curDepth, Set<Integer> visited) {
        if (visited.contains(cur)) return;

        // 到sink
        if (!graph.containsKey(cur)) {
            if (curDepth > FindEarliestAncestor.curDepth) {
                FindEarliestAncestor.curDepth = curDepth;
                ancestor = cur;
            }
            return;
        }
        visited.add(cur);

        for (int neighbor : graph.get(cur)) {
            dfs(neighbor, graph, curDepth + 1, visited);
        }
    }
}
