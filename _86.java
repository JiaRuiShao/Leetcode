import helper.ListNode;

/**
 * 86. Partition List.
 * Given the head of a linked list and a value x, partition it such that
 * all nodes less than x come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 */
public class _86 {
    class Solution1_Two_Pointers {
        public ListNode partition(ListNode head, int x) {
            ListNode smallerDummy = new ListNode(-1), largerDummy = new ListNode(-1);
            ListNode smaller = smallerDummy, larger = largerDummy;
            ListNode temp = head;
            while (temp != null) {
                if (temp.val < x) {
                    smaller.next = new ListNode(temp.val);
                    smaller = smaller.next;
                } else {
                    larger.next = new ListNode(temp.val);
                    larger = larger.next;
                }
                temp = temp.next;
            }
            smaller.next = largerDummy.next;
            return smallerDummy.next;
        }
    }
}
