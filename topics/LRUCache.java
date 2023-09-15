package topics;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
	public static class ListNode {
		public int key;
		public int val;
		public ListNode prev;
		public ListNode next;
		
		public ListNode(int key, int val, ListNode prev, ListNode next) {
			this.key = key;
			this.val = val;
			this.prev = prev;
			this.next = next;
		}
		
		public ListNode(int key, int val) {
			this.key = key;
			this.val = val;
			this.prev = null;
			this.next = null;
		}
		
		public ListNode() {
			this.key = -1;
			this.val = -1;
			this.prev = null;
			this.next = null;
		}
	}
	
	static public class DoublyLinkedList {
		ListNode dummyHead;
		ListNode dummyTail;
		
		public DoublyLinkedList() {
			this.dummyHead = new ListNode();
			this.dummyTail = new ListNode();
			dummyHead.next = dummyTail;
			dummyTail.prev = dummyHead;
		}
		
		public void addLast(ListNode node) {
			ListNode prev = dummyTail.prev;
			prev.next = node;
			dummyTail.prev = node;
			node.prev = prev;
			node.next = dummyTail;
		}
		
		public void remove(ListNode node) {
			node.prev.next = node.next;
			node.next.prev = node.prev;
			node.prev = null;
			node.next = null;
		}
		
		public ListNode removeFirst() {
			ListNode eldestNode = dummyHead.next;
			if (eldestNode != dummyTail) {
				dummyHead.next = eldestNode.next;
				eldestNode.next.prev = dummyHead;
				eldestNode.prev = null;
				eldestNode.next = null;
				return eldestNode;
			}
			return null;
		}
	}
	
	private final int MAX_SIZE;
	private final DoublyLinkedList cache;
	private final Map<Integer, LRUCache.ListNode> helperMap;
	
	public LRUCache(int capacity) {
		this.MAX_SIZE = capacity;
		cache = new DoublyLinkedList();
		helperMap = new HashMap<>(capacity + 2);
	}
	
	public int get(int key) {
		LRUCache.ListNode node = helperMap.get(key);
		if (node == null) {
			return -1;
		}
		makeRecentlyUsed(node);
		return node.val;
	}
	
	public void put(int key, int val) {
		LRUCache.ListNode node = helperMap.get(key);
		if (node != null) {
			remove(node);
		}
		add(new LRUCache.ListNode(key, val));
		removeEldest();
	}
	
	private void makeRecentlyUsed(LRUCache.ListNode node) {
		remove(node);
		add(node);
	}
	
	private void add(LRUCache.ListNode node) {
		cache.addLast(node);
		helperMap.put(node.key, node);
	}
	
	private void remove(LRUCache.ListNode node)  {
		cache.remove(node);
		helperMap.remove(node.key);
	}
	
	private void removeEldest() {
		if (helperMap.size() > MAX_SIZE) {
			ListNode eldestNode = cache.removeFirst();
			if (eldestNode != null) {
				helperMap.remove(eldestNode.key);
			}
		}
	}
}