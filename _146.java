import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 146. LRU Cache
 */
public class _146 {
	/**
	 * LinkedList structure to maintain usage ordering; HashMap to store Key-CacheNode mapping.
	 * Use dummy head and tail for linked list construction to avoid corner cases.
	 * Doubly linked list: dummyHead <-> LRU ... MRU <-> dummyTail
	 */
	static class Solution1_HashMap_DoublyLinkedList_Implementation {
		static class CacheNode {
			int key;
			int value;
			CacheNode prev;
			CacheNode next;

			public CacheNode(int key, int value) {
				this.key = key;
				this.value = value;
			}
		}

		class LRUCache {
			CacheNode head;
			CacheNode tail;
			Map<Integer, CacheNode> cache;
			final int capacity;

			public LRUCache(int capacity) {
				head = new CacheNode(-1, -1);
				tail = new CacheNode(-1, -1);
				head.next = tail;
				tail.prev = head;
				cache = new HashMap<>();
				this.capacity = capacity;
			}
			
			// get val -> move this cache node to end of list
			public int get(int key) {
				CacheNode node = cache.get(key);
				if (node == null) {
					return -1;
				}
				makeRecently(node);
				return node.value;
			}

			// Moves the given node to the tail (most recently used)
			// - delete this node and add to end of list
    		// - update 4 * 2 times, maintain both prev and next pointers
			private void makeRecently(CacheNode node) {
				removeNode(node);
				addNode(node);
			}
			
			public void put(int key, int value) {
				if (cache.containsKey(key)) {
					CacheNode node = cache.get(key);
					node.value = value;
					makeRecently(node);
				} else {
					if (cache.size() == capacity) {
						int removedKey = removeLRU();
						cache.remove(removedKey);
					}
					CacheNode newNode = new CacheNode(key, value);
					addNode(newNode);
					cache.put(key, newNode);
				}
			}

			// prev <-> node <-> next
			// prev <-> next
			private void removeNode(CacheNode node) {
				CacheNode prev = node.prev;
				CacheNode next = node.next;
				prev.next = next;
				next.prev = prev;
				node.prev = null;
				node.next = null;
			}

			// add at the end of the list
			// prev <-> tail
			// prev <-> node <-> tail
			private void addNode(CacheNode node) {
				CacheNode prev = tail.prev;
				prev.next = node;
				node.prev = prev;
				node.next = tail;
				tail.prev = node;
			}

			// Removes and returns the key of the least recently used node
			private int removeLRU() {
				CacheNode lru = head.next;
				removeNode(lru);
				return lru.key;
			}
		}

	}

	/**
	 * Implementation using Java LinkedHashMap.
	 */
	static class Solution2_LinkedHashMap_Implementation {
		static class LRUCache {
			private final LinkedHashMap<Integer, Integer> map;
			
			public LRUCache(int capacity) {
				/**
				 * Set accessOrder to true to make sure recently used/accessed (k, v) is the last element.
				 * Set initialCapacity to our target capacity + 2 & loadFactor to 1 so that we don't have to reload the data
				 *  - save space for no more rehashing
				 *  - degrade searching performance for too many collisions (too many nodes in the linked list at a certain index)
				 *
				 *  A hash table with a threshold of 0.6f would resize when 60% of the space is occupied.
				 *  As a convention, the size of the hashtable is doubled. In order to prevent that & also have good performance,
				 *  we can use initialCapacity as capacity / 0.6 + 1, so that even each key is in different bucket, the loadFactor < 0.6.
				 */
				map = new LinkedHashMap<>((capacity / 3) * 5 + 1, 0.6f, true) {
					/**
					 * Returns true if this map should remove its eldest entry.
					 * This method is invoked by put and putAll after inserting a new entry into the map.
					 * If return true, the LinkedHashMap will remove the eldest entry;
					 * If return false, the LinkedHashMap will behave like a normal map without removing the eldest entry.
					 *
					 * @param eldest eldest entry, aka the first entry
					 * @return true if we want to remove the eldest/first entry; false if not
					 */
					@Override
					protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
						return size() > capacity;
					}
				};
			}
			
			public int get(int key) {
				return map.getOrDefault(key, -1);
			}
			
			public void put(int key, int value) {
				map.put(key, value);
			}
		}
	}

	static class Solution3_LinkedHashMap_Implementation {
		class LRUCache {
			final int capacity;
			LinkedHashMap<Integer, Integer> cache;
		
			public LRUCache(int capacity) { 
				this.capacity = capacity;
				cache = new LinkedHashMap<>();
			}
			
			public int get(int key) {
				if (!cache.containsKey(key)) {
					return -1;
				}
				makeRecently(key);
				return cache.get(key);
			}
			
			public void put(int key, int val) {
				if (cache.containsKey(key)) {
					cache.put(key, val);
					makeRecently(key);
					return;
				}
				
				if (cache.size() >= capacity) {
					int LRUKey = cache.keySet().iterator().next();
					cache.remove(LRUKey);
				}
				cache.put(key, val);
			}
			
			// LinkedHashMap maintain insertion order
			// LRU <-> MRU
			private void makeRecently(int key) {
				int val = cache.get(key);
				cache.remove(key);
				cache.put(key, val);
			}
		}
	}
	
	private static void testCase1() {
		Solution1_HashMap_DoublyLinkedList_Implementation.LRUCache lruCache = new Solution1_HashMap_DoublyLinkedList_Implementation().new LRUCache(2);
		lruCache.put(1, 1);
		lruCache.put(2, 2);
		assert lruCache.get(1) == 1;
		lruCache.put(3, 3);
		assert lruCache.get(2) == -1;
		lruCache.put(4, 4);
		assert lruCache.get(1) == -1;
		assert lruCache.get(3) == 3;
		assert lruCache.get(4) == 4;
	}
	
	private static void testCase2() {
		Solution1_HashMap_DoublyLinkedList_Implementation.LRUCache lruCache = new Solution1_HashMap_DoublyLinkedList_Implementation().new LRUCache(1);
		lruCache.put(2, 1);
		assert lruCache.get(2) == 1;
		lruCache.put(3, 2);
		assert lruCache.get(2) == -1;
		assert lruCache.get(3) == 2;
	}
	
	public static void main(String[] args) {
		testCase1();
		testCase2();
	}
}
