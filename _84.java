import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84. Largest Rectangle in Histogram
 * 
 * - S0 BF: O(n^2), O(1)
 * - S1 Prefix & Suffix DP Array: O(n), O(n)
 * - S2 MonoStack O(n), O(n)
 */
public class _84 {
    class Solution0_BruteForce {
        public int largestRectangleArea(int[] heights) {
            int maxArea = 0;
            // Try every possible starting position
            for (int i = 0; i < heights.length; i++) {
                int minHeight = heights[i];
                // Try every ending position
                for (int j = i; j < heights.length; j++) {
                    minHeight = Math.min(minHeight, heights[j]);
                    int width = j - i + 1;
                    int area = minHeight * width;
                    maxArea = Math.max(maxArea, area);
                }
            }
            return maxArea;
        }
    }

    class Solution1_Prefix_Suffix {
        /**
         * Precompute boundaries using DP
         * 
         * For each bar:
         * - leftBound[i] = leftmost index where all bars >= heights[i]
         * - rightBound[i] = rightmost index where all bars >= heights[i]
         * 
         * Time: O(n) - three passes
         * Space: O(n) - two arrays
         */
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            
            // leftBound[i] = how far left can bar i extend
            int[] leftBound = new int[n];
            // rightBound[i] = how far right can bar i extend
            int[] rightBound = new int[n];
            
            // Initialize bounds
            leftBound[0] = 0;
            rightBound[n - 1] = n - 1;
            
            // Compute left boundaries
            for (int i = 1; i < n; i++) {
                int left = i - 1;
                
                // Jump backwards using previously computed bounds
                while (left >= 0 && heights[left] >= heights[i]) {
                    left = leftBound[left] - 1;
                }
                
                leftBound[i] = left + 1;
            }
            
            // Compute right boundaries
            for (int i = n - 2; i >= 0; i--) {
                int right = i + 1;
                
                // Jump forwards using previously computed bounds
                while (right < n && heights[right] >= heights[i]) {
                    right = rightBound[right] + 1;
                }
                
                rightBound[i] = right - 1;
            }
            
            // Calculate max area
            int maxArea = 0;
            for (int i = 0; i < n; i++) {
                int width = rightBound[i] - leftBound[i] + 1;
                maxArea = Math.max(maxArea, heights[i] * width);
            }
            
            return maxArea;
        }
    }

    class Solution2_MonotonicStack {
        // max(area) = max((right - left + 1) * min(heights[left...right])); left <= right
        // for each bar, try to find the leftmost and rightmost index with curr height as minHeight
        // keep a mono increasing stack where head is the max val index
        // update area when a bar popped out
        // [2,1,2] maxArea = 3
        // [1,2,3,4,5] maxArea = 9
        public int largestRectangleArea(int[] heights) {
            int n = heights.length, maxArea = 0;
            Deque<Integer> maxStack = new ArrayDeque<>(); // mono increasing stack
            for (int i = n - 1; i >= 0; i--) {
                while (!maxStack.isEmpty() && heights[maxStack.peek()] > heights[i]) {
                    int height = heights[maxStack.pop()];
                    int left = i + 1;
                    int right = maxStack.isEmpty() ? n - 1: maxStack.peek() - 1;
                    int width = right - left + 1;
                    maxArea = Math.max(maxArea, height * width);
                }
                maxStack.push(i);
            }

            while (!maxStack.isEmpty()) {
                int height = heights[maxStack.pop()];
                // left = 0
                int width = maxStack.isEmpty() ? n : maxStack.peek();
                maxArea = Math.max(maxArea, height * width);
            }
            return maxArea;
        }
    }
}
