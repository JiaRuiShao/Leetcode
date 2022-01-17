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

    private static boolean backtracking() {

    }

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        
        return backtracking(nums, k, new boolean[nums.length], );
    }

    public static void main(String[] args) {
        int[] test1 = new int[] {4,3,2,3,5,2,1};
		System.out.println(canPartitionKSubsets(test1, 4)); // true

		int[] test2 = new int[] {1,2,3,4};
		System.out.println(canPartitionKSubsets(test2, 3)); // false
    }
    
}
