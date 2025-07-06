/**
 * 11. Container With Most Water
 */
public class _11 {
    class Solution_Two_Pointers {
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
