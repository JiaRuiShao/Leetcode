import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 1723. Find Minimum Time to Finish All Jobs
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
            for (int worker = 0; worker < workloads.length; worker++) {
                int workload = workloads[worker];
                if (canFinish || workload + jobWorkload > threshold) continue;
                workloads[worker] += jobWorkload;
                canFinish = canFinishWithKWorker(jobs, idx - 1, workloads, threshold);
                workloads[worker] -= jobWorkload;
            }
            return canFinish;
        }
    }
}
