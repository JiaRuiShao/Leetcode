import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 735. Asteroid Collision
 */
public class _735 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_Simulation_Stack {
        public int[] asteroidCollision(int[] asteroids) {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i < asteroids.length; ) {
                int currAsteroid = asteroids[i];
                int prevAsteroid = stack.isEmpty() ? currAsteroid : stack.peek();

                if (prevAsteroid > 0 && currAsteroid < 0) {
                    if (Math.abs(currAsteroid) < Math.abs(prevAsteroid)) {
                        i++;
                    } else if (Math.abs(currAsteroid) > Math.abs(prevAsteroid)) {
                        stack.pop();
                    } else {
                        stack.pop();
                        i++;
                    }
                } else {
                    stack.push(currAsteroid);
                    i++;
                }
            }

            int n = stack.size();
            int[] asteroidsAfterCollision = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                asteroidsAfterCollision[i] = stack.pop();
            }
            return asteroidsAfterCollision;
        }
    }
}
