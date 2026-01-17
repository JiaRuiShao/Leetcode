import java.util.Deque;
import java.util.TreeMap;
import java.util.ArrayDeque;

/**
 * 239. Sliding Window Maximum
 * 
 * S0 BF: O(NK), O(1)
 * S1 MaxHeap/TreeMap: O(nlogk), O(k)
 * S2 MonoQueue: O(n), O(k) [PREFERRED]
 * S3 Prefix & SUffix Array: O(n), O(n) [PREFERRED]
 */
public class _239 {
    class Solution1_Sliding_Window_Mono_Queue {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length - k + 1;
            if (n <= 0) return new int[0];
            int[] max = new int[n];
            Deque<Integer> monoQueue = new ArrayDeque<>();
            int left = 0, right = 0, pos = 0;
            while (right < nums.length) {
                int add = nums[right++];
                while (!monoQueue.isEmpty() && monoQueue.peekLast() < add) {
                    monoQueue.pollLast();
                }
                monoQueue.offerLast(add);
                if (right - left == k) {
                    int currMax = monoQueue.peekFirst();
                    max[pos++] = currMax;
                    
                    if (nums[left++] == currMax) {
                        monoQueue.pollFirst();
                    }
                }
            }
            return max;
        }
    }

    class Solution2_TreeMap {
        /**
         * TreeMap approach - maintains sorted window
         * 
         * Unlike heap, TreeMap allows:
         * - O(log k) insertion
         * - O(log k) deletion of specific element
         * - O(1) to get max (lastKey)
         * 
         * Time: O(n log k)
         * Space: O(k)
         */
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            int[] result = new int[n - k + 1];
            
            // TreeMap: key = value, value = count (handle duplicates)
            TreeMap<Integer, Integer> map = new TreeMap<>();
            
            // Initialize first window
            for (int i = 0; i < k; i++) {
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
            result[0] = map.lastKey(); // Max element
            
            // Slide window
            for (int i = k; i < n; i++) {
                // Add new element
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
                
                // Remove leftmost element of previous window
                int toRemove = nums[i - k];
                map.put(toRemove, map.get(toRemove) - 1);
                if (map.get(toRemove) == 0) {
                    map.remove(toRemove);
                }
                
                result[i - k + 1] = map.lastKey();
            }
            
            return result;
        }
    }

    class Solution3_PrefixSuffix {
        /**
         * DP approach with prefix/suffix max arrays
         * 
         * Key insight: Every window either:
         * 1. Fits within a block - use prefix/suffix
         * 2. Spans blocks - combine suffix of left block + prefix of right block
         * 
         * Time: O(n)
         * Space: O(n)
         */
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            
            // prefixMax[i] = max from start of block to i
            int[] prefixMax = new int[n];
            // suffixMax[i] = max from i to end of block
            int[] suffixMax = new int[n];
            
            // Fill prefix and suffix max arrays
            for (int i = 0; i < n; i++) {
                if (i % k == 0) {
                    // Start of new block
                    prefixMax[i] = nums[i];
                } else {
                    prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
                }
            }
            
            for (int i = n - 1; i >= 0; i--) {
                if (i == n - 1 || (i + 1) % k == 0) {
                    // End of block
                    suffixMax[i] = nums[i];
                } else {
                    suffixMax[i] = Math.max(suffixMax[i + 1], nums[i]);
                }
            }
            
            // Calculate result
            int[] result = new int[n - k + 1];
            for (int i = 0; i <= n - k; i++) {
                int left = i;
                int right = i + k - 1;
                
                // Max of suffix starting at left and prefix ending at right
                result[i] = Math.max(suffixMax[left], prefixMax[right]);
            }
            
            return result;
        }
    }
}
