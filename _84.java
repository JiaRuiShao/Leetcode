import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84. Largest Rectangle in Histogram
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

    class Solution1_OnePass_MonotonicStack {
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
