import java.util.Arrays;
import java.util.TreeMap;

/**
 * 1235. Maximum Profit in Job Scheduling
 * 
 * Clarification:
 * - range of start/end times? If smaller, we can use dp array
 * - [startTime, endTime)?
 * - What count as overlapped?
 */
public class _1235 {
    // Time: O(nlogn)
    // Space: O(nlogn)
    class Solution1_DP_TreeMap {
        public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
            int n = profit.length;
            int[][] jobs = new int[n][3];
            for (int i = 0; i < n; i++) {
                jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
            }

            // sort by end time in asc order
            Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));

            // if using dp array, how to populate max profit from prevEnd to nextStart? Use TreeMap.floorKey() 
            // int[] dp = new int[maxEndTime + 1]; // max profit before time i
            // dp[end] = max(dp[end], dp[start] + profit)
            TreeMap<Integer, Integer> dp = new TreeMap<>();
            dp.put(0, 0);
            for (int[] job : jobs) {
                int start = job[0], end = job[1], earn = job[2];
                // below line logic is wrong -- dp values must NOT decrease as time increase
                // dp.put(end, Math.max(dp.getOrDefault(end, earn), dp.floorEntry(start).getValue() + earn));
                dp.put(end, Math.max(dp.lastEntry().getValue(), dp.floorEntry(start).getValue() + earn));
            }
            return dp.lastEntry().getValue();
        }
    }

    class Solution2_DP_BinarySearch {
        private class Job {
            int start, end, profit;
            
            Job(int start, int end, int profit) {
                this.start = start;
                this.end = end;
                this.profit = profit;
            }
        }
        
        public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
            int n = startTime.length;
            Job[] jobs = new Job[n];
            
            for (int i = 0; i < n; i++) {
                jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
            }
            
            // Sort by end time
            Arrays.sort(jobs, (a, b) -> a.end - b.end);
            
            // dp[i] = max profit using first i jobs
            int[] dp = new int[n + 1];
            
            for (int i = 1; i <= n; i++) {
                Job currentJob = jobs[i - 1];
                
                // Option 1: Don't take current job
                int skip = dp[i - 1];
                
                // Option 2: Take current job
                // Find last job that doesn't conflict
                int lastCompatible = findLastCompatibleJob(jobs, i - 1);
                int take = currentJob.profit + dp[lastCompatible + 1];
                
                dp[i] = Math.max(skip, take);
            }
            
            return dp[n];
        }
        
        /**
         * Find last job with end <= current job's start
         * Returns index in jobs array, or -1 if none found
         */
        private int findLastCompatibleJob(Job[] jobs, int currentIndex) {
            int currentStart = jobs[currentIndex].start;
            int left = 0;
            int right = currentIndex - 1;
            int result = -1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (jobs[mid].end <= currentStart) {
                    // Compatible, but check if there's a later one
                    result = mid;
                    left = mid + 1;
                } else {
                    // End time too late, look left
                    right = mid - 1;
                }
            }
            
            return result;
        }
    }

    class Solution3_DP_Recursion {
        private class Job {
            int start, end, profit;
            
            Job(int start, int end, int profit) {
                this.start = start;
                this.end = end;
                this.profit = profit;
            }
        }
        
        public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
            int n = startTime.length;
            Job[] jobs = new Job[n];
            
            for (int i = 0; i < n; i++) {
                jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
            }
            
            // CRITICAL: Sort by end time for DP to work properly
            Arrays.sort(jobs, (a, b) -> a.end - b.end);
            
            // DP with memoization
            int[] memo = new int[n];
            Arrays.fill(memo, -1);
            
            return dp(jobs, 0, memo);
        }
        
        private int dp(Job[] jobs, int index, int[] memo) {
            // Base case: processed all jobs
            if (index == jobs.length) {
                return 0;
            }
            
            // Check memoization
            if (memo[index] != -1) {
                return memo[index];
            }
            
            // Option 1: Skip current job
            int skip = dp(jobs, index + 1, memo);
            
            // Option 2: Take current job
            // Find next compatible job using binary search
            int nextIndex = findNextCompatibleJob(jobs, index);
            int take = jobs[index].profit + dp(jobs, nextIndex, memo);
            
            // Store and return maximum
            memo[index] = Math.max(skip, take);
            return memo[index];
        }
        
        /**
         * Binary search to find first job that starts >= current job's end time
         * Returns index of next compatible job, or jobs.length if none found
         */
        private int findNextCompatibleJob(Job[] jobs, int currentIndex) {
            int currentEnd = jobs[currentIndex].end;
            int left = currentIndex + 1;
            int right = jobs.length;
            
            // Find leftmost job with start >= currentEnd
            while (left < right) {
                int mid = left + (right - left) / 2;
                
                if (jobs[mid].start >= currentEnd) {
                    // Could be the answer, but check left for earlier one
                    right = mid;
                } else {
                    // Start time too early, look right
                    left = mid + 1;
                }
            }
            
            return left;
        }
    }
}
