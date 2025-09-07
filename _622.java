import java.util.LinkedList;
import java.util.List;

/**
 * 622. Design Circular Queue
 */
public class _622 {
    class MyCircularQueue_LinkedList_Implementation {

        List<Integer> list;
        int capacity;

        public MyCircularQueue_LinkedList_Implementation(int k) {
            list = new LinkedList<>();
            capacity = k;
        }
        
        public boolean enQueue(int value) {
            if (isFull()) return false;
            list.add(value);
            return true;
        }
        
        public boolean deQueue() {
            if (isEmpty()) return false;
            list.remove(0);
            return true;
        }
        
        public int Front() {
            return isEmpty() ? -1 : list.get(0);
        }
        
        public int Rear() {
            return isEmpty() ? -1 : list.get(list.size() - 1);
        }
        
        public boolean isEmpty() {
            return list.isEmpty();
        }
        
        public boolean isFull() {
            return list.size() == capacity;
        }
    }

    class MyCircularQueue_Array_Implementation {

        int[] arr;
        int front, rear, size, capacity;

        public MyCircularQueue_Array_Implementation(int k) {
            size = 0;
            capacity = k;
            front = 0;
            rear = 0;
            arr = new int[k];
        }
        
        public boolean enQueue(int value) {
            if (isFull()) return false;
            arr[rear] = value;
            rear = (rear + 1) % capacity;
            size++;
            return true;
        }
        
        public boolean deQueue() {
            if (isEmpty()) return false;
            arr[front] = 0;
            front = (front + 1) % capacity;
            size--;
            return true;
        }
        
        public int Front() {
            return isEmpty() ? -1 : arr[front];
        }
        
        public int Rear() {
            int idx = (rear - 1 + capacity) % capacity;
            return isEmpty() ? -1 : arr[idx];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public boolean isFull() {
            return size == capacity;
        }
    }

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */
}
