package topics;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MonoStack {
    int[] nextGreaterElem(int[] nums) {
        int n = nums.length;
        int[] ngl = new int[n];
        Deque<Integer> stk = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && stk.peek() <= nums[i]) {
                stk.pop();
            }
            ngl[i] = stk.isEmpty() ? -1 : stk.peek();
            stk.push(nums[i]);
        }
        return ngl;
    }

    int[] nextLessElem(int[] nums) {
        int n = nums.length;
        int[] nle = new int[n];
        Deque<Integer> stk = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && stk.peek() >= nums[i]) {
                stk.pop();
            }
            nle[i] = stk.isEmpty() ? -1 : stk.peek();
            stk.push(nums[i]);
        }
        return nle;
    }

    int[] prevGreaterElem(int[] nums) {
        Deque<Integer> stk = new ArrayDeque<>(); // mono decreasing stack
        int[] pgl = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while (!stk.isEmpty() && stk.peek() <= num) {
                stk.pop();
            }
            pgl[i] = stk.isEmpty() ? -1 : stk.peek();
            stk.push(num);
        }
        return pgl;
    }

    int[] prevLessElem(int[] nums) {
        Deque<Integer> stk = new ArrayDeque<>();
        int n = nums.length;
        int[] ple = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && stk.peek() >= nums[i]) {
                stk.pop();
            }
            ple[i] = stk.isEmpty() ? -1 : stk.peek();
            stk.push(nums[i]);
        }
        return ple;
    }

    public static void main(String[] args) {
        MonoStack test = new MonoStack();
        int[] nums = {8, 4, 6, 6, 3};
        int[] nle = test.nextLessElem(nums);
        System.out.println(Arrays.toString(nle)); // res = [4, 3, 3, 3, -1]

        nums = new int[] {8, 7, 6, 7};
        int[] pgl = test.prevGreaterElem(nums);
        System.out.println(Arrays.toString(pgl)); // res = [-1, 8, 7, 8]
    }
}
