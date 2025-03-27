/**
 * 169. Majority Element
 */
public class _169 {
    class Solution4_Majority_Voting {
        // 1st solution: use a hashmap to store val, freq pairs (❌)
        // 2nd solution: Arrays.sort(nums), then traverse
        // 3rd solution: bit manipulation
        // 4th solution: the majority elem has to have positive counting/voting
        public int majorityElement(int[] nums) {
            // freq > len / 2
            int vote = 0;
            int candidate = 0;
            for (int num : nums) {
                if (vote == 0) { // when vote for last candidate became 0, we set curr num as new candidate
                    candidate = num;
                    vote++;
                } else if (candidate == num) {
                    vote++;
                } else {
                    vote--;
                }
            }
            return candidate;
        }
    }
}
