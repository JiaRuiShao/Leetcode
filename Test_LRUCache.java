import topics.LRUCache;

public class Test_LRUCache {
	private static LRUCache lruCache;
	
	private static void testCase1() {
		lruCache = new LRUCache(2);
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
		lruCache = new LRUCache(1);
		lruCache.put(2, 1);
		assert lruCache.get(2) == 1;
		lruCache.put(3, 2);
		assert lruCache.get(2) == -1;
		assert lruCache.get(3) == 2;
	}
	
	private static void testCase3() {}
	private static void testCase4() {}
	private static void testCase5() {}
	private static void testCase6() {}
	private static void testCase7() {}
	private static void testCase8() {}
	
	public static void main(String[] args) {
		testCase1();
		testCase2();
	}
}
