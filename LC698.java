import java.util.Arrays;

/* 
Problem:
Given an integer array nums and an integer k, return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.

Example 1:
Input: nums = [4,3,2,3,5,2,1], k = 4
Output: true
Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.

Example 2:
Input: nums = [1,2,3,4], k = 3
Output: false

Backtracking
 */

public class LC698 {

    /**
     * A private backtracking helper function to fill all k buckets using all numbers in the nums input.
     * Time: O(N + k*2^N) where N is the input arr size & K is the target
     *  - backtrack traverse time: bucket num * time to fill one bucket (basically iterate over nums and for each element either use it or skip it, which is O(2^N) operation)
     * Space: O(N) 
     *  - boolean used[]
     * 
     * @param input the input arr nums
     * @param used the "selection list"
     * @param start the curr index
     * @param target each bucket's target sum
     * @param currSum the current bucket numbers sum
     * @param bucket the number of bucket needs to be filled
     * @return true/false
     */
    private static boolean backtrack(int[] input, boolean[] used, int start, int target, int currSum, int bucket) {
        // base case
        // if all buckets filled
        if (bucket == 1) return true;

        // if this bucket sum equals target, move to next bucket, reset bucket sum, restart from input[0]
        if (currSum == target) return backtrack(input, used, 0, target, 0, bucket - 1);

        // recursive rules
        // if this bucket is not filled
        for (int i = start; i < input.length; i++) {
            if (used[i] || currSum + input[i] > target) continue; // if this num has used or too large for this bucket
            // select
            used[i] = true;
            
            // recursive backtracking
            if (backtrack(input, used, i + 1, target, currSum + input[i], bucket)) return true;

            // undo select
            used[i] = false;
        }

        // tried all combinations, didn't find a way to fill all k buckets with all the numbers in the nums input 
        return false;
    }

    /**
     * Partition into K subsets/buckets.
     * 
     * @param nums the input numbers
     * @param k subset/bucket number required to be filled
     * @return true if we can find k subsets that have the same sum
     */
    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0) return false;
        int sum = Arrays.stream(nums).reduce(0, Integer::sum); // O(n)
        // int sum = 0;
        // for (int v : nums) sum += v;
        if (sum % k != 0) return false;
        return backtrack(nums, new boolean[nums.length], 0, sum / k, 0, k);
    }

    public static void main(String[] args) {
        int[] test1 = new int[] {4,3,2,3,5,2,1};
		System.out.println(canPartitionKSubsets(test1, 4)); // true

		int[] test2 = new int[] {1,2,3,4};
		System.out.println(canPartitionKSubsets(test2, 3)); // false
    }
    
}
