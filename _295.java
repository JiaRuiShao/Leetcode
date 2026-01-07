import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 295. Find Median from Data Stream
 * 
 * Followup:
 * - All numbers are in range [0, 100]? use int[] with size as 101 to store num freq
 * - 99% of numbers are in range [0, 100]? 
 */
public class _295 {
    // Space: O(n)
    class Solution1_PriorityQueue {
        PriorityQueue<Integer> minHeap; // larger half
        PriorityQueue<Integer> maxHeap; // smaller half

        public Solution1_PriorityQueue() {
            minHeap = new PriorityQueue<>();
            maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        }
        
        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll()); // make sure elem in minHeap >= elem in maxHeap
            if (minHeap.size() == maxHeap.size() + 1) { // make sure maxHeap.size == minHeap.size or minHeap.size + 1
                maxHeap.offer(minHeap.poll());
            }
        }
        
        public double findMedian() {
            Double median = maxHeap.isEmpty()? null : Double.valueOf(maxHeap.peek());
            if (!minHeap.isEmpty() && minHeap.size() == maxHeap.size()) {
                median = (median + minHeap.peek()) / 2.0;
            }
            return median;
        }
    }

    // Space: O(n)
    class Solution2_List_BinarySearch {
        private List<Integer> list;
        
        public Solution2_List_BinarySearch() {
            list = new ArrayList<>();
        }
        
        // Time: O(n)
        public void addNum(int num) {
            // Binary search to find insertion position
            int pos = Collections.binarySearch(list, num);
            if (pos < 0) {
                pos = -(pos + 1);
            }
            list.add(pos, num);  // O(n) - need to shift elements
        }
        
        // Time: O(1)
        public double findMedian() {
            int n = list.size();
            if (n % 2 == 1) {
                return list.get(n / 2);
            } else {
                return (list.get(n / 2 - 1) + list.get(n / 2)) / 2.0;
            }
        }
    }

    class Followup_AllWithinRange {
        int[] freq;
        int count;

        Followup_AllWithinRange() {
            freq = new int[101];
            count = 0;
        }

        public double findMedian() {
            // when total count is odd, mid1 == mi2
            // when total count is even, mid2 = mid1 + 1
            int mid1 = (count + 1) / 2;
            int mid2 = (count + 2) / 2;

            int c = 0;
            int m1 = -1, m2 = -1;

            for (int i = 0; i <= 100; i++) {
                c += freq[i];
                if (c >= mid1 && m1 == -1) m1 = i;
                if (c >= mid2) {
                    m2 = i;
                    break;
                }
            }
            return (m1 + m2) / 2.0;
        }
    }

    class Followup_MostWithinRange {
        int[] freq = new int[101];        // for [0,100]
        TreeMap<Integer, Integer> left;   // < 0
        TreeMap<Integer, Integer> right;  // > 100
        int totalCount;

        public void add(int num) {
            if (num >= 0 && num <= 100) {
                freq[num]++;
            } else if (num < 0) {
                left.put(num, left.getOrDefault(num, 0) + 1);
            } else {
                right.put(num, right.getOrDefault(num, 0) + 1);
            }
            totalCount++;
        }
    }
}
