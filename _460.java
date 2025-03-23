import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 460. LFU Cache
 */
public class _460 {
    static class Solution1_HashMap_DoublyLinkedList_Implementation {

        class LFUCache {

            // Doubly linked list node modified from 146 LRU question.
            // The list is maintained in order:
            // [LFU & LRU ... LFU & MRU] <-> [MFU & LRU ... MFU & MRU]
            static class CacheNode {
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
        class LFUCache {
            // Mapping from key to value
            HashMap<Integer, Integer> kv;
            // Mapping from key to frequency
            HashMap<Integer, Integer> kf;
            // Mapping from frequency to keys (maintained as insertion order)
            HashMap<Integer, LinkedHashSet<Integer>> fk;
            // Tracks the minimum frequency
            int minFreq;
            // Maximum capacity of the LFU cache
            int capacity;

            public LFUCache(int capacity) {
                kv = new HashMap<>();
                kf = new HashMap<>();
                fk = new HashMap<>();
                this.capacity = capacity;
                this.minFreq = 0;
            }

            public int get(int key) {
                if (!kv.containsKey(key)) {
                    return -1;
                }
                increaseFreq(key);
                return kv.get(key);
            }

            public void put(int key, int val) {
                if (this.capacity <= 0) return;

                // If the key already exists, update its value and frequency
                if (kv.containsKey(key)) {
                    kv.put(key, val);
                    increaseFreq(key);
                    return;
                }

                // When cache is full, remove the key with the lowest frequency
                if (this.capacity <= kv.size()) {
                    removeMinFreqKey();
                }

                // Insert the new key with initial frequency 1
                kv.put(key, val);
                kf.put(key, 1);
                fk.putIfAbsent(1, new LinkedHashSet<>());
                fk.get(1).add(key);
                // Reset minFreq to 1 for the new key
                this.minFreq = 1;
            }

            private void removeMinFreqKey() {
                // Get the set of keys with the minimum frequency
                LinkedHashSet<Integer> keyList = fk.get(this.minFreq);
                // Remove the oldest key from that set
                int deletedKey = keyList.iterator().next();
                keyList.remove(deletedKey);
                if (keyList.isEmpty()) {
                    fk.remove(this.minFreq);
                    // No need to update minFreq here; it will be reset on insertion
                }
                kv.remove(deletedKey);
                kf.remove(deletedKey);
            }

            private void increaseFreq(int key) {
                int freq = kf.get(key);
                kf.put(key, freq + 1);
                fk.get(freq).remove(key);
                fk.putIfAbsent(freq + 1, new LinkedHashSet<>());
                fk.get(freq + 1).add(key);
                if (fk.get(freq).isEmpty()) {
                    fk.remove(freq);
                    if (freq == this.minFreq) {
                        this.minFreq++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Test case:
        // ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
        // [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
        Solution1_HashMap_DoublyLinkedList_Implementation.LFUCache cache = new Solution1_HashMap_DoublyLinkedList_Implementation().new LFUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // Expected output: 1
        cache.put(3, 3);                  // Evicts key 2.
        System.out.println(cache.get(2)); // Expected output: -1 (not found)
        System.out.println(cache.get(3)); // Expected output: 3
        cache.put(4, 4);                  // Evicts key 1.
        System.out.println(cache.get(1)); // Expected output: -1 (not found)
        System.out.println(cache.get(3)); // Expected output: 3
        System.out.println(cache.get(4)); // Expected output: 4
    }
}
