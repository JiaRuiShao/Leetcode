import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 295. Find Median from Data Stream
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
}
