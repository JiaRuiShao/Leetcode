/**
 * 134. Gas Station
 * 
 * Followup:
 * - Return all possible starting points? Use BF nested loop
 */
public class _134 {
    // Time: O(n^2)
    // Space: O(1)
    class Solution0_BruteForce_TLE {
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int n = gas.length;
            for (int start = 0; start < n; start++) {
                int gasSum = 0;
                for (int curr = start; gasSum >= 0; curr++) {
                    if (curr == start + n) return start;
                    gasSum += gas[curr % n];
                    gasSum -= cost[curr % n];
                }
            }
            return -1;
        }
    }

    class Solution1_Greedy {
        // 1. If total gas >= total cost, solution exists
        // 2. If we can't reach station j from station i, 
        //    then we can't reach j from any station between i and j because all of them have less gas before reaching j
        // 3. So skip all intermediate stations and try from j
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int n = gas.length, start = 0, currGas = 0, totalGas = 0;
            for (int i = 0; i < n; i++) {
                int diff = gas[i] - cost[i];
                currGas += diff;
                if (currGas < 0) {
                    currGas = 0;
                    start = i + 1;
                }
                totalGas += diff;
            }
            return totalGas >= 0 ? start : -1;
        }
    }

    // Track prefix sum of (gas - cost), start index is the minIdx + 1 
    // where minIdx points to min prefix sum
    class Solution2_PrefixSum {
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int n = gas.length, start = -1, sum = 0, minSum = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int diff = gas[i] - cost[i];
                sum += diff;
                if (sum < minSum) {
                    minSum = sum;
                    start = (i + 1) % n;
                }
            }
            return sum >= 0 ? start : -1;
        }
    }
}
