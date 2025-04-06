/**
 * 1361. Validate Binary Tree Nodes
 */
public class _1361 {
    class Solution1_Union_Find {
        // Constraints:
        // only one root
        // every node has one parent (except for the root, root has zero)
        // no cycle
        // all connected
        public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
            // check constraint#2
            int[] inDegree = new int[n];
            for (int i = 0; i < n; i++) {
                int left = leftChild[i];
                int right = rightChild[i];
                if (left != -1) {
                    if (++inDegree[left] > 1) {
                        return false;
                    }
                }
                if (right != -1) {
                    if (++inDegree[right] > 1) {
                        return false;
                    }
                }
            }
    
            // check constraint#1
            int rootCount = 0;
            for (int i = 0; i < n; i++) {
                if (inDegree[i] == 0) {
                    if (++rootCount > 1) {
                        return false;
                    }
                }
            }
    
            // check constraint#3 & constraint#4
            UnionFind uf = new UnionFind(n);
            
            for (int i = 0; i < n; i++) {
                int left = leftChild[i];
                int right = rightChild[i];
                if (left != -1) {
                    if (uf.isConnected(i, left)) {
                        return false;
                    }
                    uf.union(i, left);
                }
                if (right != -1) {
                    if (uf.isConnected(i, right)) {
                        return false;
                    }
                    uf.union(i, right);
                }
            }
    
            return uf.getComponents() == 1;
        }
    
        class UnionFind {
            private int components;
            private int[] parent;
    
            public UnionFind(int n) {
                components = n;
                parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }
    
            private int findParent(int node) {
                if (parent[node] != node) {
                    parent[node] = findParent(parent[node]);
                }
                return parent[node];
            }
    
            public boolean isConnected(int n1, int n2) {
                return findParent(n1) == findParent(n2);
            }
    
            public void union(int n1, int n2) {
                int p1 = findParent(n1);
                int p2 = findParent(n2);
                if (p1 != p2) {
                    parent[p1] = p2;
                    components--;
                }
            }
    
            public int getComponents() {
                return components;
            }
        }
    }

    /**
     * We're only using visited[] here because there's no need to use onPath[] for binary tree structure.
     * In a graph, a node could be visited multiple times with no cycle; in a (binary) tree, if a node is visited more than once, then it means there's a cycle.
     */
    class Solution2_DFS {
        // Constraints:
        // only one root
        // every node has one parent (except for the root, root has zero)
        // no cycle
        // all connected
        public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
            // check constraint#2
            int[] inDegree = new int[n];
            for (int i = 0; i < n; i++) {
                int left = leftChild[i];
                int right = rightChild[i];
                if (left != -1) {
                    if (++inDegree[left] > 1) {
                        return false;
                    }
                }
                if (right != -1) {
                    if (++inDegree[right] > 1) {
                        return false;
                    }
                }
            }
    
            // check constraint#1
            int root = -1;
            for (int i = 0; i < n; i++) {
                if (inDegree[i] == 0) {
                    if (root != -1) {
                        return false;
                    }
                    root = i;
                }
            }
    
            // check constraint#3
            boolean[] visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (!visited[i] && hasCycle(leftChild, rightChild, visited, root)) {
                    return false;
                }
            }
    
            // check constraint#4
            for (boolean visit : visited) {
                if (!visit) {
                    return false;
                }
            }
            return true;
        }
    
        private boolean hasCycle(int[] left, int[] right, boolean[] visited, int node) {
            if (node == -1) return false;
            if (visited[node]) return true;
            visited[node] = true;
            return hasCycle(left, right, visited, left[node]) || hasCycle(left, right, visited, right[node]);
        }
    }
}
