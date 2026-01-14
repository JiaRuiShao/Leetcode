import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 1723. Find Minimum Time to Finish All Jobs
 * 
 * - S1: Backtrack with pruning O(k^n) [PREFERRED]
 * - S2: Binary Search + Backtrack O(logS*k^n)
 * - S3: DP with Bitmask
 */
public class _1723 {
    // Time: O(k*k^n) where n is jobs.length and k is num of workers
    class Solution1_Backtrack_By_Ball_TLE {
        private int minTime;
        public int minimumTimeRequired(int[] jobs, int k) {
            minTime = Integer.MAX_VALUE;
            int[] workingHrs = new int[k];
            backtrack(jobs, 0, workingHrs);
            return minTime;
        }

        private void backtrack(int[] jobs, int jobIdx, int[] hours) {
            if (jobIdx == jobs.length) {
                minTime = Math.min(minTime, Arrays.stream(hours).max().getAsInt());
                return;
            }
            for (int worker = 0; worker < hours.length; worker++) {
                int jobHours = jobs[jobIdx];
                hours[worker] += jobHours;
                backtrack(jobs, jobIdx + 1, hours);
                hours[worker] -= jobHours;
            }
        }
    }

    // use cache set to store the previously traversed workloads
    // reference: https://leetcode.com/problems/find-minimum-time-to-finish-all-jobs/solutions/1009817/one-branch-cutting-trick-to-solve-three-leetcode-questions/
    class Solution1_Backtrack_By_Ball_Prune {
        private int minTime;
        public int minimumTimeRequired(int[] jobs, int k) {
            minTime = Integer.MAX_VALUE;
            int[] workingHrs = new int[k];
            backtrack(jobs, 0, workingHrs);
            return minTime;
        }

        private void backtrack(int[] jobs, int jobIdx, int[] hours) {
            if (jobIdx == jobs.length) {
                minTime = Math.min(minTime, Arrays.stream(hours).max().getAsInt());
                return;
            }
            Set<Integer> cache = new HashSet<>();
            for (int worker = 0; worker < hours.length; worker++) {
                int jobHours = jobs[jobIdx];
                hours[worker] += jobHours;
                if (hours[worker] <= minTime && !cache.contains(hours[worker])) {
                    cache.add(hours[worker]);
                    backtrack(jobs, jobIdx + 1, hours);
                }
                hours[worker] -= jobHours;
            }
        }
    }

    class Solution2_BS_Backtrack_TLE {
        // left boundary BS on maxWorkingTime
        public int minimumTimeRequired(int[] jobs, int k) {
            int left = Arrays.stream(jobs).max().getAsInt();
            int right = Arrays.stream(jobs).sum();
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int[] workloads = new int[k];
                if (canFinishWithKWorker(jobs, 0, workloads, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }

        private boolean canFinishWithKWorker(int[] jobs, int idx, int[] workloads, int threshold) {
            if (idx == jobs.length) {
                return true;
            }
            int jobWorkload = jobs[idx];
            boolean canFinish = false;
            for (int worker = 0; worker < workloads.length; worker++) {
                int workload = workloads[worker];
                if (canFinish || workload + jobWorkload > threshold) continue;
                workloads[worker] += jobWorkload;
                canFinish = canFinishWithKWorker(jobs, idx + 1, workloads, threshold);
                workloads[worker] -= jobWorkload;
            }
            return canFinish;
        }
    }

    // Time: O(log(sum)*k^n)
    // Space: O(logn+k+n) where n is for recursion stack
    class Solution2_BS_Backtrack_Sorting {
        // left boundary BS on maxWorkingTime
        public int minimumTimeRequired(int[] jobs, int k) {
            int left = Arrays.stream(jobs).max().getAsInt();
            int right = Arrays.stream(jobs).sum();
            Arrays.sort(jobs);
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int[] workloads = new int[k];
                if (canFinishWithKWorker(jobs, jobs.length - 1, workloads, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }

        private boolean canFinishWithKWorker(int[] jobs, int idx, int[] workloads, int threshold) {
            if (idx == -1) {
                return true;
            }
            int jobWorkload = jobs[idx];
            boolean canFinish = false;
            for (int worker = 0; worker < workloads.length && !canFinish; worker++) {
                int workload = workloads[worker];
                if (workload + jobWorkload > threshold) continue;
                workloads[worker] += jobWorkload;
                canFinish = canFinishWithKWorker(jobs, idx - 1, workloads, threshold);
                workloads[worker] -= jobWorkload;
            }
            return canFinish;
        }
    }

    // only used when n & k are small
    // Time: O(k × 3^n)
    class Solution3_DP_Bitmask {
        public int minimumTimeRequired(int[] jobs, int k) {
            int n = jobs.length;
            
            // Precompute sum for each subset of jobs
            int[] subsetSum = new int[1 << n];
            for (int mask = 0; mask < (1 << n); mask++) {
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        subsetSum[mask] += jobs[i];
                    }
                }
            }
            
            // dp[i][mask] = minimum max load using i workers for jobs in mask
            int[][] dp = new int[k + 1][1 << n];
            for (int[] row : dp) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
            dp[0][0] = 0;
            
            for (int i = 1; i <= k; i++) {
                for (int mask = 0; mask < (1 << n); mask++) {
                    // Try all subsets of mask to assign to worker i
                    for (int subset = mask; subset > 0; subset = (subset - 1) & mask) {
                        int remaining = mask ^ subset;
                        
                        if (dp[i - 1][remaining] != Integer.MAX_VALUE) {
                            dp[i][mask] = Math.min(
                                dp[i][mask],
                                Math.max(dp[i - 1][remaining], subsetSum[subset])
                            );
                        }
                    }
                }
            }
            
            return dp[k][(1 << n) - 1];
        }
    }
}
