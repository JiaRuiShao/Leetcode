/**
 * 707. Design Linked List
 */
public class _707 {
    class MyLinkedList {
        // arraylist O(n) worst
        // node O(n)
        private class Node {
            int val;
            Node prev, next;
            Node (int val) {
                this.val = val;
                this.prev = null;
                this.next = null;
            }
        }

        private Node head, tail;
        private int size;

        public MyLinkedList() {
            size = 0;
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
        }

        private Node getFromBothEnd(int index) {
            if (index >= size) {
                return null;
            }
            boolean inPriorHalf = index <= (size - 1) / 2;
            Node curr = inPriorHalf ? head.next : tail.prev;
            index = inPriorHalf ? index : size - 1 - index;
            for (int i = 0; i < index && curr != null; i++) {
                curr = inPriorHalf ? curr.next : curr.prev;
            }
            return curr;
        }
        
        public int get(int index) {
            Node curr = getFromBothEnd(index);
            return curr != null ? curr.val : -1;
        }
        
        public void addAtHead(int val) {
            addAtIndex(0, val);
        }
        
        public void addAtTail(int val) {
            Node newNode = new Node(val);        
            tail.prev.next = newNode;
            newNode.next = tail;
            newNode.prev = tail.prev;
            tail.prev = newNode;
            size++;
        }
        
        public void addAtIndex(int index, int val) {
            Node curr = getFromBothEnd(index);
            if (curr != null) {
                Node newNode = new Node(val);
                curr.prev.next = newNode;
                newNode.prev = curr.prev;
                newNode.next = curr;
                curr.prev = newNode;
                size++;
            } else if (index == size) {
                addAtTail(val);
            }
        }
        
        public void deleteAtIndex(int index) {
            Node curr = getFromBothEnd(index);
            if (curr != null) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                curr.prev = null;
                curr.next = null;
                size--;
            }
        }
    }
}
