import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class _542 {
    static class Solution {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            int[][] ans = new int[m][n];
            Queue<int[]> q = new LinkedList<>(); // maintain the queue of cells to be examined next

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        q.offer(new int[]{i, j}); // add all the cells with 0s to q
                    } else {
                        ans[i][j] = Integer.MAX_VALUE; // need to be updated during the BFS
                    }
                }
            }

            int[] directions = new int[]{0, 1, 0, -1, 0}; // (x, y+1), (x+1, y), (x, y-1), (x-1, 0)
            while (!q.isEmpty()) { // BFS start
                int[] curr = q.poll(); // poll the cell from queue
                int x = curr[0];
                int y = curr[1];

                for (int i = 0; i < directions.length - 1; i++) { // examine its neighbors
                    int nextX = directions[i] + curr[0];
                    int nextY = directions[i + 1] + curr[1];

                    // if the new calculated distance for neighbor (x,y) is smaller, we add (x,y) to q and update dist[x][y]
                    if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && ans[nextX][nextY] > ans[x][y] + 1) {
                        q.offer(new int[]{nextX, nextY});
                        ans[nextX][nextY] = ans[x][y] + 1;
                    }
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int[][] mat = new int[][] {{0,0,0}, {0,1,0}, {0,0,0}};
        System.out.println(Arrays.deepToString(new Solution().updateMatrix(mat)));

        // [[0,0,0],[0,1,0],[0,0,0]]
        System.out.println();

        mat = new int[][] {{0,0,0}, {0,1,0}, {1,1,1}};
        System.out.println(Arrays.deepToString(new Solution().updateMatrix(mat)));
        // [[0,0,0],[0,1,0],[1,2,1]]
    }
    
}
