public class Lesson_7 {

    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,3);
        graph.addEdge(1,4);
        graph.addEdge(1,6);
        graph.addEdge(2,6);
        graph.addEdge(2,8);
        graph.addEdge(3,8);
        graph.addEdge(8,9);
        graph.addEdge(6,5);
        graph.addEdge(6,7);
        graph.addEdge(4,5);
        graph.addEdge(5,7);
        graph.addEdge(9,7);

        BreadthFirstPaths bfp = new BreadthFirstPaths(graph, 1);
        System.out.println(bfp.hasPathTo(9));
        System.out.println(bfp.pathTo(9));

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 0);
        System.out.println(dfs.hasPathTo(9));
        System.out.println(dfs.pathTo(9));
    }
}
