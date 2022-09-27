package helper;

class UF {
    int count; // num of connected components
    int[] parent;

    public UF (int n) { // n is num of nodes in the graph
        count = n; // n is connected components num in teh beginning
        parent = new int[n];
        for (int i = 0; i < n; i++) { // set parent node as themselves
            parent[i] = i;
        }
    }

    public void union(int n1, int n2) { // connect two nodes together as one component
        // find the parent node / root node of two given nodes
        int p1 = findParent(n1), p2 = findParent(n2);
        // n1 & n2 are already connected
        if (p1 == p2) return;

        // set one as the other's parent
        parent[p1] = p2;
        // decrease the total num of connected components
        count--;
    }

    public boolean connected(int n1, int n2) { // whether two nodes are connected
        return findParent(n1) == findParent(n2);
    }

    private int findParent(int node) { // find the parent/root of given node, set root as the parent of all nodes in path from given node to root
        if (node == parent[node]) return node; // root
        parent[node] = findParent(parent[node]);
        return parent[node];
    }

    public int count() {
        return count;
    }
}