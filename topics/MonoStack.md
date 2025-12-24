We use monotonic increasing/descreasing stack when we need to find next greater/smaller elements from left or right.

## Find Next Less Element
```java
// input:  nums = [8, 4, 6, 6, 3]
// return: res = [4, 3, 3, 3, -1]
// Traverse from right to left and keep a monotonic increasing stack
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
```

## Find Prev Greater Elem
```java
// input:  nums = [8, 4, 6, 6, 3]
// return: res =  [-1, 8, 7, 8]

int[] prevGreaterElem(int[] nums) {
    Deque<Integer> stk = new ArrayDeque<>(); // mono decreasing stack
    int[] pgl = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        while (!stk.isEmpty() && stk.peek() <= num) {
            stk.pop();
        }
        pgl[i] = stk.peek();
        stk.push(num);
    }
    return pgl;
}
```