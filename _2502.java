import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 2502. Design Memory Allocator
 */
public class _2502 {
    class Solution1_Map {
        // free blocks: [start, end)
        private TreeMap<Integer, Integer> free;
        // allocated blocks: mID -> list of [start, size]
        private Map<Integer, List<int[]>> used;

        public Solution1_Map(int n) {
            free = new TreeMap<>();
            free.put(0, n);
            used = new HashMap<>();
        }

        // Allocate size units for mID
        public int allocate(int size, int mID) {

            for (Map.Entry<Integer, Integer> entry : free.entrySet()) {
                int start = entry.getKey();
                int end = entry.getValue();

                if (end - start >= size) {

                    // Remove old interval
                    free.remove(start);

                    // Remaining right part (if any)
                    if (start + size <= end) {
                        free.put(start + size, end);
                    }

                    // Record allocation
                    used.computeIfAbsent(mID, k -> new ArrayList<>()).add(new int[]{start, size});

                    return start;
                }
            }

            return -1;
        }

        // Free all blocks for mID
        public int freeMemory(int mID) {
            if (!used.containsKey(mID)) return 0;
            int freed = 0;

            for (int[] block : used.get(mID)) {
                int start = block[0];
                int size = block[1];
                int end = start + size;

                freed += size;

                merge(start, end);
            }

            used.remove(mID);
            return freed;
        }

        // Merge freed block into free map
        private void merge(int start, int end) {

            // Check left neighbor
            Map.Entry<Integer, Integer> left = free.lowerEntry(start);
            if (left != null && left.getValue() == start) {
                start = left.getKey();
                free.remove(left.getKey());
            }

            // Check right neighbor
            Map.Entry<Integer, Integer> right = free.higherEntry(start);
            if (right != null && end == right.getKey()) {
                end = right.getValue();
                free.remove(right.getKey());
            }

            free.put(start, end);
        }
    }

    class Solution2_Array {

        int[] memory;
        Map<Integer, List<Integer>> used; // mID: {startId1, startId2, ...}

        public Solution2_Array(int n) {
            memory = new int[n];
            used = new HashMap<>();
        }
        
        public int allocate(int size, int mID) {
            int n = memory.length, pos = 0;
            while (pos < n) {
                while (pos < n && memory[pos] != 0) {
                    pos++;
                }
                int count = 0, start = pos;
                while (pos < n && memory[pos] == 0 && count < size) {
                    count++;
                    pos++;
                }
                if (count == size) {
                    for (int i = start; i < start + size; i++) {
                        memory[i] = mID;
                    }
                    used.computeIfAbsent(mID, k -> new ArrayList<>()).add(start);
                    return start;
                }
            }
            return -1;
        }
        
        public int freeMemory(int mID) {
            int n = memory.length, pos = 0;
            int freed = 0;
            for (int start : used.getOrDefault(mID, List.of())) {
                pos = start;
                while (pos < n && memory[pos] == mID) {
                    memory[pos++] = 0;
                }
                freed += pos - start;
            }
            used.remove(mID);
            return freed;
        }
    }
}
