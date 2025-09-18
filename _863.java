import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import helper.TreeNode;

/**
 * 863. All Nodes Distance K in Binary Tree
 */
public class _863 {
    class Solution1_BFS_ParentMap {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            Map<TreeNode, TreeNode> parents = new HashMap<>();
            // dfs to build parents map
            dfs(root, parents);
            // we can also use bfs to construct parents map here
            // bfs(root, parents);

            // normal bfs
            List<Integer> nodes = new ArrayList<>();
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(target);
            Set<Integer> visited = new HashSet<>();
            visited.add(target.val);
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    TreeNode curr = q.poll();
                    if (k == 0) {
                        nodes.add(curr.val);
                        continue;
                    }
                    TreeNode left = curr.left, right = curr.right;
                    if (left != null && !visited.contains(left.val)) {
                        q.offer(left);
                        visited.add(left.val);
                    }
                    if (right != null && !visited.contains(right.val)) {
                        q.offer(right);
                        visited.add(right.val);
                    }
                    TreeNode parent = parents.get(curr);
                    if (parent != null && !visited.contains(parent.val)) {
                        q.offer(parent);
                        visited.add(parent.val);
                    }
                }
                k--;
            }
            return nodes;
        }

        private void dfs(TreeNode node, Map<TreeNode, TreeNode> parents) {
            if (node == null) return;
            if (node.left != null) parents.put(node.left, node);
            if (node.right != null) parents.put(node.right, node);
            dfs(node.left, parents);
            dfs(node.right, parents);
        }

        private void bfs(TreeNode node, Map<TreeNode, TreeNode> parents) {
            if (node == null) return;
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(node);
            while (!q.isEmpty()) {
                TreeNode curr = q.poll();
                if (curr.left != null) {
                    parents.put(curr.left, curr);
                    q.offer(curr.left);
                }
                if (curr.right != null) {
                    parents.put(curr.right, curr);
                    q.offer(curr.right);
                }
            }
        }
    }

    class Solution1_DFS_ParentMap {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            Map<TreeNode, TreeNode> parents = new HashMap<>();
            // bfs to build parents map
            bfs(root, parents);

            // dfs to find nodes with k dist
            List<Integer> nodes = new ArrayList<>();
            Set<Integer> visited = new HashSet<>();
            dfs(target, k, visited, parents, nodes);
            return nodes;
        }

        private void bfs(TreeNode node, Map<TreeNode, TreeNode> parents) {
            if (node == null) return;
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(node);
            while (!q.isEmpty()) {
                TreeNode curr = q.poll();
                if (curr.left != null) {
                    parents.put(curr.left, curr);
                    q.offer(curr.left);
                }
                if (curr.right != null) {
                    parents.put(curr.right, curr);
                    q.offer(curr.right);
                }
            }
        }

        private void dfs(TreeNode node, int k, Set<Integer> visited, Map<TreeNode, TreeNode> parents, List<Integer> nodes) {
            if (node == null || visited.contains(node.val)) return;
            if (k == 0) {
                nodes.add(node.val);
                visited.add(node.val);
                return;
            }
            k--;
            visited.add(node.val);
            dfs(node.left, k, visited, parents, nodes);
            dfs(node.right, k, visited, parents, nodes);
            dfs(parents.get(node), k, visited, parents, nodes);
        }
    }

    // When you find the target, fan out K down. As you unwind, at each ancestor, fan out again but don’t step back into the child that led to the target
    // Space complexity is optimized from O(n) to O(h)
    class Solution2_DFS {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            List<Integer> nodes = new ArrayList<>();
            dfs(root, target, k, nodes);
            return nodes;
        }

        private int dfs(TreeNode node, TreeNode target, int k, List<Integer> nodes) {
            if (node == null) return -1;
            if (node == target) {
                goDown(node, k, nodes);
                return 0; // found target, return distance betw this node to target node
            }
            int leftDist = dfs(node.left, target, k, nodes);
            if (leftDist != -1) { // target found in left subtree
                leftDist++;
                if (leftDist == k) {
                    nodes.add(node.val);
                } else if (leftDist < k) {
                    goDown(node.right, k - (leftDist + 1), nodes);
                }
                return leftDist;
            }
            int rightDist = dfs(node.right, target, k, nodes);
            if (rightDist != -1) {
                rightDist++;
                if (rightDist == k) {
                    nodes.add(node.val);
                } else if (rightDist < k) {
                    goDown(node.left, k - (rightDist + 1), nodes);
                }
                return rightDist;
            }
            return -1;
        }

        private void goDown(TreeNode node, int k, List<Integer> nodes) {
            if (node == null || k < 0) return;
            if (k == 0) {
                nodes.add(node.val);
                return;
            }
            goDown(node.left, k - 1, nodes);
            goDown(node.right, k - 1, nodes);
        }
    }

    // We can also convert this tree to graph with start node as target node, then perform graph dfs/bfs to find nodes with k distance
    class Solution3_Graph {

    }
}
