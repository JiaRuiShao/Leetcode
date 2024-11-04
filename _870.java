import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 870. Advantage Shuffle.
 */
public class _870 {
    class Solution1_Two_Pointers {
        public int[] advantageCount(int[] nums1, int[] nums2) {
            int n = nums1.length;
    
            // 1 - sort nums2 in desc order & keep the original arr order
            PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
            for (int i = 0; i < n; i++) {
                maxHeap.offer(new int[]{i, nums2[i]});
            }
            // 2 - sort nums1 in asc order
            Arrays.sort(nums1);
    
            // 3 - use two pointers technique to point to the smallest & largest elem in nums1
            int left = 0, right = n - 1;
            int[] res = new int[n];
    
            // 4 - matching nums1 smaller num against nums2 larger ones
            while (!maxHeap.isEmpty()) {
                int[] pair = maxHeap.poll();
                int i = pair[0], val = pair[1];
                if (val < nums1[right]) { // pair maxVal with largest val in nums1
                    res[i] = nums1[right];
                    right--;
                } else { // else pair with smallest val in nums1
                    res[i] = nums1[left];
                    left++;
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int[] nums2 = {1, 10, 4, 11};
        Solution1_Two_Pointers solution = new _870().new Solution1_Two_Pointers();
        System.out.println(Arrays.toString(solution.advantageCount(nums1, nums2))); // [2, 11, 7, 15]
    }
}