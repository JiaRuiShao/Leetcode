import java.util.LinkedList;
import java.util.Queue;

/**
 * 733. Flood Fill
 */
public class _733 {
    // Time: O(mn)
    // Space: O(mn)
    class Solution1_DFS {
        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            int originalColor = image[sr][sc];
            if (color == originalColor) return image;
            dfs(image, sr, sc, originalColor, color);
            return image;
        }

        private void dfs(int[][] image, int r, int c, int oldColor, int newColor) {
            int m = image.length, n = image[0].length;
            if (r < 0 || r >= m || c < 0 || c >= n || image[r][c] != oldColor) {
                return;
            }
            image[r][c] = newColor;
            dfs(image, r - 1, c, oldColor, newColor);
            dfs(image, r + 1, c, oldColor, newColor);
            dfs(image, r, c - 1, oldColor, newColor);
            dfs(image, r, c + 1, oldColor, newColor);
        }
    }

    class Solution2_BFS {
        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            if (image[sr][sc] == color) {
                return image;
            }
            
            int originalColor = image[sr][sc];
            int m = image.length;
            int n = image[0].length;
            
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{sr, sc});
            image[sr][sc] = color;
            
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                
                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n 
                        && image[nr][nc] == originalColor) {
                        
                        image[nr][nc] = color;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            
            return image;
        }
    }
}
