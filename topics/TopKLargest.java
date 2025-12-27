package topics;

import java.util.PriorityQueue;

public class TopKLargest {
    // Using Min Heap - O(K) space
    public static int[] topKMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove smallest
            }
        }
        
        // Extract results
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }
        return result;
    }
    
    // Using Max Heap - O(N) space
    public static int[] topKMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = 
            new PriorityQueue<>((a, b) -> b - a);
        
        for (int num : nums) {
            maxHeap.offer(num);
        }
        
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        return result;
    }
}