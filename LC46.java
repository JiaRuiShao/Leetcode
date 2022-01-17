import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* 
Problem:
Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

Example 1:
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

Example 2:
Input: nums = [0,1]
Output: [[0,1],[1,0]]

Example 3:
Input: nums = [1]
Output: [[1]]

DFS/Backtrack
*/

public class LC46 {

    /**
     * Basic backtracking helper function.
     * 
     * Time: O(N * N!)
     * Space: (N)
     * 
     * @param nums the input numbers
     * @param path the track or "path"
     * @param result permutations list
     */
    private static void backtrack1(int[] nums, List<Integer> path, List<List<Integer>> result) {
        // base case
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // recursive rule
        for (int num : nums) {
            // illegal selection when the current num is already in the path, move on to the next element
            if (path.contains(num)) continue; // O(n)
            // **select
            path.add(num);
            // **move on to the next call stack in decision tree
            backtrack1(nums, path, result);
            // **deselect
            path.remove(path.size() - 1);
        }
    }

    /**
     * Basic Backtracking to find all permutations.
     * 
     * @param nums the input numbers
     * @return permutations list
     */
    public static List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> path = new LinkedList<>(); // **record "path"**
        backtrack1(nums, path, result);
        return result;
    }

    /**
     * Advanced backtracking helper function withe the help of used boolean arr that tracks whether each element is contained in current path or not.
     * 
     * Time: O(N!)
     * Space: (2N)
     * 
     * @param nums the input numbers
     * @param path the track or "path"
     * @param result permutations list
     */
    private static void backtrack2(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // base case
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // recursive rule
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            // illegal selection when the current num is already in the path, move on to the next element
            if (used[i]) continue; // O(1)
            // **select
            path.add(num);
            used[i] = true;
            // **move on to the next call stack in decision tree
            backtrack2(nums, used, path, result);
            // **deselect
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    /**
     * Basic Backtracking to find all permutations.
     * 
     * @param nums the input numbers
     * @return permutations list
     */
    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new LinkedList<>(); // save the results
        List<Integer> path = new LinkedList<>(); // **record "path"
        boolean[] used = new boolean[nums.length]; // **record each elements usage
        backtrack2(nums, used, path, result);
        return result;
    }
    
    public static void main(String[] args) {
		int[] test1 = new int[] {1, 2, 3};
		permute2(test1).stream().forEach(System.out::print); // [[[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
		System.out.println();

		int[] test2 = new int[] {0, 1};
		permute2(test2).stream().forEach(System.out::print); // [[0,1],[1,0]]
        System.out.println();

        int[] test3 = new int[] {1};
		permute2(test3).stream().forEach(System.out::print); // [[1]]
        System.out.println();
	}
}
