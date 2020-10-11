import java.util.*;
/*
* 给出方程式 A / B = k, 其中 A 和 B 均为用字符串表示的变量， k 是一个浮点型数字。根据已知方程式求解问题，并返回计算结果。如果结果不存在，则返回 -1.0。
输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
示例 1：
输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
解释：
给定：a / b = 2.0, b / c = 3.0
问题：a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
返回：[6.0, 0.5, -1.0, 1.0, -1.0 ]
*
示例 2：
输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
输出：[3.75000,0.40000,5.00000,0.20000]
*
示例 3：
输入：equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
输出：[0.50000,2.00000,-1.00000,-1.00000]
*
* */
public class 除法求值399 {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] answer = new double[queries.size()];
        Map<String, Map<String, Double>> graph = createGraph(equations, values); // 创建图
        int index = 0;
        for (List<String> q : queries){
            answer[index++] = dfs(graph, new HashSet<>(), q.get(0), q.get(1), 1);
        }
        return answer;
    }

    private double dfs(Map<String, Map<String, Double>> graph, Set<String> visited, String start, String end,
                       double ans) {
        if (!graph.containsKey(start)||!graph.containsKey(end)){
            return -1;
        }
        Map<String, Double> edges = graph.get(start);
        for (String key : edges.keySet()){
            if (!visited.contains(key)){
                visited.add(key);
                Double val = edges.get(key);
                if (key.equals(end)){
                    return ans * val; //返回乘积
                }
                double d = dfs(graph, visited, key, end, ans * val);
                if (d!= -1){
                    return d;
                }
            }
        }
        return -1;
    }

    private Map<String, Map<String, Double>> createGraph(List<List<String>> equations, double[] values) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i = 0; i <equations.size() ; i++) {
            // 取元素 from -> to, val;
            String from = equations.get(i).get(0);
            String to = equations.get(i).get(1);
            Double val = values[i];
            //构建关系 {from : {to: val}}
            Map<String, Double> edge1 = graph.getOrDefault(from, new HashMap<>());
            edge1.put(to, val);
            graph.put(from, edge1);

            Map<String, Double> edge2 = graph.getOrDefault(to, new HashMap<>());
            edge2.put(from, 1/ val);
            graph.put(to, edge2);
        }
        return graph;
    }


    //采用并查集， 但我看不懂
    static class Solution {
        static Map<String, String> parents;
        static Map<String, Double> val;


        public String find(String x) {

            if (!x.equals(parents.get(x))) {
                String tmpParent = parents.get(x);
                String root = find(tmpParent);
                double oldVal = val.get(x);
                val.put(x, oldVal * val.get(tmpParent));
                parents.put(x, root);
            }
            return parents.get(x);
        }

        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            parents = new HashMap<>();
            val = new HashMap<>();
            int i = 0;
            for (List<String> equation : equations) {
                String from = equation.get(0);
                String to = equation.get(1);
                double cur = values[i];
                if (!parents.containsKey(from) && !parents.containsKey(to)) {
                    parents.put(from, to);
                    val.put(from, cur);
                    parents.put(to, to);
                    val.put(to, 1.0);
                } else if (!parents.containsKey(from)) {
                    parents.put(from, to);
                    val.put(from, cur);
                } else if (!parents.containsKey(to)) {
                    parents.put(to, from);
                    val.put(to, 1 / cur);
                } else {
                    String pa = find(from);
                    String pb = find(to);
                    if (!pa.equals(pb)) {
                        parents.put(pa, pb);
                        val.put(pa, cur * val.get(to) / val.get(from));
                    }
                }
                i++;
            }
            i = 0;
            double[] res = new double[queries.size()];
            for (List<String> query : queries) {
                String from = query.get(0);
                String to = query.get(1);
                if (!parents.containsKey(from) || !parents.containsKey(to)) {
                    res[i++] = -1;
                    continue;
                }
                String pa = find(from);
                String pb = find(to);
                if (!pa.equals(pb)) res[i] = -1;
                else {
                    res[i] = val.get(from) / val.get(to);
                }
                i++;
            }
            return res;
        }
    }

}
