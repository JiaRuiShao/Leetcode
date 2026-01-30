import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 85. Maximal Rectangle
 */
public class _85 {
    class Solution1_Histogram {
        public int maximalRectangle(char[][] matrix) {
            if (matrix == null || matrix.length == 0) {
                return 0;
            }
            
            int rows = matrix.length;
            int cols = matrix[0].length;
            int[] heights = new int[cols];
            int maxArea = 0;
            
            for (int r = 0; r < rows; r++) {
                // Build histogram for current row
                for (int c = 0; c < cols; c++) {
                    if (matrix[r][c] == '1') {
                        heights[c]++;
                    } else {
                        heights[c] = 0;
                    }
                }
                
                // Find largest rectangle in this histogram
                maxArea = Math.max(maxArea, largestRectangleInHistogram(heights));
            }
            
            return maxArea;
        }
        
        private int largestRectangleInHistogram(int[] heights) {
            Deque<Integer> stack = new ArrayDeque<>();
            int maxArea = 0;
            int n = heights.length;
            
            for (int i = 0; i <= n; i++) {
                int h = (i == n) ? 0 : heights[i];
                
                while (!stack.isEmpty() && h < heights[stack.peek()]) {
                    int height = heights[stack.pop()];
                    int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                    maxArea = Math.max(maxArea, height * width);
                }
                
                stack.push(i);
            }
            
            return maxArea;
        }
    }

    class Solution2_DP_RowByRow {
        // area = height[c] * (right[c] - left[c])
        // height[c] = contiguous 1s above (<= row)
        // left[c] = leftmost index for rectangle with curr grid as bottom left corner
        // right[c] = rigthmost index for rectangle (exlcusive)
        // 1) Update height
        // 2) Update left
        // 3) Update right
        // 4) Compute area
        public int maximalRectangle(char[][] matrix) {
            int maxRectangle = 0;
            int m = matrix.length, n = matrix[0].length;
            int[] height = new int[n];
            int[] left = new int[n];
            int[] right = new int[n];
            Arrays.fill(right, n);
            for (int r = 0; r < m; r++) {
                int currLeft = 0;
                for (int c = 0; c < n; c++) {
                    if (matrix[r][c] == '1') {
                        height[c]++;
                        left[c] = Math.max(currLeft, left[c]);
                    } else {
                        height[c] = 0;
                        left[c] = 0; // reset for next row
                        currLeft = c + 1;
                    }
                }

                int currRight = n;
                for (int c = n - 1; c >= 0; c--) {
                    if (matrix[r][c] == '1') {
                        right[c] = Math.min(currRight, right[c]);
                    } else {
                        right[c] = n; // reset for next row
                        currRight = c;
                    }
                    maxRectangle = Math.max(maxRectangle, height[c] * (right[c] - left[c]));
                }
            }
            return maxRectangle;
        }
    }
}
