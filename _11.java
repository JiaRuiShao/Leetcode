/**
 * 11. Container With Most Water
 * 
 * Clarification:
 * - Are lines in height[] >=2
 * - Assume max area size is within integer range
 */
public class _11 {
    // area = (y - x) * min(yh, xh)
    class Solution_Two_Pointers_Greedy {
        public int maxArea(int[] height) {
            int maxArea = 0, left  = 0, right = height.length - 1;
            while (left < right) {
                int leftHeight = height[left];
                int rightHeight = height[right];
                int width = right - left;
                if (leftHeight < rightHeight) {
                    maxArea = Math.max(maxArea, width * leftHeight);
                    left++;
                } else {
                    maxArea = Math.max(maxArea, width * rightHeight);
                    right--;
                }
            }
            return maxArea;
        }
    }
}
