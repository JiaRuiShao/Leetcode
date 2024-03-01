- ### Inclusive Search Range: [left, right]
  - **Basic Binary Search**
    ```
    int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // search range: [left, right]
        while (left <= right) { // terminate when search range is empty
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                // search range: [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // search range: [left, mid-1]
                right = mid - 1;
            }
        }
        return -1;
    }
    ```
  - **Binary Search -- Left Boundary of a Dup Elem (if there's any)**
    ```
    int leftBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // search range: [left, right]
        while (left <= right) { // terminate when left == right + 1
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // narrow the upper bound of the search range
                right = mid - 1;
            } else if (nums[mid] < target) {
                // search range: [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // search range: [left, mid-1]
                right = mid - 1;
            }
        }
        // when all elems < target, left = nums.length
        if (left == nums.length) return -1;
        return nums[left] == target ? left : -1;
    }
    ```
    - left pointer out of bound: input `nums` is `[1, 1, 1, 1]` and `target` is `2`
    - left pointer points to the `smallest elem > target` if target doesn't exist: `nums[left] = smallest elem larger than target in nums`
    - left pointer points to the left most elem if there's many target elems in `nums`
  - **Binary Search -- Right Boundary of a Dup Elem (if there's any)**
    ```
    int rightBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // search range: [left, right]
        while (left <= right) { // terminate when left == right + 1
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // narrow the lower bound of the search range
                left = mid + 1;
            } else if (nums[mid] < target) {
                // search range: [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // search range: [left, mid-1]
                right = mid - 1;
            }
        }
        // when all elems > target, right == -1
        if (right < 0) return -1;
        return nums[right] == target ? right : -1;
    }
    ```
    - right pointer out of bound: input `nums` is `[1, 1, 1, 1]` and `target` is `0`
    - right pointer points to the `largest elem < target` if target doesn't exist: `nums[right] = largest elem less than target in nums`
    - right pointer points to the right most elem if there's many target elems in `nums`
  - ### Other Improvements
    - To calculate the mid from given range: [left, right], we can use bitwise operation to divide a large int by 2 without needing to worry about integer overflow:
    ```java
    int mid = (low + high) >>> 1;
    ```
    whereas `>>>` is an unsigned bitwise operator that shifts the bits of the number to the right and fills the leftmost bits with zeros.
    - Use `Objects.compareTo()` when comparing objects not primitives
    ```java
    int cmp = midVal.compareTo(key);
    if (cmp < 0) // mid less than key, going right
        low = mid + 1;
    else if (cmp > 0) // mid larger than key, going left
        high = mid - 1;
    else // found the key
        return mid;
    ```