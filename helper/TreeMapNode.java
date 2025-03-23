package helper;

public class TreeMapNode<K, V> {
        K key;
        V value;
        int size; // node # of subtree with current node as root node
    
        TreeMapNode<K, V> left;
        TreeMapNode<K, V> right;

        TreeMapNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
}
