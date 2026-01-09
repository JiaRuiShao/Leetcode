import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 621. Task Scheduler
 * 
 * Greedy: task with max frequency is bottleneck
 * 
 * Clarification:
 * - Task always upper case letters A-Z? Yes
 * - Range of n and tasks?
 */
public class _621 {
    // earliest available ≠ best to schedule now
    // here we process the task with earliest available time and then by task frequency desc
    // but the correct logic should be to sort by freq first, then by availability
    // failed for test where tasks = ["B","C","D","A","A","A","A","G"], n = 1
    // expected = 8, got 10
    class Solution_Wrong {
        class Task {
            char id;
            int count;
            int available;

            public Task(char c, int count) {
                this.id = c;
                this.count = count;
                this.available = -1;
            }
        }

        public int leastInterval(char[] tasks, int n) {
            int[] freq = new int[26];
            for (char c : tasks) {
                int index = c - 'A';
                freq[index]++;
            }

            // order by next available asc, then by count desc
            PriorityQueue<Task> q = new PriorityQueue<>((a, b) -> {
                if (a.available != b.available) {
                    return Integer.compare(a.available, b.available);
                }
                return Integer.compare(b.count, a.count);
            });

            for (int i = 0; i < 26; i++) {
                char c = (char) (i + 'A');
                if (freq[i] > 0) {
                    q.offer(new Task(c, freq[i]));
                }
            }

            int interval = 0;
            while (!q.isEmpty()) {
                Task curr = q.poll();
                interval = Math.max(interval + 1, curr.available);
                if (--curr.count > 0) {
                    curr.available = interval + n + 1;
                    q.offer(curr);
                }
            }
            return interval;
        }
    }

    // Correct implementation: two queues
    // availablePQ: tasks that are ready now
    // cooldownPQ: tasks still cooling -- we never schedule a colling task
    // pick the task with highest count among the available tasks
    class Solution1_PriorityQueue {
        class Task {
            char id;
            int count;
            int available;

            Task(char id, int count) {
                this.id = id;
                this.count = count;
                this.available = 0;
            }
        }

        // Time: O(nlog26) where n is tasks len
        // Space: O(1)
        public int leastInterval(char[] tasks, int n) {
            int[] freq = new int[26];
            for (char c : tasks) {
                freq[c - 'A']++;
            }

            // MaxHeap by remaining count (ONLY available tasks)
            PriorityQueue<Task> availablePQ = new PriorityQueue<>((a, b) -> b.count - a.count);

            // MinHeap by next available time (cooling tasks)
            PriorityQueue<Task> cooldownPQ = new PriorityQueue<>((a, b) -> a.available - b.available);

            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0) {
                    availablePQ.offer(new Task((char) ('A' + i), freq[i]));
                }
            }

            int time = 0;

            while (!availablePQ.isEmpty() || !cooldownPQ.isEmpty()) {
                time++;

                // Move tasks out of cooldown
                while (!cooldownPQ.isEmpty() && cooldownPQ.peek().available <= time) {
                    availablePQ.offer(cooldownPQ.poll());
                }

                if (!availablePQ.isEmpty()) {
                    Task curr = availablePQ.poll();
                    curr.count--;

                    if (curr.count > 0) {
                        curr.available = time + n + 1;
                        cooldownPQ.offer(curr);
                    }
                } else {
                    // No task available → jump time
                    time = cooldownPQ.peek().available - 1;
                }
            }

            return time;
        }
    }

    class Solution1_PriorityQueue2 {
        public int leastInterval(char[] tasks, int n) {
            // 1 - task freq
            int[] freq = new int[26];
            for (char task : tasks) {
                freq[task - 'A']++;
            }

            // 2 - build freq pq
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
            for (int f : freq) {
                if (f > 0) {
                    maxHeap.offer(f);
                }
            }

            // 3 - simulate task running process
            int time = 0;
            while (!maxHeap.isEmpty()) {
                List<Integer> cooldown = new ArrayList<>();
                for (int i = 0; i < n + 1; i++) {
                    Integer f = maxHeap.poll();
                    if (f == null && cooldown.isEmpty()) {
                        break;
                    }
                    if (f != null && --f > 0) {
                        cooldown.add(f);
                    }
                    time++;
                }
                maxHeap.addAll(cooldown);
            }
            return time;
        }
    }

    class Solution2_Math {
        public int leastInterval(char[] tasks, int n) {
            // Count frequency of each task
            int[] freq = new int[26];
            int maxFreq = 0;
            
            for (char task : tasks) {
                freq[task - 'A']++;
                maxFreq = Math.max(maxFreq, freq[task - 'A']);
            }
            
            // Count how many tasks have the maximum frequency
            int maxCount = 0;
            for (int f : freq) {
                if (f == maxFreq) {
                    maxCount++;
                }
            }
            
            // Calculate minimum intervals needed
            // (maxFreq - 1) creates the number of "frames"
            // (n + 1) is the size of each frame
            // maxCount accounts for the tasks in the last frame
            // time needed when maxFreq task is bottleneck
            int minIntervals = (maxFreq - 1) * (n + 1) + maxCount;
            
            // If tasks are diverse enough, no idle time needed
            // time needed when maxFreq task is NOT bottleneck
            return Math.max(minIntervals, tasks.length);
        }
    }
}
