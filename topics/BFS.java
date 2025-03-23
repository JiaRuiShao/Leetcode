package topics;

import java.util.LinkedList;
import java.util.Queue;

import helper.Node;
import helper.State;

public class BFS {
    void basicBFSForTree(Node root) {
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.println(curr.val);
            for (Node children : curr.children) {
                queue.offer(children);
            }
        }
    }

    void basicBFSForTreeWithLevelCount(Node root) {
        int level = 0;
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();
                System.out.println("level = " + level + ", val = " + curr.val);
                for (Node children : curr.children) {
                    queue.offer(children);
                }
            }
        }
    }

    void basicBFSForTreeWithNodeState(Node root) {
        Queue<State> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(new State(root, 1));
        }

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            System.out.println("level = " + curr.level + ", val = " + curr.node.val);
            for (Node children : curr.node.children) {
                queue.offer(new State(children, curr.level + 1));
            }
        }
    }
    
}
