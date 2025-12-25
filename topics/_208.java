package topics;

/**
 * 208. Implement Trie (Prefix Tree)
 */
public class _208 {
    class Trie {

        private static class Node {
            Node[] children = new Node[26];
            boolean endOfWord = false;
        }

        private Node root;

        public Trie() {
            root = new Node();
        }
        
        public void insert(String word) {
            Node curr = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new Node();
                }
                curr = curr.children[index];
            }
            curr.endOfWord = true;
        }
        
        public boolean search(String word) {
            Node lastNode = getLastMatchedNode(word);
            return lastNode != null && lastNode.endOfWord;
        }
        
        public boolean startsWith(String prefix) {
            return getLastMatchedNode(prefix) != null;
        }

        private Node getLastMatchedNode(String s) {
            Node curr = root;
            for (char c : s.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) {
                    return null;
                }
                curr = curr.children[index];
            }
            return curr;
        }
    }
}
