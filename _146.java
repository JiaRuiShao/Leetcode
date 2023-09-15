import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _146 {
	/**
	 * Implementation using Java LinkedHashMap.
	 */
	static class Solution1_LinkedHashMap_Implementation {
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
	
	static class Solution2_HashMap_DoublyLinkedList_Implementation {
		/**
		 * tail always points to the last not-null Node --> create a lot corner cases.
		 */
		static class LRUCache {
			private Map<Integer, Node> LRUMap = null;
			private int MAX_SIZE = 0;
			private Node dummyHead = null, tail = null;
			
			public LRUCache(int capacity) {
				LRUMap = new ConcurrentHashMap<>(capacity + 2, 1);
				MAX_SIZE = capacity;
				dummyHead = new Node(-1, -1);
				tail = dummyHead;
			}
			
			public int get(int key) {
				Node node = LRUMap.get(key);
				if (node == null) {
					return -1;
				}
				maintainAccessOrder(node);
				return node.getVal();
			}
			
			public void put(int key, int val) {
				Node node = LRUMap.get(key);
				if (node == null) {
					Node newNode = new Node(key, val, tail, null);
					addLast(newNode);
					LRUMap.put(key, newNode);
					validateSize();
				} else {
					node.setVal(val);
					maintainAccessOrder(node);
				}
			}
			
			private void remove(Node node) {
				node.getPrev().setNext(node.getNext());
				if (node.getNext() != null) {
					node.getNext().setPrev(node.getPrev());
				} else {
					tail = node.getPrev();
				}
				node.setNext(null);
				node.setPrev(null);
			}
			
			private void addLast(Node node) {
				node.setNext(null);
				node.setPrev(tail);
				tail.setNext(node);
				tail = node;
			}
			
			private void maintainAccessOrder(Node node) {
				remove(node);
				addLast(node);
			}
			
			private int getSize() {
				return LRUMap.size();
			}
			
			private void validateSize() {
				Node eldestNode = null;
				if (getSize() <= MAX_SIZE || dummyHead == tail || (eldestNode = dummyHead.getNext()) == null) {
					return;
				}
				LRUMap.remove(eldestNode.getKey());
				remove(eldestNode);
			}
		}
	}
	
	static class Node {
		private int key = 0, val = 0;
		private Node prev = null, next = null;
		
		public Node (int key, int val) {
			this.key = key;
			this.val = val;
			prev = null;
			next = null;
		}
		
		public Node (int key, int val, Node prev, Node next) {
			this.key = key;
			this.val = val;
			this.prev = prev;
			this.next = next;
		}
		
		public int getKey() {
			return key;
		}
		
		public int getVal() {
			return val;
		}
		
		public Node getPrev() {
			return prev;
		}
		
		public Node getNext() {
			return next;
		}
		
		public void setKey(int key) {
			this.key = key;
		}
		
		public void setVal(int val) {
			this.val = val;
		}
		
		public void setPrev(Node prev) {
			this.prev = prev;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
	}
	
	private static void testCase1() {
		Solution2_HashMap_DoublyLinkedList_Implementation.LRUCache lruCache = new Solution2_HashMap_DoublyLinkedList_Implementation.LRUCache(2);
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
		Solution2_HashMap_DoublyLinkedList_Implementation.LRUCache lruCache = new Solution2_HashMap_DoublyLinkedList_Implementation.LRUCache(1);
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
