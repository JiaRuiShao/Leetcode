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
            // Node represents a key-value pair with frequency
            class Node {
                int key;
                int value;
                int freq;
                Node prev;
                Node next;
                
                Node(int key, int value) {
                    this.key = key;
                    this.value = value;
                    this.freq = 1;
                }
            }
            
            // Doubly linked list for maintaining LRU order within same frequency
            class DLList {
                Node head;
                Node tail;
                int size;
                
                DLList() {
                    head = new Node(0, 0);
                    tail = new Node(0, 0);
                    head.next = tail;
                    tail.prev = head;
                    size = 0;
                }
                
                void add(Node node) {
                    Node next = head.next;
                    head.next = node;
                    node.prev = head;
                    node.next = next;
                    next.prev = node;
                    size++;
                }
                
                void remove(Node node) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    size--;
                }
                
                Node removeLast() {
                    if (size == 0) return null;
                    Node last = tail.prev;
                    remove(last);
                    return last;
                }
            }
            
            private final int capacity;
            private int minFreq;
            private final Map<Integer, Node> cache;              // key -> node
            private final Map<Integer, DLList> freqMap;          // freq -> DLList
            
            public LFUCache(int capacity) {
                this.capacity = capacity;
                this.minFreq = 0;
                this.cache = new HashMap<>();
                this.freqMap = new HashMap<>();
            }
            
            public int get(int key) {
                Node node = cache.get(key);
                if (node == null) {
                    return -1;
                }
                updateFreq(node);
                return node.value;
            }
            
            public void put(int key, int value) {
                if (capacity == 0) return;
                
                Node node = cache.get(key);
                
                if (node != null) {
                    // Update existing key
                    node.value = value;
                    updateFreq(node);
                } else {
                    // Add new key
                    if (cache.size() >= capacity) {
                        // Evict LFU (and LRU if tie)
                        DLList minFreqList = freqMap.get(minFreq);
                        Node toRemove = minFreqList.removeLast();
                        cache.remove(toRemove.key);
                    }
                    
                    // Add new node
                    Node newNode = new Node(key, value);
                    cache.put(key, newNode);
                    freqMap.computeIfAbsent(1, k -> new DLList()).add(newNode);
                    minFreq = 1;
                }
            }
            
            private void updateFreq(Node node) {
                int oldFreq = node.freq;
                int newFreq = oldFreq + 1;
                
                // Remove from old frequency list
                DLList oldList = freqMap.get(oldFreq);
                oldList.remove(node);
                
                // Update minFreq if needed
                if (oldFreq == minFreq && oldList.size == 0) {
                    minFreq = newFreq;
                }
                
                // Add to new frequency list
                node.freq = newFreq;
                freqMap.computeIfAbsent(newFreq, k -> new DLList()).add(node);
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
                freqKeys.get(freq).remove(key);
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
