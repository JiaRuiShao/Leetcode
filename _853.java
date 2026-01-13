import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 853. Car Fleet
 */
public class _853 {
    class Solution1_Sort_Traversal {
        class Car {
            int pos;
            double time;
            public Car(int pos, double time) {
                this.pos = pos;
                this.time = time;
            }
        }
        
        public int carFleet(int target, int[] position, int[] speed) {
            int n = position.length;
            Car[] cars = new Car[n];
            for (int i = 0; i < n; i++) {
                cars[i] = new Car(position[i], (double)(target - position[i]) / speed[i]);
            }
            Arrays.sort(cars, (a, b) -> Integer.compare(a.pos, b.pos));

            int fleet = n;
            for (int i = n - 2; i >= 0; i--) {
                if (cars[i].time <= cars[i + 1].time) {
                    fleet--;
                    cars[i].time = cars[i + 1].time;
                }
            }
            return fleet;
        }
    }

    class Solution2_Sort_MonoStack {

        class Car {
            int pos;
            double time;
            public Car(int pos, double time) {
                this.pos = pos;
                this.time = time;
            }
        }

        public int carFleet(int target, int[] position, int[] speed) {
            int n = position.length;
            Car[] cars = new Car[n];
            for (int i = 0; i < n; i++) {
                cars[i] = new Car(position[i], (double)(target - position[i]) / speed[i]);
            }
            Arrays.sort(cars, (a, b) -> Integer.compare(a.pos, b.pos));

            int fleet = n;
            Deque<Double> minStk = new ArrayDeque<>(); // find previous LTE elements using minStack
            for (int i = n - 1; i >= 0; i--) {
                if (!minStk.isEmpty() && cars[i].time <= minStk.peek()) {
                    fleet--;
                } else {
                    minStk.push(cars[i].time);
                }
            }
            return fleet;

            // below is another way to return the fleet count without explicitly count fleet number
            // Deque<Double> minStk = new ArrayDeque<>(); // find previous LTE elements using minStack
            // for (int i = n - 1; i >= 0; i--) {
            //     if (!minStk.isEmpty() && cars[i].time <= minStk.peek()) {
            //         continue;
            //     }
            //     minStk.push(cars[i].time);
            // }
            // return minStk.size();
        }
    }

    class Solution3_Sort_MonoStack {

        class Car {
            int pos;
            double time;
            public Car(int pos, double time) {
                this.pos = pos;
                this.time = time;
            }
        }

        public int carFleet(int target, int[] position, int[] speed) {
            int n = position.length;
            Car[] cars = new Car[n];
            for (int i = 0; i < n; i++) {
                cars[i] = new Car(position[i], (double)(target - position[i]) / speed[i]);
            }
            Arrays.sort(cars, (a, b) -> Integer.compare(a.pos, b.pos));

            Deque<Double> minStk = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                // car behind is faster, catch up with slower car ahead
                while (!minStk.isEmpty() && cars[i].time >= minStk.peek()) {
                    minStk.pop();
                }
                minStk.push(cars[i].time);
            }
            return minStk.size();
        }
    }
}