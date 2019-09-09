import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * @문제
     * 그래프가 주어졌을 때, 그 그래프의 최소 스패닝 트리를 구하는 프로그램을 작성하시오.
     * 최소 스패닝 트리는, 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 그 가중치의 합이 최소인 트리를 말한다.
     * @입력
     * 첫째 줄에 정점의 개수 V(1 ≤ V ≤ 10,000)와 간선의 개수 E(1 ≤ E ≤ 100,000)가 주어진다.
     * 다음 E개의 줄에는 각 간선에 대한 정보를 나타내는 세 정수 A, B, C가 주어진다.
     * 이는 A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있다는 의미이다.
     * C는 음수일 수도 있으며, 절댓값이 1,000,000을 넘지 않는다.
     * 최소 스패닝 트리의 가중치가 -2147483648보다 크거나 같고, 2147483647보다 작거나 같은 데이터만 입력으로 주어진다.
     * @출력
     * 첫째 줄에 최소 스패닝 트리의 가중치를 출력한다.
     * @예제입력
     * 3 3
     * 1 2 1
     * 2 3 2
     * 1 3 3
     * @예제출력
     * 3
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UnionFind unionFind = new UnionFind();
        List<Edge> mst = new ArrayList();

        int nodeNum = scanner.nextInt();
        int edgeNum = scanner.nextInt();
        int[] parent = new int[nodeNum + 1];

        int i = 0;
        while (i++ < edgeNum) {
            int nodeA = scanner.nextInt();
            int nodeB = scanner.nextInt();
            int distance = scanner.nextInt();
            mst.add(new Edge(nodeA, nodeB, distance));

            parent[i] = i;
        }

        Collections.sort(mst);
        int sum = 0;

        for (Edge edge : mst) {
            if (!unionFind.isSameParent(parent, edge.node[0], edge.node[1])) {
                unionFind.union(parent, edge.node[0], edge.node[1]);
                sum += edge.distance;
            }
        }
        System.out.printf("%d", sum);
    }
}


class  Edge implements Comparable{
    int[] node = new int[2];
    int distance;

    Edge(int a, int b, int distance) {
        this.node[0] = a;
        this.node[1] = b;
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        Edge edge = (Edge) o;
        if (this.distance < edge.distance)
            return -1;
        else if (this.distance > edge.distance)
            return 1;
        else
            return 0;
    }
}


class UnionFind {
    public int getParent(int[] parent, int node) {
        if (parent[node] == node) return node;
        return parent[node] = getParent(parent, parent[node]);
    }

    public boolean isSameParent(int[] parent, int a, int b) {
        int parentA = getParent(parent, a);
        int parentB = getParent(parent, b);
        return parentA == parentB;
    }

    public void union(int[] parent, int a, int b) {
        int parentA = getParent(parent, a);
        int parentB = getParent(parent, b);
        if (parentA < parentB)
            parent[b] = a;
        else
            parent[a] = b;
    }
}
