import java.util.HashMap;
import java.util.Map;

/**
 * 666. Path Sum IV
 */
public class _666 {
    class Solution1_DFS {
        public int pathSum(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            
            Map<Integer, Integer> tree = new HashMap<>();
            for (int num : nums) {
                int key = num / 10; // depth*10 + position
                int value = num % 10;
                tree.put(key, value);
            }
            
            return dfs(tree, nums[0] / 10, 0);
        }
        
        private int dfs(Map<Integer, Integer> tree, int coordinate, int pathSum) {
            if (!tree.containsKey(coordinate)) return 0;
            
            pathSum += tree.get(coordinate);
            
            int depth = coordinate / 10;
            int position = coordinate % 10;
            
            // Calculate children coordinates
            int leftChild = (depth + 1) * 10 + position * 2 - 1;
            int rightChild = (depth + 1) * 10 + position * 2;
            
            // If leaf node, return the path sum
            if (!tree.containsKey(leftChild) && !tree.containsKey(rightChild)) {
                return pathSum;
            }
            
            // Sum of all paths in left and right subtrees
            return dfs(tree, leftChild, pathSum) + dfs(tree, rightChild, pathSum);
        }
    }
}
