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
 * 
 * - S1 Parent Map + BFS: O(n), O(n)
 * - S2 DFS with dist tracking: O(n), O(h)
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
    // Time: O(n)
    // Space: O(h)
    class Solution2_DFS {
        // for a target node, we need to go down, go up (continue up and go the other side)
        // Note: k could be 0
        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            List<Integer> kDistNodes = new ArrayList<>();
            dfs(root, target, k, kDistNodes);
            return kDistNodes;
        }

        private Integer dfs(TreeNode root, TreeNode target, int k, List<Integer> res) {
            if (root == null) {
                return null; // target not found
            }

            // pre-order to find target
            if (root == target) {
                // go down for pre-order traverse
                goDownByK(root, k, res);
                // go up for post-order traverse
                return 0; // mark as found target, dist == 0
            }

            Integer left = dfs(root.left, target, k, res);
            if (left != null) {
                int dist = left + 1;
                if (dist == k) {
                    res.add(root.val);
                } else if (dist < k) { // go up and go right
                    // go down for pre-order traverse
                    goDownByK(root.right, k - dist - 1, res);
                }
                return dist;
            }

            Integer right = dfs(root.right, target, k, res);
            if (right != null) {
                int dist = right + 1;
                if (dist == k) {
                    res.add(root.val);
                } else if (dist < k) { // go up and go left
                    // go down for pre-order traverse
                    goDownByK(root.left, k - dist - 1, res);
                }
                return dist;
            }
            return null;
        }

        private void goDownByK(TreeNode root, int k, List<Integer> res) {
            if (root == null) {
                return;
            }
            if (k == 0) {
                res.add(root.val);
                return;
            }
            goDownByK(root.left, k - 1, res);
            goDownByK(root.right, k - 1, res);
        }
    }
}
