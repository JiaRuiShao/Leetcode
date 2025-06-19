import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 460. LFU Cache
 */
public class _460 {
    static class Solution1_HashMap_DoublyLinkedList_Implementation {

        class LFUCache {

            // Doubly linked list node modified from 146 LRU question.
            // The list is maintained in order:
            // [LFU & LRU ... LFU & MRU] <-> [MFU & LRU ... MFU & MRU]
            class CacheNode {
                int key;
                int value;
                int freq;    // Frequency counter.
                CacheNode prev;
                CacheNode next;

                public CacheNode(int key, int value) {
                    this.key = key;
                    this.value = value;
                    this.freq = 1; // New nodes start with frequency 1.
                }
            }

            CacheNode head;
            CacheNode tail;
            Map<Integer, CacheNode> cacheMap;  // Key -> CacheNode.
            // Maps a frequency to the MRU (rightmost) node for that frequency group.
            Map<Integer, CacheNode> freqMap;
            final int capacity;

            public LFUCache(int capacity) {
                this.capacity = capacity;
                head = new CacheNode(-1, -1);
                tail = new CacheNode(-1, -1);
                head.next = tail;
                tail.prev = head;
                head.freq = 0;
                tail.freq = 0;
                cacheMap = new HashMap<>();
                freqMap = new HashMap<>();
            }

            public int get(int key) {
                CacheNode node = cacheMap.get(key);
                if (node == null) return -1;
                updateFrequency(node);
                return node.value;
            }

            public void put(int key, int value) {
                if (capacity == 0) return;

                CacheNode node = cacheMap.get(key);
                if (node != null) {
                    // Update existing node.
                    node.value = value;
                    updateFrequency(node);
                } else {
                    // Evict if at capacity: remove the LFU & LRU node (head.next).
                    if (cacheMap.size() == capacity) {
                        CacheNode toEvict = head.next;
                        updateFreqMapOnRemoval(toEvict, toEvict.freq);
                        removeNode(toEvict);
                        cacheMap.remove(toEvict.key);
                    }
                    // Create new node with frequency 1.
                    CacheNode newNode = new CacheNode(key, value);
                    // For a new node, if a frequency-1 group exists, insert after its MRU;
                    // otherwise, insert right after the head.
                    CacheNode insPoint = freqMap.containsKey(1) ? freqMap.get(1) : head;
                    addNodeAfter(newNode, insPoint);
                    cacheMap.put(key, newNode);
                    freqMap.put(1, newNode);
                }
            }

            // Increase the frequency of a node and reposition it.
            // Instead of traversing from the head to find an insertion point,
            // we capture the candidate from the node’s original previous pointer.
            private void updateFrequency(CacheNode node) {
                int oldFreq = node.freq;
                // Save the candidate insertion point before removal.
                updateFreqMapOnRemoval(node, oldFreq);
                CacheNode candidate = freqMap.getOrDefault(oldFreq, node.prev);
                removeNode(node);
                node.freq++;
                // If there's already a node with the new frequency, insert after its MRU.
                // Otherwise, we simply reinsert after the candidate.
                CacheNode insPoint = freqMap.getOrDefault(node.freq, candidate);
                addNodeAfter(node, insPoint);
                // Update the MRU pointer for the new frequency.
                freqMap.put(node.freq, node);
            }

            // Standard removal from the doubly linked list.
            private void removeNode(CacheNode node) {
                CacheNode prev = node.prev;
                CacheNode next = node.next;
                prev.next = next;
                next.prev = prev;
                node.prev = null;
                node.next = null;
            }

            // Insert node right after the specified previous node.
            private void addNodeAfter(CacheNode node, CacheNode prev) {
                CacheNode next = prev.next;
                prev.next = node;
                node.prev = prev;
                node.next = next;
                next.prev = node;
            }

            // When a node is removed from its frequency group, update freqMap accordingly.
            private void updateFreqMapOnRemoval(CacheNode node, int freq) {
                if (freqMap.containsKey(freq) && freqMap.get(freq) == node) {
                    // Find the new MRU for this frequency group by looking backward.
                    CacheNode prev = node.prev;
                    if (prev != head && prev.freq == freq) {
                        freqMap.put(freq, prev);
                    } else {
                        freqMap.remove(freq);
                    }
                }
            }
        }
    }

    static class Solution2_LinkedHashSet_Implementation {
        // freqKeys is to save mappings from frequency to keys (maintained as insertion order)
        // Q: Why we don't use other linked structure like Deque or List type here?
        // A: We need to guarantee 1) constant time to delete a key 2) maintain insertion order 
        class LFUCache {
            Map<Integer, Integer> keyVal;
            Map<Integer, Integer> keyFreq;
            Map<Integer, HashSet<Integer>> freqKeys;
            int capacity, minFreq;

            public LFUCache(int capacity) {
                this.capacity = capacity;
                minFreq = 0;
                keyVal = new HashMap<>();
                keyFreq = new HashMap<>();
                freqKeys = new HashMap<>();
            }
            
            public int get(int key) {
                Integer val = keyVal.get(key);
                if (val == null) {
                    return -1;
                }
                increaseUsage(key);
                return val;
            }

            private void increaseUsage(int key) {
                int freq = keyFreq.getOrDefault(key, 0);
                keyFreq.put(key, freq + 1);
                freqKeys.computeIfAbsent(freq, k -> new LinkedHashSet<>()).remove(key);
                freqKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
                if (freqKeys.get(freq).isEmpty() && freq == minFreq) {
                    minFreq++;
                }
            }
            
            public void put(int key, int value) {
                boolean existInMap = keyVal.containsKey(key);
                keyVal.put(key, value);
                increaseUsage(key);
                if (!existInMap) {
                    evictLFU();
                    minFreq = 1;
                }
            }

            private void evictLFU() {
                if (keyVal.size() > capacity) {
                    HashSet<Integer> keysWithLeastFrequency = freqKeys.get(minFreq);
                    int remove = keysWithLeastFrequency.iterator().next();
                    keysWithLeastFrequency.remove(remove);
                    keyVal.remove(remove);
                    keyFreq.remove(remove);
                }
            }
        }
    }

    static class MyLFUCache {
        // why not use TreeMap - because it uses Red-Black Tree under the hood and its operation is logrithmic time
        Map<Integer, Integer> keyVal;
        Map<Integer, Integer> keyFreq;
        Map<Integer, LinkedHashSet<Integer>> freqMap;
        int minFreq, capacity;

        public MyLFUCache(int capacity) {
            minFreq = 1;
            this.capacity = capacity;
            keyVal = new HashMap<>();
            keyFreq = new HashMap<>();
            freqMap = new HashMap<>();
        }
        
        public int get(int key) {
            if (!keyVal.containsKey(key)) return -1;
            updateFreq(key);
            return keyVal.get(key);
        }
        
        public void put(int key, int value) {
            if (!keyVal.containsKey(key)) {
                if (keyVal.size() == capacity) {
                    Set<Integer> freqSet = freqMap.get(minFreq);
                    int keyToRem = freqSet.iterator().next();
                    freqSet.remove(keyToRem);
                    if (freqSet.isEmpty()) {
                        freqMap.remove(minFreq);
                    }
                    keyFreq.remove(keyToRem);
                    keyVal.remove(keyToRem);
                }
                minFreq = 1; // update minFreq to 1 whenever there's a new pair
            }
            keyVal.put(key, value);
            updateFreq(key);
        }

        private void updateFreq(int key) {
            int freq = keyFreq.getOrDefault(key, 0);
            keyFreq.put(key, freq + 1);
            freqMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
            LinkedHashSet<Integer> freqSet = freqMap.get(freq);
            if (freqSet != null && !freqSet.isEmpty()) freqSet.remove((Integer)key);
            if (freqSet != null && freqSet.isEmpty()) {
                if (freq == minFreq) minFreq++;
                freqMap.remove(freq);
            }
        }
    }

    static void testLFUCache() {
        MyLFUCache lfu = new MyLFUCache(3);
        lfu.put(2, 2);
        lfu.put(1, 1);
        lfu.get(2); // returns 2
        lfu.get(1); // returns 1
        lfu.get(2); // returns 2
        lfu.put(3, 3);
        lfu.put(4, 4); // evicts LFU key

        lfu.get(3); // returns -1 (evicted)
        lfu.get(2);  // returns 2
        lfu.get(1);  // returns 1
        lfu.get(4);  // returns 4
    }

    public static void main(String[] args) {
        // Test case:
        // ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
        // [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
        // Solution1_HashMap_DoublyLinkedList_Implementation.LFUCache cache = new Solution1_HashMap_DoublyLinkedList_Implementation().new LFUCache(2);

        // cache.put(1, 1);
        // cache.put(2, 2);
        // System.out.println(cache.get(1)); // Expected output: 1
        // cache.put(3, 3);                  // Evicts key 2.
        // System.out.println(cache.get(2)); // Expected output: -1 (not found)
        // System.out.println(cache.get(3)); // Expected output: 3
        // cache.put(4, 4);                  // Evicts key 1.
        // System.out.println(cache.get(1)); // Expected output: -1 (not found)
        // System.out.println(cache.get(3)); // Expected output: 3
        // System.out.println(cache.get(4)); // Expected output: 4

        testLFUCache();
    }
}
