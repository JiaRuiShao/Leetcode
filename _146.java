import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 146. LRU Cache
 * 
 * - S1: HashMap + DLL LinkedList
 * - S2: LinkedHashMap
 * 
 * Followup:
 * - Make it thread-safe? Use synchronized / ReentrantLock
 * - 
 */
public class _146 {
	/**
	 * LinkedList structure to maintain usage ordering; HashMap to store Key-CacheNode mapping.
	 * Use dummy head and tail for linked list construction to avoid corner cases.
	 * Doubly linked list: dummyHead <-> LRU ... MRU <-> dummyTail
	 */
	class Solution1_HashMap_DoublyLinkedList {
		static class Node {
			int key, val;
			Node prev, next;
			public Node(int key, int val) {
				this.key = key;
				this.val = val;
				this.prev = null;
				this.next = null;
			}
		}

		Map<Integer, Node> keyToNode;
		Node head, tail;
		int capacity;

		public Solution1_HashMap_DoublyLinkedList(int capacity) {
			keyToNode = new HashMap<>();
			head = new Node(-1, -1);
			tail = new Node(-1, -1);
			head.next = tail;
			tail.prev = head;
			this.capacity = capacity;
		}
		
		public int get(int key) {
			if (!keyToNode.containsKey(key)) {
				return -1;
			}
			Node node = keyToNode.get(key);
			delete(node);
			addToLast(node);
			return node.val;
		}
		
		public void put(int key, int value) {
			if (keyToNode.containsKey(key)) {
				Node node = keyToNode.get(key);
				node.val = value;
				delete(node);
				addToLast(node);
			} else {
				if (keyToNode.size() == capacity) {
					int rem = removeFirst();
					keyToNode.remove(rem);
				}
				Node newNode = new Node(key, value);
				addToLast(newNode);
				keyToNode.put(key, newNode);
			}
		}

		private void delete(Node node) {
			Node prev = node.prev;
			Node next = node.next;
			prev.next = next;
			next.prev = prev;
		}

		private void addToLast(Node node) {
			Node prev = tail.prev;
			prev.next = node;
			tail.prev = node;
			node.prev = prev;
			node.next = tail;
		}

		private int removeFirst() {
			Node first = head.next;
			Node next = first.next;
			head.next = next;
			next.prev = head;
			return first.key;
		}
	}

	/**
	 * Implementation using Java LinkedHashMap.
	 */
	static class Solution2_LinkedHashMap {
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
				map = new LinkedHashMap<>((capacity / 3) * 5 + 1, 0.6f, true) { // accessOrder - the ordering mode - true for access-order, false for insertion-order
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

	static class Solution3_LinkedHashMap {
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
		Solution1_HashMap_DoublyLinkedList lruCache = new _146().new Solution1_HashMap_DoublyLinkedList(2);
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
		Solution1_HashMap_DoublyLinkedList lruCache = new _146().new Solution1_HashMap_DoublyLinkedList(1);
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
