import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 895. Maximum Frequency Stack
 */
public class _895 {
    /**
     * This solution uses two maps—one for tracking element frequencies and another for mapping frequencies to stacks.
     */
    class FreqStack {

        Map<Integer, Integer> freq;
        Map<Integer, Deque<Integer>> freqStk;
        int maxFreq;

        public FreqStack() {
            freq = new HashMap<>();
            freqStk = new HashMap<>();
            maxFreq = 0;
        }

        public void push(int val) {
            freq.put(val, freq.getOrDefault(val, 0) + 1);
            int valFreq = freq.get(val);
            freqStk.putIfAbsent(valFreq, new LinkedList<>());
            freqStk.get(valFreq).push(val);
            maxFreq = Math.max(maxFreq, valFreq);
        }

        public int pop() {
            Deque<Integer> maxFreqStk = freqStk.get(maxFreq);
            int remove = maxFreqStk.pop();
            if (maxFreqStk.isEmpty()) {
                freqStk.remove(maxFreq);
                maxFreq--;
                // test will fail if write like:
                // freqStk.remove(maxFreq--);
            }
            freq.put(remove, freq.get(remove) - 1);
            return remove;
        }
    }

}
