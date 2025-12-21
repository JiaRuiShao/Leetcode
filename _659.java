import java.util.HashMap;
import java.util.Map;

/**
 * 659. Split Array into Consecutive Subsequences
 * 
 * Clarification:
 * - Can we use each elem once? Yes
 * - Do we need to use all elements in the array? Yes
 * - Do duplicate numbers exist? Yes
 */
public class _659 {
    // Time: O(n)
    // Space: O(n)
    // Greedy approach: always try to append the existing subsequence first; only start a new sequence if can't append
    // if we can append but we don't, we're wasting numbers
    class Solution1_Greedy_HashMap {
        public boolean isPossible(int[] nums) {
            // Frequency map: how many of each number available
            Map<Integer, Integer> freq = new HashMap<>();
            // Appendable map: how many subsequences end at each number
            Map<Integer, Integer> appendable = new HashMap<>();
            
            // Build frequency map
            for (int num : nums) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
            
            for (int num : nums) {
                // Already used
                if (freq.get(num) == 0) {
                    continue;
                }
                
                // Option 1: Append to existing subsequence ending at num-1
                if (appendable.getOrDefault(num - 1, 0) > 0) {
                    appendable.put(num - 1, appendable.get(num - 1) - 1);
                    appendable.put(num, appendable.getOrDefault(num, 0) + 1);
                    freq.put(num, freq.get(num) - 1);
                }

                // Option 2: Start new subsequence with num, num+1, num+2
                else if (freq.getOrDefault(num + 1, 0) > 0 && 
                        freq.getOrDefault(num + 2, 0) > 0) {
                    freq.put(num, freq.get(num) - 1);
                    freq.put(num + 1, freq.get(num + 1) - 1);
                    freq.put(num + 2, freq.get(num + 2) - 1);
                    appendable.put(num + 2, appendable.getOrDefault(num + 2, 0) + 1);
                }

                // Option 3: Cannot place this number
                else {
                    return false; // had to use all elements
                }
            }
            
            return true;
        }
    }
}
