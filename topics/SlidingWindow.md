**滑动窗口算法框架**
- `...` is where we update window data
- Note:two places where we update the window data `...` are **symmetrical**
```
void slidingWindow(String s) {
    // build the freqMap
    Map<Character, Integer> freq = new HashMap<>();
    int left = 0, right = 0;
    while (right < s.length()) {
        // c 是将移入窗口的字符
        char c = s.charAt(right);
        // 增大窗口
        right++;

        // 进行窗口内数据的一系列更新
        // ...

        /*** debug place ***/
        System.out.printf("window: [%d, %d)\n", left, right);
        /********************/

        // 判断左侧窗口是否要收缩
        while (window need shrink) {
            // update the minimum window
            // ...

            // d 是将移出窗口的字符
            char d = s.charAt(right);
            // 缩小窗口
            left++;

            // 进行窗口内数据的一系列更新
            // ...
        }
    }
}
```