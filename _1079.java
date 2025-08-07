import java.util.Arrays;

/**
 * 1079. Letter Tile Possibilities
 */
public class _1079 {
    // permutation - elem used once -- dedup needed
    // tiles is not guaranteed to be sorted
    // Time: O(nlogn + n*n!) factorial -- n! nodes x work per node: n
    // Space: O(n)
    class Solution1_Backtrack_Permutation_ElemUsedOnce_DeDup {
        private int count;

        public int numTilePossibilities(String tiles) {
            count = 0;
            boolean[] visited = new boolean[tiles.length()];
            char[] arr = tiles.toCharArray();
            Arrays.sort(arr);
            backtrack(arr, visited);
            return count - 1; // exclude empty string
        }
        
        private void backtrack(char[] arr, boolean[] visited) {
            count++;
            int n = arr.length;
            char prev = '\0';
            for (int i = 0; i < n; i++) {
                char curr = arr[i];
                if (visited[i] || curr  == prev) continue;
                prev = curr;
                visited[i] = true;
                backtrack(arr, visited);
                visited[i] = false;
            }
        }
    }
}